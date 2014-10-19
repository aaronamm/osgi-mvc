package com.funnic.mvc.core.api.view;

import org.osgi.framework.Bundle;

import java.net.URL;

/**
 * @author Per
 */
public interface ViewRepositoryManager {

	/**
	 * Retrieves a view.
	 *
	 * @param bundle The bundle who is asking for the view
	 * @param path The path to the view.
	 * @return A URL to where the view is located
	 */
	ViewInfo getView(Bundle bundle, String path);
}
