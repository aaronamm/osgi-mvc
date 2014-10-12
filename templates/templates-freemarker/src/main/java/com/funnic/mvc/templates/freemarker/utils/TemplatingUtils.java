package com.funnic.mvc.templates.freemarker.utils;

import org.osgi.framework.Bundle;

/**
 * @author Per
 */
public class TemplatingUtils {

	public static boolean matchingBundleName(final String searchForName, final String bundleName) {
		if (searchForName == null)
			return true;
		return searchForName.equals(bundleName);
	}

	public static boolean isLocalPath(final String path) {
		return (path.startsWith("/") || path.startsWith("./") || path.startsWith("../"))
				&& !path.contains("://");
	}
}
