package com.funnic.mvc.core.impl.repository;

import com.funnic.mvc.core.api.exceptions.TemplateInfoNotFoundException;
import com.funnic.mvc.core.api.repository.TemplateInfo;
import com.funnic.mvc.core.api.repository.TemplateRepository;
import com.funnic.mvc.core.api.repository.TemplateRepositoryManager;
import com.funnic.mvc.core.impl.predicates.GetTemplateRepositoryPredicate;
import org.apache.commons.collections4.CollectionUtils;
import org.osgi.framework.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Per
 */
public class TemplateRepositoryManagerImpl implements TemplateRepositoryManager {
	private final List<TemplateRepository> repositories = new ArrayList<TemplateRepository>();

	public void addTemplateRepository(final TemplateRepository repository) {
		repositories.add(repository);
	}

	public void removeTemplateRepository(final TemplateRepository repository) {
		repositories.remove(repository);
	}

	@Override
	public TemplateInfo getTemplateInfo(Bundle bundle, String path) throws TemplateInfoNotFoundException {
		final TemplateRepository repository = CollectionUtils.find(repositories, new GetTemplateRepositoryPredicate(path));
		if (repository != null) {
			return repository.getTemplateInfo(bundle, path);
		}

		throw new IllegalArgumentException("Unknown repository type for name: " + path);
	}
}
