package com.funnic.mvc.core.impl;

import com.funnic.mvc.core.api.LinkBuilder;

/**
 * @author Per
 */
public class LinkBuilderImpl implements LinkBuilder {

	private final String contextPath;

	public LinkBuilderImpl(String contextPath) {
		this.contextPath = contextPath;
	}

	@Override
	public String getHref(String controller, String action) {
		StringBuilder sb = new StringBuilder(contextPath);
		sb.append("/");
		sb.append(controller);
		sb.append("/");
		sb.append(action);
		return sb.toString();
	}
}
