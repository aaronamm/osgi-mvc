package com.funnic.mvc.core.impl.templating.predicates;

import com.funnic.mvc.core.api.templating.TemplateEngine;
import org.apache.commons.collections4.Predicate;

/**
 * @author Per
 */
public class GetTemplateEngineByPath implements Predicate<TemplateEngine> {
	private final String path;

	public GetTemplateEngineByPath(final String path) {
		this.path = path;
	}

	@Override
	public boolean evaluate(final TemplateEngine object) {
		return object.accept(path);
	}
}
