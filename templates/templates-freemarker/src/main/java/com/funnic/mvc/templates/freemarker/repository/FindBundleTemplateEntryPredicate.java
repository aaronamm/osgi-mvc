package com.funnic.mvc.templates.freemarker.repository;

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
	public boolean evaluate(BundleTemplateEntry object) {
		return object.getBundle().equals(bundle);
	}
}
