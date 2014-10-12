package com.funnic.mvc.core.api.templating;

import org.osgi.framework.Bundle;

import java.io.Writer;
import java.util.Map;

/**
 * @author Per
 */
public interface TemplateEngine {

	/**
	 * Check if this template engine can handle the supplied file name
	 *
	 * @param path The name to the template file
	 * @return
	 */
	boolean accept(String path);

	/**
	 * Process the supplied template name with the given models and put the result into the writer
	 *
	 * @param bundle The bundle requesting the template file data
	 * @param path   The name to the template file
	 * @param models The models we want to use in the template file
	 * @param writer Where the result is written to
	 */
	void process(Bundle bundle, String path, Map<String, Object> models, Writer writer);

	/**
	 * Process the supplied template name with the given models and put the result into the writer
	 *
	 * @param path   The name to the template file
	 * @param models The models we want to use in the template file
	 * @param writer Where the result is written to
	 */
	void process(String path, Map<String, Object> models, Writer writer);
}
