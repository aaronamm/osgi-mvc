package com.funnic.mvc.core.api;

import com.funnic.mvc.core.api.annotations.ComponentName;
import com.funnic.mvc.core.api.annotations.RequestMethod;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Per
 */
public abstract class AbstractController implements Controller {

	private final String controllerName;
	private final List<ActionInfo> actions = new ArrayList<ActionInfo>();

	public AbstractController() {
		ComponentName componentName = getClass().getAnnotation(ComponentName.class);
		if (componentName != null)
			controllerName = componentName.name();
		else {
			String className = getClass().getSimpleName();
			int idx = className.lastIndexOf(".");
			controllerName = className.substring(idx + 1).replace("Controller", "").toLowerCase();
		}

		Method[] methods = getClass().getMethods();
		for (Method method : methods) {
			if (!Modifier.isPublic(method.getModifiers()))
				continue;

			final RequestMethod requestMethod = method.getAnnotation(RequestMethod.class);
			if (requestMethod == null)
				continue;

			final Class<?> returnType = method.getReturnType();
			if (!returnType.isAssignableFrom(ActionResult.class))
				continue;

			ActionInfo actionInfo = new ActionInfo(this, method, requestMethod);
			actions.add(actionInfo);
		}
	}

	@Override
	public String getControllerName() {
		return controllerName;
	}

	@Override
	public List<ActionInfo> getActions() {
		return actions;
	}
}
