package com.funnic.mvc.core.api.renderer;

import com.funnic.mvc.core.api.exceptions.ActionNotFoundException;
import com.funnic.mvc.core.api.exceptions.ControllerNotFoundException;
import com.funnic.mvc.core.api.exceptions.RenderException;

import java.io.Writer;
import java.util.Map;

/**
 * @author Per
 */
public interface ControllerRenderer {
	/**
	 * Render an action on a controller with the given parameters. Put the result into the writer instance
	 *
	 * @param controller
	 * @param action
	 * @param parameters
	 * @param writer
	 * @throws ControllerNotFoundException
	 * @throws ActionNotFoundException
	 */
	void render(String controller, String action, Map<String, Object> parameters, Writer writer) throws ControllerNotFoundException, ActionNotFoundException, RenderException;

	/**
	 * Render an action on a controller. All parameters are resolved using the built-in parameter resolver. The result is then put into the writer instance
	 *
	 * @param controller
	 * @param action
	 * @param writer
	 * @throws ControllerNotFoundException
	 * @throws ActionNotFoundException
	 */
	void render(String controller, String action, Writer writer) throws ControllerNotFoundException, ActionNotFoundException, RenderException;
}
