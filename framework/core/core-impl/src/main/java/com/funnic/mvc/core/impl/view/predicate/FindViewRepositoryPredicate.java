package com.funnic.mvc.core.impl.view.predicate;

import com.funnic.mvc.core.impl.view.ViewRepository;
import org.apache.commons.collections4.Predicate;
import org.osgi.framework.ServiceReference;

/**
 * @author Per
 */
public class FindViewRepositoryPredicate implements Predicate<ViewRepository> {
	private final ServiceReference serviceReference;
	public FindViewRepositoryPredicate(final ServiceReference serviceReference) {
		this.serviceReference = serviceReference;
	}

	@Override
	public boolean evaluate(ViewRepository object) {
		return object.getServiceReference().equals(serviceReference);
	}
}
