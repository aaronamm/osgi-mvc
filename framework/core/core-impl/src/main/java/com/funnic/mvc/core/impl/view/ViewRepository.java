package com.funnic.mvc.core.impl.view;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

import java.net.URL;

/**
 * @author Per
 */
public interface ViewRepository {
	/**
	 * Resolve the supplied path and return a URL for it
	 *
	 * @param path The path to the view
	 * @return URL if found; null otherwise
	 */
	URL getView(String path);

	/**
	 * Retrieve the associated bundle for this repository
	 *
	 * @return
	 */
	Bundle getBundle();

	/**
	 *
	 * @return
	 */
	ServiceReference getServiceReference();
}
