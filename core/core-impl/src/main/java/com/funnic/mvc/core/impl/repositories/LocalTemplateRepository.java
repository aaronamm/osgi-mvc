package com.funnic.mvc.core.impl.repositories;

import com.funnic.mvc.core.api.TemplateRepository;
import org.osgi.framework.Bundle;

import java.net.URL;

/**
 * @author Per
 */
public class LocalTemplateRepository implements TemplateRepository {
	@Override
	public String getPrefix() {
		return "/";
	}

	@Override
	public URL getTemplateURL(final Bundle bundle, final String path) {
		return bundle.getEntry(path);
	}

}
