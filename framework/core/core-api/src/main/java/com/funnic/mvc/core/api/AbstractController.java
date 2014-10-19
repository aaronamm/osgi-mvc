package com.funnic.mvc.core.api;

import com.funnic.mvc.core.api.actions.ActionResult;
import com.funnic.mvc.core.api.actions.ForwardToActionResult;
import com.funnic.mvc.core.api.actions.RediectToActionResult;
import com.funnic.mvc.core.api.actions.RenderViewResult;
import com.funnic.mvc.core.api.annotations.Action;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Per
 */
public abstract class AbstractController implements Controller {

	private final List<ActionInfo> actions = new ArrayList<ActionInfo>();

	public AbstractController() {
		final Method[] methods = getClass().getMethods();
		for (Method method : methods) {
			if (!Modifier.isPublic(method.getModifiers()))
				continue;

			final Class<?> returnType = method.getReturnType();
			if (!returnType.isAssignableFrom(ActionResult.class))
				continue;

			final Action actionAttribute = method.getAnnotation(Action.class);
			final String name = actionAttribute != null ? actionAttribute.name().toLowerCase() : method.getName().toLowerCase();
			final RequestType[] requestTypes = actionAttribute != null ? actionAttribute.types() : new RequestType[]{RequestType.GET, RequestType.POST};
			ActionInfo actionInfo = new ActionInfo(this, method, name.isEmpty() ? method.getName().toLowerCase() : name, requestTypes);
			actions.add(actionInfo);
		}
	}

	@Override
	public String getClassName() {
		return getClass().getSimpleName();
	}

	@Override
	public List<ActionInfo> getActions() {
		return actions;
	}

	/**
	 * Tell the MVC framework to render the supplied view
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
	 * @param action     The name of the action
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
	 * Tell the MVC framework to forward to the supplied action in this controller
	 *
	 * @param action     The name of the action
	 * @param parameters Parameters to the forwarded action
	 * @return
	 */
	protected ActionResult ForwardToAction(final String action, final Map<String, Object> parameters) {
		return new ForwardToActionResult(action, parameters);
	}

	/**
	 * Tell the MVC framework to forward to the supplied action in the supplied controller
	 *
	 * @param action     The name of the action
	 * @param controller The controller
	 * @return
	 */
	protected ActionResult ForwardToAction(final String action, final String controller) {
		return new ForwardToActionResult(action, controller);
	}

	/**
	 * Tell the MVC framework to forward to the supplied action in the supplied controller
	 *
	 * @param action     The name of the action
	 * @param controller The controller
	 * @param parameters Parameters to the forwarded action
	 * @return
	 */
	protected ActionResult ForwardToAction(final String action, final String controller, final Map<String, Object> parameters) {
		return new ForwardToActionResult(action, controller, parameters);
	}
}
