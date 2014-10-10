package com.funnic.mvc.core.api;

import org.osgi.framework.Bundle;

import java.net.URL;

/**
 * @author Per
 */
public interface TemplateRepositoryManager {

	/**
	 * Retrieves the template URL based on the supplied path.
	 *
	 * @param bundle The bundle that requests the template
	 * @param path   The path to the template
	 * @return Return an url to the template if found; null otherwise.
	 */
	URL getTemplateURL(Bundle bundle, String path);
}
