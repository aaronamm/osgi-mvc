package com.funnic.mvc.core.impl.repositories;

import org.apache.commons.collections4.Predicate;
import org.osgi.framework.Bundle;

/**
 * @author Per
 */
public class FindBundleTemplateEntryPredicate implements Predicate<BundleTemplateEntry> {

	private final Bundle bundle;

	public FindBundleTemplateEntryPredicate(final Bundle bundle) {
		this.bundle = bundle;
	}

	@Override
	public boolean evaluate(final BundleTemplateEntry object) {
		return object.getBundle().equals(bundle);
	}
}
