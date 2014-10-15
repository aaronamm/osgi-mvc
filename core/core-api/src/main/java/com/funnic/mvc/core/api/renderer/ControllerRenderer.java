package com.funnic.mvc.core.api.renderer;

import com.funnic.mvc.core.api.RequestType;

import java.io.Writer;
import java.util.Map;

/**
 * @author Per
 */
public interface ControllerRenderer {
	/**
	 * Render an action on a controller with the given parameters. Put the result into the writer instance
	 *
	 * @param controller The controller we want to render
	 * @param action The action we want to render
	 * @param parameters The parameters to the action
	 * @param writer Where the result is written to
	 * @throws com.funnic.mvc.core.api.exceptions.ControllerNotFoundException
	 * @throws com.funnic.mvc.core.api.exceptions.ActionNotFoundException
	 * @throws com.funnic.mvc.core.api.exceptions.RenderException
	 */
	void render(String controller, String action, Map<String, Object> parameters, Writer writer);

	/**
	 * Render an action on a controller. All parameters are resolved using the built-in parameter resolver. The result is then put into the writer instance
	 *
	 * @param controller The controller we want to render
	 * @param action The action we want to render
	 * @param type The type of the request
	 * @param writer Where the result is written to
	 * @throws com.funnic.mvc.core.api.exceptions.ControllerNotFoundException
	 * @throws com.funnic.mvc.core.api.exceptions.ActionNotFoundException
	 * @throws com.funnic.mvc.core.api.exceptions.RenderException
	 */
	void render(String controller, String action, RequestType type, Writer writer);
}
