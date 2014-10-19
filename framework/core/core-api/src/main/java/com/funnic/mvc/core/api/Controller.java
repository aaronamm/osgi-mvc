package com.funnic.mvc.core.api;

import java.util.List;

/**
 * @author Per
 */
public interface Controller {
	/**
	 *
	 * @return The class name
	 */
	String getClassName();

	/**
	 * Retrieves the methods accessible by the mvc framework
	 *
	 * @return All methods accessible by the mvc framework
	 */
	List<ActionInfo> getActions();
}
