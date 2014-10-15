package com.funnic.mvc.core.api.exceptions;

import org.osgi.framework.Bundle;

/**
 * Exception thrown if template info is not found
 *
 * @author Per
 */
public class TemplateInfoNotFoundException extends RuntimeException {
	private final Bundle bundle;
	private final String path;

	public TemplateInfoNotFoundException(final Bundle bundle, final String path) {
		super("Cannot find template info for bundle: " + bundle.toString() + " at name: " + path);
		this.bundle = bundle;
		this.path = path;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public String getPath() {
		return path;
	}
}
