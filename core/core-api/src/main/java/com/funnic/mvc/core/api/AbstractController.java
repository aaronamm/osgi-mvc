package com.funnic.mvc.core.api;

import com.funnic.mvc.core.api.actions.ActionResult;
import com.funnic.mvc.core.api.actions.ForwardToActionResult;
import com.funnic.mvc.core.api.annotations.ComponentName;
import com.funnic.mvc.core.api.annotations.RequestMethod;
import com.funnic.mvc.core.api.actions.RediectToActionResult;
import com.funnic.mvc.core.api.actions.RenderViewResult;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Per
 */
public abstract class AbstractController implements Controller {

	private final String controllerName;
	private final List<ActionInfo> actions = new ArrayList<ActionInfo>();

	public AbstractController() {
		ComponentName componentName = getClass().getAnnotation(ComponentName.class);
		if (componentName != null)
			controllerName = componentName.name();
		else {
			String className = getClass().getSimpleName();
			int idx = className.lastIndexOf(".");
			controllerName = className.substring(idx + 1).replace("Controller", "").toLowerCase();
		}

		Method[] methods = getClass().getMethods();
		for (Method method : methods) {
			if (!Modifier.isPublic(method.getModifiers()))
				continue;

			final RequestMethod requestMethod = method.getAnnotation(RequestMethod.class);
			if (requestMethod == null)
				continue;

			final Class<?> returnType = method.getReturnType();
			if (!returnType.isAssignableFrom(ActionResult.class))
				continue;

			ActionInfo actionInfo = new ActionInfo(this, method, requestMethod);
			actions.add(actionInfo);
		}
	}

	@Override
	public String getControllerName() {
		return controllerName;
	}

	@Override
	public List<ActionInfo> getActions() {
		return actions;
	}

	/**
	 * Tell the MVC framework to render the view
	 *
	 * @param view
	 * @return
	 */
	protected ActionResult View(final String view) {
		return new RenderViewResult(view);
	}

	/**
	 * Tell the MVC framework to render the view with the given model objects
	 *
	 * @param view
	 * @param models
	 * @return
	 */
	protected ActionResult View(final String view, final Map<String, Object> models) {
		return new RenderViewResult(view, models);
	}

	/**
	 * Tell the MVC framework to redirect to the supplied action in this controller
	 *
	 * @param action The name of the action
	 * @return
	 */
	protected ActionResult RedirectToAction(final String action) {
		return new RediectToActionResult(action);
	}

	/**
	 * Tell the MVC framework to redirect to the supplied action in the supplied controller
	 *
	 * @param action The name of the action
	 * @param controller
	 * @return
	 */
	protected ActionResult RedirectToAction(final String action, final String controller) {
		return new RediectToActionResult(action, controller);
	}

	/**
	 * Tell the MVC framework to forward to the supplied action in this controller
	 *
	 * @param action The name of the action
	 * @return
	 */
	protected ActionResult ForwardToAction(final String action) {
		return new ForwardToActionResult(action);
	}

	/**
	 * Tell the MVC framework to forward to the supplied action in the supplied controller
	 *
	 * @param action The name of the action
	 * @param controller
	 * @return
	 */
	protected ActionResult ForwardToAction(final String action, final String controller) {
		return new ForwardToActionResult(action, controller);
	}
}
