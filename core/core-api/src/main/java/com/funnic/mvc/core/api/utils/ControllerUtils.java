package com.funnic.mvc.core.api.utils;

import com.funnic.mvc.core.api.AbstractController;
import com.funnic.mvc.core.api.annotations.ComponentName;

/**
 * @author Per
 */
public final class ControllerUtils {

	/**
	 * Retrieves the name of the controller based on the class.
	 *
	 * @param clazz
	 * @return
	 */
	public static String getControllerName(Class<? extends AbstractController> clazz) {
		final ComponentName componentName = clazz.getAnnotation(ComponentName.class);
		final String controllerName;
		if (componentName != null)
			controllerName = componentName.value().toLowerCase();
		else {
			String className = clazz.getSimpleName();
			int idx = className.lastIndexOf(".");
			controllerName = className.substring(idx + 1).replace("Controller", "").toLowerCase();
		}
		return controllerName;
	}
}
