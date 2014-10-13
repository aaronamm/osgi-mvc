package com.funnic.mvc.core.impl.templating;

import com.funnic.mvc.core.api.templating.TemplateEngine;
import com.funnic.mvc.core.api.templating.TemplateEngineManager;
import com.funnic.mvc.core.impl.predicates.GetTemplateEngineByPath;
import org.apache.commons.collections4.CollectionUtils;
import org.osgi.framework.Bundle;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Per
 */
public class TemplateEngineManagerImpl implements TemplateEngineManager {

	private final List<TemplateEngine> templateEngines = new ArrayList<TemplateEngine>();

	public void process(final Bundle bundle, final String path, final Map<String, Object> models, final Writer writer) {
		final TemplateEngine engine = CollectionUtils.find(templateEngines, new GetTemplateEngineByPath(path));
		if (engine == null) {
			throw new IllegalStateException("No engine found for template: " + path);
		}

		engine.process(bundle, path, models, writer);
	}

	@Override
	public void process(String path, Map<String, Object> models, Writer writer) {
		final TemplateEngine engine = CollectionUtils.find(templateEngines, new GetTemplateEngineByPath(path));
		if (engine == null) {
			throw new IllegalStateException("No engine found for template: " + path);
		}

		engine.process(path, models, writer);

	}

	public void addTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngines.add(templateEngine);
	}

	public void removeTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngines.remove(templateEngine);
	}
}
