package com.funnic.mvc.core.api.templating;

import org.osgi.framework.Bundle;

import java.io.Writer;
import java.util.Map;

/**
 * @author Per
 */
public interface TemplateEngineManager {
	/**
	 * Process the supplied template name with the given models and put the result into the writer
	 *
	 * @param bundle The bundle requesting the template file data
	 * @param path   The name to the template file
	 * @param models The models we want to use in the template file
	 * @param writer Where the result is written to
	 */
	void process(final Bundle bundle, final String path, final Map<String, Object> models, final Writer writer);
}
