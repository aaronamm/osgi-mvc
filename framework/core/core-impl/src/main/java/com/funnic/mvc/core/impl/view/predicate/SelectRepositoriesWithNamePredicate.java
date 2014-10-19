package com.funnic.mvc.core.impl.view.predicate;

import com.funnic.mvc.core.impl.view.ViewRepository;
import org.apache.commons.collections4.Predicate;

/**
 * @author Per
 */
public class SelectRepositoriesWithNamePredicate implements Predicate<ViewRepository> {
	private final String bundleName;

	public SelectRepositoriesWithNamePredicate(final String bundleName) {
		this.bundleName = bundleName;
	}

	@Override
	public boolean evaluate(ViewRepository object) {
		return object.getBundle().getSymbolicName().equals(bundleName);
	}
}
