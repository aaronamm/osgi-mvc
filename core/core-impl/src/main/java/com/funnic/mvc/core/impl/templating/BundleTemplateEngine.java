package com.funnic.mvc.core.impl.templating;

import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

import java.io.Writer;
import java.util.Map;

/**
 * @author Per
 */
public interface BundleTemplateEngine {

	/**
	 * Process the supplied template path with the given models and put the result into the writer
	 *
	 * @param path   The path to the template file
	 * @param models The models we want to use in the template file
	 * @param writer Where the result is written to
	 */
	void process(String path, Map<String, Object> models, Writer writer);

	/**
	 * Retrieves the bundle
	 *
	 * @return
	 */
	Bundle getBundle();

	/**
	 * Retrieves the version
	 *
	 * @return
	 */
	Version getBundleVersion();

	/**
	 * Retrieves the bundle name
	 *
	 * @return
	 */
	String getBundleName();
}
