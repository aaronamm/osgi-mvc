package com.funnic.mvc.core.api;

import java.util.List;

/**
 * @author Per
 */
public interface Controller {

	/**
	 * Retrieves this controllers root-name
	 *
	 * @return The controller root name
	 */
	String getControllerName();

	/**
	 * Retrieves the methods accessible by the mvc framework
	 *
	 * @return All methods accessible by the mvc framework
	 */
	List<ActionInfo> getActions();
}
