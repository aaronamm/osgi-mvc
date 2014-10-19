package com.funnic.mvc.core.impl.predicates;

import com.funnic.mvc.core.impl.controllers.ControllerInfo;
import org.apache.commons.collections4.Predicate;
import org.osgi.framework.ServiceReference;

/**
 * @author Per
 */
public class ControllerFromServiceReferencePredicate implements Predicate<ControllerInfo> {
	private final ServiceReference serviceReference;

	public ControllerFromServiceReferencePredicate(final ServiceReference serviceReference) {
		this.serviceReference = serviceReference;
	}

	@Override
	public boolean evaluate(ControllerInfo info) {
		return info.equals(serviceReference);
	}
}
