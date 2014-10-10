package com.funnic.mvc.core.api;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Per
 */
public interface Controller {

	/**
	 * Retrieves this controllers root-path
	 *
	 * @return The controller root path
	 */
	String getRootPath();

	/**
	 * Retrieves the methods accessible by the mvc framework
	 *
	 * @return All methods accessible by the mvc framework
	 */
	List<MethodInfo> getAccessibleMethods();
}
