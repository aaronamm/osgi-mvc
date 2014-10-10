package com.funnic.mvc.core.impl.controllers;

/**
 * @author Per
 */
public interface ControllerRepository {

	/**
	 * Retrieves a controller at the given path
	 *
	 * @param path
	 * @return
	 */
	ControllerInfo getController(String path);
}
