package com.funnic.mvc.core.impl.repositories;

import com.funnic.mvc.core.api.TemplateRepository;
import com.funnic.mvc.core.api.TemplateRepositoryManager;
import com.funnic.mvc.core.impl.predicates.GetTemplateRepositoryPredicate;
import org.apache.commons.collections4.CollectionUtils;
import org.osgi.framework.Bundle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Per
 */
public class TemplateRepositoryManagerImpl implements TemplateRepositoryManager {
	private List<TemplateRepository> repositories = new ArrayList<TemplateRepository>();

	public TemplateRepositoryManagerImpl(List<TemplateRepository> repositories) {
		this.repositories.addAll(repositories);
	}

	@Override
	public URL getTemplateURL(Bundle bundle, String path) {
		final TemplateRepository repository = CollectionUtils.find(repositories, new GetTemplateRepositoryPredicate(path));
		if (repository != null) {
			return repository.getTemplateURL(bundle, path);
		}

		throw new IllegalArgumentException("Unknown repository type for path: " + path);
	}

	public void addTemplateRepository(TemplateRepository repository) {
		repositories.add(repository);
	}

	public void removeTemplateRepository(TemplateRepository repository) {
		repositories.remove(repository);
	}
}
