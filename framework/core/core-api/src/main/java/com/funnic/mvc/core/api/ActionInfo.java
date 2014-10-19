package com.funnic.mvc.core.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Per
 */
public class ActionInfo {
	private final Controller controller;
	private final Method method;
	private final String name;
	private final RequestType[] requestTypes;

	public ActionInfo(final Controller controller, final Method method, final String name, final RequestType[] requestTypes) {
		this.controller = controller;
		this.method = method;
		this.name = name;
		this.requestTypes = requestTypes;
	}

	public Controller getController() {
		return controller;
	}

	public Method getMethod() {
		return method;
	}

	public String getName() {
		return name;
	}

	public RequestType[] getRequestTypes() {
		return requestTypes;
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
