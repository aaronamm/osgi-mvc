package com.funnic.mvc.templates.freemarker.repository;

import com.funnic.mvc.core.api.exceptions.TemplateInfoNotFoundException;
import com.funnic.mvc.core.api.repository.TemplateInfo;
import com.funnic.mvc.core.api.repository.TemplateRepository;
import com.funnic.mvc.core.spi.VersionRange;
import com.funnic.mvc.templates.freemarker.utils.TemplatingUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.EnumerationUtils;
import org.osgi.framework.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Per
 */
public class FreemarkerTemplateRepository implements TemplateRepository, BundleListener {
	private static final String TEMPLATE_PREFIX = ".ftl";
	private static final String BUNDLE_PATH_PREFIX= "bundle://";

	private final BundleContext bundleContext;
	private final List<BundleTemplateEntry> bundleTemplateEntries = new ArrayList<BundleTemplateEntry>();

	public FreemarkerTemplateRepository(final BundleContext bundleContext) {
		this.bundleContext = bundleContext;
		bundleContext.addBundleListener(this);
		Bundle[] bundles = bundleContext.getBundles();
		for (Bundle bundle : bundles) {
			addBundle(bundle);
		}
	}

	public void destroy() {
		bundleContext.removeBundleListener(this);
	}

	@Override
	public boolean accept(String path) {
		return path.endsWith(TEMPLATE_PREFIX);
	}

	@Override
	public TemplateInfo getTemplateInfo(Bundle bundle, String path) throws TemplateInfoNotFoundException {
		if(TemplatingUtils.isLocalPath(path)) {
			final URL url = bundle.getEntry(path);
			if(url == null)
				throw new TemplateInfoNotFoundException(bundle, path);
			return new TemplateInfo(url, bundle);
		}

		return resolveTemplateInfo(bundle, path);
	}

	/**
	 * Resolve a template from the supplied bundle's point-of-view
	 *
	 * @param bundle
	 * @param path
	 * @return
	 */
	private TemplateInfo resolveTemplateInfo(final Bundle bundle, final String path) {
		final String pathWithoutPrefix = path.substring(BUNDLE_PATH_PREFIX.length());
		int idx = pathWithoutPrefix.indexOf("/");
		final String bundleInfo = pathWithoutPrefix.substring(0, idx);
		final String relativePath = pathWithoutPrefix.substring(idx);

		String[] bundleInfoParts = bundleInfo.split(":");
		String bundleName = null;
		if (bundleInfoParts.length > 0)
			bundleName = bundleInfoParts[0];
		VersionRange versionRange = VersionRange.ALL;
		if (bundleInfoParts.length > 1)
			versionRange = VersionRange.parseRange(bundleInfoParts[1]);

		return getBestMatchingTemplates(bundleName, versionRange, relativePath);
	}


	private TemplateInfo getBestMatchingTemplates(final String bundleName, final VersionRange versionRange, final String relativePath) {
		Version matchingVersion = Version.emptyVersion;
		TemplateInfo matchingUrl = null;
		for (final BundleTemplateEntry entry : bundleTemplateEntries) {
			final Bundle currentBundle = entry.getBundle();
			final Version currentVersion = currentBundle.getVersion();
			if (currentVersion.compareTo(matchingVersion) > 0 && versionRange.withinRange(currentVersion) &&
					TemplatingUtils.matchingBundleName(bundleName, currentBundle.getSymbolicName())) {
				TemplateInfo match = entry.getTemplateInfo(relativePath);
				if (match != null) {
					matchingVersion = currentVersion;
					matchingUrl = match;
				}
			}
		}

		return matchingUrl;
	}


	@Override
	public void bundleChanged(BundleEvent event) {
		if (event.getType() == BundleEvent.STARTED) {
			addBundle(event.getBundle());
		} else if (event.getType() == BundleEvent.STOPPED) {
			removeBundle(event.getBundle());
		}
	}

	private void addBundle(final Bundle bundle) {
		final List<URL> entries = getEntries(bundle);
		if(entries.isEmpty())
			return;

		bundleTemplateEntries.add(new BundleTemplateEntry(bundle, entries));
	}

	public List<URL> getEntries(final Bundle bundle) {
		Enumeration<URL> enumeration = bundle.findEntries("/", "*.ftl", true);
		if(enumeration == null)
			return new ArrayList<URL>();
		return EnumerationUtils.toList(enumeration);
	}

	private void removeBundle(final Bundle bundle) {
		BundleTemplateEntry entry = CollectionUtils.find(bundleTemplateEntries,
				new FindBundleTemplateEntryPredicate(bundle));
		bundleTemplateEntries.remove(entry);
	}
}
