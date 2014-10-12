package com.funnic.mvc.core.api;

import com.funnic.mvc.core.api.annotations.RequestMethod;
import com.funnic.mvc.core.api.annotations.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Per
 */
public class ActionInfo {
	private final Controller controller;
	private final Method method;
	private final RequestMethod requestMethod;

	public ActionInfo(final Controller controller, final Method method, final RequestMethod requestMethod) {
		this.controller = controller;
		this.method = method;
		this.requestMethod = requestMethod;
	}

	public Controller getController() {
		return controller;
	}

	public Method getMethod() {
		return method;
	}

	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	/**
	 * Invoke this method
	 *
	 * @param params
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public Object invoke(final Object[] params) throws InvocationTargetException, IllegalAccessException {
		return this.method.invoke(this.controller, params);
	}

}
