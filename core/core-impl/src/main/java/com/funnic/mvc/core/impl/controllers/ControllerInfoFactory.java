package com.funnic.mvc.core.impl.controllers;

import org.osgi.framework.ServiceReference;

/**
 * @author Per
 */
public interface ControllerInfoFactory {

	/**
	 * Create a ControllerInfo instance based on the supplied service reference
	 *
	 * @param controllerReference
	 * @return
	 */
	ControllerInfo create(ServiceReference controllerReference);
}
