package com.funnic.mvc.core.impl.servlet;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Per on 2014-09-25.
 */
public class RequestInfo {
	private final String requestURI;
	private final String controllerName;
	private final String actionName;

	public RequestInfo(String requestURI) {
		if (StringUtils.startsWith(requestURI, "/")) {
			requestURI = requestURI.substring(1);
		}
		this.requestURI = "/" + requestURI;
		final String[] requestParts = requestURI.split("/");
		this.controllerName = getOrDefault(requestParts, 0, "home");
		this.actionName = getOrDefault(requestParts, 1, "index");
	}

	public String getOrDefault(String[] array, int idx, String defaultValue) {
		if (array.length <= idx)
			return defaultValue;

		return StringUtils.defaultIfEmpty(array[idx], defaultValue);
	}

	public String getRequestURI() {
		return requestURI;
	}

	public String getControllerName() {
		return controllerName;
	}

	public String getActionName() {
		return actionName;
	}
}
