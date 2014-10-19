package com.funnic.mvc.core.impl.view;

import com.funnic.mvc.core.api.view.ViewInfo;
import com.funnic.mvc.core.api.view.ViewRepositoryManager;
import com.funnic.mvc.core.impl.view.predicate.FindViewRepositoryPredicate;
import com.funnic.mvc.core.impl.view.predicate.SelectRepositoriesWithNameAndVersionPredicate;
import com.funnic.mvc.core.impl.view.predicate.SelectRepositoriesWithNamePredicate;
import com.funnic.mvc.core.impl.view.predicate.SelectRepositoriesWithVersionPredicate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Per
 */
public class ViewRepositoryManagerImpl implements ViewRepositoryManager {
	private static final Logger log = LoggerFactory.getLogger(ViewRepositoryManagerImpl.class);
	private static final String BUNDLE_PATH_PREFIX = "bundle://";

	private final List<ViewRepository> viewRepositories = new ArrayList<>();
	private final Bundle bundle;

	public ViewRepositoryManagerImpl(final Bundle bundle) {
		this.bundle = bundle;
	}

	@Override
	public ViewInfo getView(final Bundle bundle, final String path) {
		log.debug("Resolving view: {} for bundle: {}", path, bundle.toString());
		if(isLocalPath(path)) {
			final URL url = bundle.getEntry(path);
			Validate.notNull(url, "Template '%s' cannot be found", path);
			return new ViewInfo(url, bundle);
		}

		// bundle://bundle-name:version/path/to/file.ftl
		// bundle://:version/path/to/file.ftl
		// bundle://:/path/to/file.ftl
		// bundle://bundle-name:/path/to/file.ftl

		final String pathWithoutPrefix = path.substring(BUNDLE_PATH_PREFIX.length());
		int idx = pathWithoutPrefix.indexOf("/");
		final String bundleInfo = pathWithoutPrefix.substring(0, idx);
		final String absolutePath = pathWithoutPrefix.substring(idx);

		final Collection<ViewRepository> matchedRepositories = CollectionUtils.select(viewRepositories, GetBestMatchingPredicate(bundleInfo));
		Version version = null;
		ViewInfo viewInfo = null;
		for (final ViewRepository repository : matchedRepositories) {
			final Version repositoryVersion = repository.getBundle().getVersion();
			if (version == null) {
				final URL currentURL = repository.getView(absolutePath);
				if(currentURL != null) {
					viewInfo = new ViewInfo(currentURL, repository.getBundle());
					version = repositoryVersion;
				}
				continue;
			}

			if(version.compareTo(repositoryVersion) > 0) {
				final URL currentURL = repository.getView(absolutePath);
				if(currentURL != null) {
					viewInfo = new ViewInfo(currentURL, repository.getBundle());
					version = repositoryVersion;
				}
			}
		}

		return viewInfo;
	}

	private Predicate<? super ViewRepository> GetBestMatchingPredicate(final String bundleInfo) {
		final String[] parts = bundleInfo.split(":");
		if(parts.length == 0)
			return PredicateUtils.truePredicate();

		if(containsBundleName(parts) && containsBundleVersion(parts)) {
			return new SelectRepositoriesWithNameAndVersionPredicate(parts[0], parts[1]);
		}

		if(containsBundleName(parts)) {
			return new SelectRepositoriesWithNamePredicate(parts[0]);
		}

		if(containsBundleVersion(parts)) {
			return new SelectRepositoriesWithVersionPredicate(parts[1]);
		}

		return PredicateUtils.truePredicate();
	}

	private boolean containsBundleVersion(final String[] parts) {
		return StringUtils.isNotBlank(parts[1]);
	}

	private boolean containsBundleName(final String[] parts) {
		return StringUtils.isNotBlank(parts[0]);
	}

	public void registerViews(ServiceReference reference) {
		final Bundle repoBundle = reference.getBundle();
		final BundleContext context = repoBundle.getBundleContext();
		final List<URL> views = (List<URL>) context.getService(reference);
		final ViewRepository viewRepository = new ViewRepositoryImpl(repoBundle, reference, views);
		viewRepositories.add(viewRepository);
	}

	public void unregisterViews(ServiceReference reference) {
		final ViewRepository repository = CollectionUtils.find(this.viewRepositories, new FindViewRepositoryPredicate(reference));
		Validate.notNull(repository, "How come the supplied service reference " + reference + " is not part of the viewRepositories list?");
		viewRepositories.remove(repository);

		final Bundle repoBundle = reference.getBundle();
		final BundleContext context = repoBundle.getBundleContext();
		context.ungetService(reference);
	}

	public static boolean isLocalPath(final String path) {
		return path.startsWith("/");
	}
}
