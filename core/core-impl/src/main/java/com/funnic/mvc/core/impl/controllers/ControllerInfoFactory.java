package com.funnic.mvc.core.impl.controllers;

import org.osgi.framework.ServiceReference;

/**
 * @author Per
 */
public interface ControllerInfoFactory {

	/**
	 * @param controllerReference
	 * @return
	 */
	ControllerInfo create(ServiceReference controllerReference);
}
