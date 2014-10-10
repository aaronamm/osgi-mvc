package com.funnic.mvc.core.impl.repositories;

import com.funnic.mvc.core.api.TemplateRepository;
import com.funnic.mvc.core.impl.VersionRange;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.osgi.framework.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Per
 */
public class BundleTemplateRepository implements TemplateRepository, BundleListener {
	private static final String PREFIX = "bundle://";

	private final BundleContext bundleContext;
	private final List<BundleTemplateEntry> bundleTemplateEntries = new ArrayList<BundleTemplateEntry>();

	public BundleTemplateRepository(final BundleContext bundleContext) {
		this.bundleContext = bundleContext;
		bundleContext.addBundleListener(this);
		Bundle[] bundles = bundleContext.getBundles();
		for (Bundle bundle : bundles) {
			addBundle(bundle);
		}
	}

	public void shutdown() {
		bundleContext.removeBundleListener(this);
	}

	@Override
	public String getPrefix() {
		return PREFIX;
	}

	@Override
	public URL getTemplateURL(final Bundle bundle, final String path) {
		final String pathWithoutPrefix = path.substring(PREFIX.length());
		int idx = pathWithoutPrefix.indexOf("/");
		final String bundleInfo = pathWithoutPrefix.substring(0, idx);
		final String relativePath = pathWithoutPrefix.substring(idx);

		String[] bundleInfoParts = bundleInfo.split(":");
		String bundleName = null;
		if (bundleInfoParts.length > 0)
			bundleName = bundleInfoParts[0];
		VersionRange versionRange = null;
		if (bundleInfoParts.length > 1)
			versionRange = VersionRange.parseRange(bundleInfoParts[1]);

		return getBestMatchingTemplates(bundleName, versionRange, relativePath);
	}

	private URL getBestMatchingTemplates(final String bundleName, final VersionRange versionRange, final String relativePath) {
		final VersionRange range = ObjectUtils.defaultIfNull(versionRange, VersionRange.ALL);
		Version matchingVersion = Version.emptyVersion;
		URL matchingUrl = null;
		for (final BundleTemplateEntry entry : bundleTemplateEntries) {
			final Bundle currentBundle = entry.getBundle();
			final Version currentVersion = currentBundle.getVersion();
			if (currentVersion.compareTo(matchingVersion) > 0 && range.withinRange(currentVersion) &&
					matchingBundleName(bundleName, currentBundle.getSymbolicName())) {
				URL match = entry.getTemplateURL(relativePath);
				if (match != null) {
					matchingVersion = currentVersion;
					matchingUrl = match;
				}
			}
		}

		return matchingUrl;
	}

	private boolean matchingBundleName(final String searchForName, final String bundleName) {
		if (searchForName == null)
			return true;
		return searchForName.equals(bundleName);
	}

	@Override
	public void bundleChanged(BundleEvent bundleEvent) {
		if (bundleEvent.getType() == BundleEvent.STARTED) {
			addBundle(bundleEvent.getBundle());
		} else if (bundleEvent.getType() == BundleEvent.STOPPED) {
			removeBundle(bundleEvent.getBundle());
		}
	}

	private void addBundle(final Bundle bundle) {
		Enumeration<URL> entries = bundle.findEntries("/", "*.ftl", true);
		if (entries != null) {
			BundleTemplateEntry entry = new BundleTemplateEntry(bundle, entries);
			bundleTemplateEntries.add(entry);
		}
	}

	private void removeBundle(final Bundle bundle) {
		BundleTemplateEntry entry = CollectionUtils.find(bundleTemplateEntries,
				new FindBundleTemplateEntryPredicate(bundle));
		bundleTemplateEntries.remove(entry);
	}

}
