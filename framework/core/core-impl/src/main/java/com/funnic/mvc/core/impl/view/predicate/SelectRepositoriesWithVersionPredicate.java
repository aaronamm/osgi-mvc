package com.funnic.mvc.core.impl.view.predicate;

import com.funnic.mvc.core.impl.VersionRange;
import com.funnic.mvc.core.impl.view.ViewRepository;
import org.apache.commons.collections4.Predicate;

/**
 * @author Per
 */
public class SelectRepositoriesWithVersionPredicate implements Predicate<ViewRepository> {
	private final VersionRange versionRange;

	public SelectRepositoriesWithVersionPredicate(final String bundleVersion) {
		this.versionRange = VersionRange.parseRange(bundleVersion);
	}

	@Override
	public boolean evaluate(ViewRepository object) {
		return versionRange.withinRange(object.getBundle().getVersion());
	}
}
