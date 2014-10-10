package com.funnic.mvc.core.api;

import org.osgi.framework.Bundle;

import java.io.Writer;
import java.util.Map;

/**
 * @author Per
 */
public interface TemplateEngine {
	/**
	 * Process the supplied template path with the given models and put the result into the writer
	 *
	 * @param bundle The bundle requesting the template file data
	 * @param path   The path to the template file
	 * @param models The models we want to use in the template file
	 * @param writer Where the result is written to
	 */
	void process(Bundle bundle, String path, Map<String, Object> models, Writer writer);
}
