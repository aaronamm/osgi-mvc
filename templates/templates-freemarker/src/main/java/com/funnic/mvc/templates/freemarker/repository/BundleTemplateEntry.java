package com.funnic.mvc.templates.freemarker.repository;

import com.funnic.mvc.core.api.repository.TemplateInfo;
import org.osgi.framework.Bundle;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Per
 */
public class BundleTemplateEntry {
	private final Bundle bundle;
	private final Map<String, TemplateInfo> templates = new HashMap<String, TemplateInfo>();

	public BundleTemplateEntry(final Bundle bundle, final List<URL> entries) {
		this.bundle = bundle;
		for (URL entry : entries) {
			final TemplateInfo info = new TemplateInfo(entry, bundle);

			final String templatePath = getTemplatePath(entry);
			templates.put(templatePath, info);

			final String bundleTemplateID = getBundleTemplatePath(bundle, templatePath);
			templates.put(bundleTemplateID, info);

			final String bundleVersionTemplateID = getBundleVersionTemplatePath(bundle, templatePath);
			templates.put(bundleVersionTemplateID, info);
		}
	}

	public TemplateInfo getTemplateInfo(final String path) {
		return templates.get(path);
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

	public Bundle getBundle() {
		return bundle;
	}
}
