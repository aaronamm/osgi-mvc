package com.funnic.mvc.core.api.view;

import org.osgi.framework.Bundle;

import java.net.URL;
import java.util.List;

/**
 * @author Per
 */
public interface ViewRepositoryFactory {

	/**
	 * Create and register a view repository
	 *
	 * @param bundle The bundle where the factory is created
	 * @param path The root directory where to look for the views
	 * @param filePattern File pattern
	 * @return A view repository
	 */
	List<URL> resolveViews(Bundle bundle, String path, String filePattern);
}
