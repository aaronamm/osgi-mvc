package com.funnic.mvc.core.api.view;

import org.osgi.framework.Bundle;

import java.net.URL;

/**
 * @author Per
 */
public class ViewInfo {
	private final URL url;
	private final Bundle bundle;

	public ViewInfo(URL url, Bundle bundle) {
		this.url = url;
		this.bundle = bundle;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public URL getUrl() {
		return url;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ViewInfo) {
			final ViewInfo rhs = (ViewInfo) obj;
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
