package com.funnic.mvc.core.api.repository;

import org.osgi.framework.Bundle;

import java.net.URL;

/**
 * @author Per
 */
public class TemplateInfo {
	private final URL url;
	private final Bundle bundle;

	public TemplateInfo(final URL url, final Bundle bundle) {
		this.url = url;
		this.bundle = bundle;
	}

	public URL getUrl() {
		return url;
	}

	public Bundle getBundle() {
		return bundle;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TemplateInfo) {
			final TemplateInfo rhs = (TemplateInfo) obj;
			return rhs.getBundle().equals(bundle) && rhs.getUrl().equals(url);
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = url.hashCode();
		result = 31 * result + bundle.hashCode();
		return result;
	}
}
