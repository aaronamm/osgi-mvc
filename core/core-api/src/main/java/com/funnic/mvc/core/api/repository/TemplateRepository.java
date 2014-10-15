package com.funnic.mvc.core.api.repository;

import com.funnic.mvc.core.api.exceptions.TemplateInfoNotFoundException;
import org.osgi.framework.Bundle;

/**
 * @author Per
 */
public interface TemplateRepository {
	/**
	 * Check if this repository can handle the a file with the supplied path
	 *
	 * @param path The path to the template file
	 * @return TRUE if this repository can handle the supplied path; FALSE otherwise
	 */
	boolean accept(String path);

	/**
	 * Retrieves the template URL based on the supplied name.
	 *
	 * @param bundle The bundle that requests the resource
	 * @param path   The name to the template
	 * @return Return an url to the template if found; null otherwise.
	 */
	TemplateInfo getTemplateInfo(Bundle bundle, String path) throws TemplateInfoNotFoundException;
}
