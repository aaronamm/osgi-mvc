package com.funnic.mvc.core.impl.servlet;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Created by Per on 2014-09-25.
 */
public class RequestInfo {
	private final String requestURI;
	private final String controllerPath;
	private final String methodPath;
	private final String[] parameters;

	public RequestInfo(String requestURI) {
		if (StringUtils.startsWith(requestURI, "/")) {
			requestURI = requestURI.substring(1);
		}
		this.requestURI = "/" + requestURI;
		final String[] requestParts = requestURI.split("/");
		this.controllerPath = "/" + getOrDefault(requestParts, 0, "home");
		this.methodPath = "/" + getOrDefault(requestParts, 1, "index");
		if (requestParts.length >= 2)
			this.parameters = Arrays.copyOfRange(requestParts, 2, requestParts.length);
		else
			this.parameters = new String[0];
	}

	public String getOrDefault(String[] array, int idx, String defaultValue) {
		if (array.length <= idx)
			return defaultValue;

		return StringUtils.defaultIfEmpty(array[idx], defaultValue);
	}

	public String getRequestURI() {
		return requestURI;
	}

	public String getControllerPath() {
		return controllerPath;
	}

	public String getMethodPath() {
		return methodPath;
	}

	public String[] getParameters() {
		return parameters;
	}
}
