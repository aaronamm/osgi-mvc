package com.funnic.mvc.core.impl.controllers;

import com.funnic.mvc.core.api.exceptions.ControllerNotFoundException;

/**
 * @author Per
 */
public interface ControllerRepository {

	/**
	 * Retrieves a controller with the supplied name
	 *
	 * @param name The name of the controller
	 * @return
	 */
	ControllerInfo getController(String name) throws ControllerNotFoundException;
}
