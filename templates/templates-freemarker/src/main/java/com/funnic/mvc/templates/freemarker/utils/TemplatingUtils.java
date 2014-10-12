package com.funnic.mvc.templates.freemarker.utils;

import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.Bundle;

/**
 * @author Per
 */
public class TemplatingUtils {

	public static boolean matchingBundleName(final String searchForName, final String bundleName) {
		if (StringUtils.isEmpty(searchForName))
			return true;
		return searchForName.equals(bundleName);
	}

	public static boolean isLocalPath(final String path) {
		return (path.startsWith("/") || path.startsWith("./") || path.startsWith("../"))
				&& !path.contains("://");
	}
}
