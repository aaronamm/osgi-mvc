package com.funnic.mvc.core.api;

/**
 * Service responsible for generating URLs
 *
 * @author Per
 */
public interface LinkBuilder {

	/**
	 * Converts the supplied controller and action into a href path
	 *
	 * @param controller The name of the controller
	 * @param action The name of the action
	 * @return A path
	 */
	String getHref(String controller, String action);
}
