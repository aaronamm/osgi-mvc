package com.funnic.mvc.core.impl.templating.freemarker;

import com.funnic.mvc.core.api.TemplateRepositoryManager;
import com.funnic.mvc.core.impl.templating.BundleTemplateEngine;
import freemarker.cache.URLTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.Map;

/**
 * @author Per
 */
public class BundleFreemarkerTemplateEngine extends URLTemplateLoader implements BundleTemplateEngine {
	private final Bundle bundle;
	private final Configuration cfg;
	private final TemplateRepositoryManager templateRepositoryManager;

	public BundleFreemarkerTemplateEngine(final Bundle bundle, final TemplateRepositoryManager templateRepositoryManager) {
		this.bundle = bundle;
		this.templateRepositoryManager = templateRepositoryManager;
		cfg = new Configuration();
		cfg.setIncompatibleImprovements(new freemarker.template.Version(2, 3, 20));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateUpdateDelay(1);
		cfg.setLocalizedLookup(false);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setTemplateLoader(this);
	}

	@Override
	public void process(String path, Map<String, Object> models, Writer writer) {
		try {
			final Template template = cfg.getTemplate("/" + path);
			template.process(models, writer);
		} catch (final IOException e) {
			throw new IllegalStateException("Could not process template: " + path, e);
		} catch (TemplateException e) {
			throw new IllegalStateException("Could not process template: " + path, e);
		}

	}

	@Override
	protected URL getURL(String path) {
		return templateRepositoryManager.getTemplateURL(bundle, path);
	}

	@Override
	public Bundle getBundle() {
		return bundle;
	}

	@Override
	public Version getBundleVersion() {
		return bundle.getVersion();
	}

	@Override
	public String getBundleName() {
		return bundle.getSymbolicName();
	}
}
