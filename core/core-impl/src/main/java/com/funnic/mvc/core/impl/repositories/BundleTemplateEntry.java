package com.funnic.mvc.core.impl.repositories;

import org.osgi.framework.Bundle;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Per
 */
public class BundleTemplateEntry {

	private final Bundle bundle;
	private final Map<String, URL> templates = new HashMap<String, URL>();

	public BundleTemplateEntry(final Bundle bundle, final Enumeration<URL> entries) {
		this.bundle = bundle;
		while (entries.hasMoreElements()) {
			final URL entry = entries.nextElement();

			String templatePath = getTemplatePath(entry);
			templates.put(templatePath, entry);

			String bundleTemplateID = getBundleTemplatePath(bundle, templatePath);
			templates.put(bundleTemplateID, entry);

			String bundleVersionTemplateID = getBundleVersionTemplatePath(bundle, templatePath);
			templates.put(bundleVersionTemplateID, entry);
		}
	}

	/**
	 * Retrieves a URL based on the supplied path
	 *
	 * @param path
	 * @return
	 */
	public URL getTemplateURL(final String path) {
		return templates.get(path);
	}

	public Bundle getBundle() {
		return this.bundle;
	}

	private static String getTemplatePath(URL url) {
		return url.getPath();
	}

	private static String getBundleVersionTemplatePath(Bundle bundle, String templatePath) {
		String symbolicName = bundle.getSymbolicName();
		return "bundle://" + symbolicName + ":" + bundle.getVersion() + templatePath;
	}

	private static String getBundleTemplatePath(Bundle bundle, String templatePath) {
		String symbolicName = bundle.getSymbolicName();
		return "bundle://" + symbolicName + templatePath;
	}
}
