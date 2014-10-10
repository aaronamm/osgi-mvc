package com.funnic.mvc.core.impl.templating;

import org.osgi.framework.Bundle;

/**
 * @author Per
 */
public interface BundleTemplateEngineFactory {

	/**
	 * Create a bundle template engine based on the supplied bundle
	 *
	 * @param bundle The bundle
	 * @return A bundle template engine instance
	 */
	BundleTemplateEngine create(Bundle bundle);
}
