package com.funnic.mvc.core.impl.predicates;

import com.funnic.mvc.core.api.repository.TemplateRepository;
import org.apache.commons.collections4.Predicate;

/**
 * @author Per
 */
public class GetTemplateRepositoryPredicate implements Predicate<TemplateRepository> {
	private final String path;

	public GetTemplateRepositoryPredicate(String path) {
		this.path = path;
	}

	@Override
	public boolean evaluate(TemplateRepository object) {
		return object.accept(path);
	}
}
