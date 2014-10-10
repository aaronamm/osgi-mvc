package com.funnic.mvc.core.impl.templating.freemarker;

import com.funnic.mvc.core.api.TemplateRepositoryManager;
import com.funnic.mvc.core.impl.templating.BundleTemplateEngine;
import com.funnic.mvc.core.impl.templating.BundleTemplateEngineFactory;
import org.osgi.framework.Bundle;

/**
 * @author Per
 */
public class BundleFreemarkerTemplateEngineFactory implements BundleTemplateEngineFactory {

	private final TemplateRepositoryManager templateRepositoryManager;

	public BundleFreemarkerTemplateEngineFactory(TemplateRepositoryManager templateRepositoryManager) {
		this.templateRepositoryManager = templateRepositoryManager;
	}

	@Override
	public BundleTemplateEngine create(final Bundle bundle) {
		//Enumeration<URL> entries = bundle.findEntries("/", "*.ftl", true);
		return new BundleFreemarkerTemplateEngine(bundle, templateRepositoryManager);
	}
}
