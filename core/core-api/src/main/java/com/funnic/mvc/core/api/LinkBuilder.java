package com.funnic.mvc.core.api;

/**
 * @author Per
 */
public interface LinkBuilder {

	/**
	 * Converts the supplied controller and action into a href path
	 *
	 * @param controller
	 * @param action
	 * @return
	 */
	String getHref(String controller, String action);
}
