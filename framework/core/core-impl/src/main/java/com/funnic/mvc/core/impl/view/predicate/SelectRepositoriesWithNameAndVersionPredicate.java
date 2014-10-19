package com.funnic.mvc.core.impl.view.predicate;

import com.funnic.mvc.core.impl.VersionRange;
import com.funnic.mvc.core.impl.view.ViewRepository;
import org.apache.commons.collections4.Predicate;
import org.osgi.framework.Bundle;

/**
 * @author Per
 */
public class SelectRepositoriesWithNameAndVersionPredicate implements Predicate<ViewRepository> {

	private final String bundleName;
	private final VersionRange versionRange;

	public SelectRepositoriesWithNameAndVersionPredicate(final String bundleName, final String bundleVersion) {
		this.bundleName = bundleName;
		this.versionRange = VersionRange.parseRange(bundleVersion);
	}

	@Override
	public boolean evaluate(final ViewRepository object) {
		final Bundle bundle = object.getBundle();
		if(!bundle.getSymbolicName().equals(bundleName)) {
			return false;
		}

		return versionRange.withinRange(bundle.getVersion());
	}
}
