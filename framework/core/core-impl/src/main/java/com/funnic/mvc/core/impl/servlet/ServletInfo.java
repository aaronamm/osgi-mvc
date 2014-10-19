package com.funnic.mvc.core.impl.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Per
 */
public class ServletInfo {

	private static final ThreadLocal<HttpServletRequest> localRequest = new ThreadLocal<HttpServletRequest>();
	private static final ThreadLocal<HttpServletResponse> localResponse = new ThreadLocal<HttpServletResponse>();

	public static HttpServletRequest getRequest() {
		return localRequest.get();
	}

	public static HttpServletResponse getResponse() {
		return localResponse.get();
	}

	public static void set(final HttpServletRequest request, final HttpServletResponse response) {
		localRequest.set(request);
		localResponse.set(response);
	}

	public static void clear() {
		localRequest.remove();
		localResponse.remove();
	}
}
