package com.funnic.mvc.core.api;

import com.funnic.mvc.core.api.annotations.Path;
import com.funnic.mvc.core.api.annotations.RequestMethod;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Per
 */
public abstract class AbstractController implements Controller {

	private final String rootPath;
	private final List<MethodInfo> accessibleMethods = new ArrayList<MethodInfo>();

	public AbstractController() {
		Path path = getClass().getAnnotation(Path.class);
		if (path != null)
			rootPath = path.value();
		else {
			String className = getClass().getSimpleName();
			int idx = className.lastIndexOf(".");
			rootPath = className.substring(idx + 1);
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

			MethodInfo methodInfo = new MethodInfo(this, method, requestMethod);
			accessibleMethods.add(methodInfo);
		}
	}

	@Override
	public String getRootPath() {
		return rootPath;
	}

	@Override
	public List<MethodInfo> getAccessibleMethods() {
		return accessibleMethods;
	}
}
