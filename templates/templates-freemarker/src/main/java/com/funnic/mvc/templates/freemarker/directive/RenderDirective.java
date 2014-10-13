package com.funnic.mvc.templates.freemarker.directive;

import com.funnic.mvc.core.api.renderer.ControllerRenderer;
import com.funnic.mvc.core.api.templating.TemplateEngineManager;
import com.funnic.mvc.templates.freemarker.FreemarkerTemplateEngine;
import freemarker.core.Environment;
import freemarker.template.*;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.collections4.MapUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author Per
 */
public class RenderDirective implements TemplateDirectiveModel {
	private static final String PARAM_NAME_CONTROLLER = "controller";
	private static final String PARAM_NAME_VIEW = "view";
	private static final String PARAM_NAME_ACTION = "action";
	private static final String PARAM_NAME_PARAMS = "params";

	private final ControllerRenderer controllerRenderer;
	private final TemplateEngineManager templateEngineManager;

	public RenderDirective(final ControllerRenderer controllerRenderer, final TemplateEngineManager templateEngineManager) {
		this.controllerRenderer = controllerRenderer;
		this.templateEngineManager = templateEngineManager;
	}

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		if (params.isEmpty()) {
			throw new TemplateModelException("render directive requires parameters.");
		}

		final TemplateModel viewModel = (TemplateModel) params.get(PARAM_NAME_VIEW);
		final TemplateModel paramsModel = (TemplateModel) params.get(PARAM_NAME_PARAMS);
		if (viewModel != null) {
			if (!(viewModel instanceof TemplateScalarModel)) {
				throw new TemplateException("Expected a scalar model for parameter '" + PARAM_NAME_VIEW + "' is instead " +
						viewModel.getClass().getName(), env);
			}
			final String view = ((TemplateScalarModel) viewModel).getAsString();
			Map<String, Object> actionParams = getUnwrappedMap(env, paramsModel);
			templateEngineManager.process(view, actionParams, env.getOut());
			return;
		}

		final TemplateModel controllerModel = (TemplateModel) params.get(PARAM_NAME_CONTROLLER);
		final TemplateModel actionModel = (TemplateModel) params.get(PARAM_NAME_ACTION);

		if (controllerModel == null) {
			throw new TemplateModelException("String value of '" + PARAM_NAME_CONTROLLER + "' is null");
		}
		if (actionModel == null) {
			throw new TemplateModelException("String value of '" + PARAM_NAME_ACTION + "' is null");
		}

		if (!(controllerModel instanceof TemplateScalarModel)) {
			throw new TemplateException("Expected a scalar model for parameter '" + PARAM_NAME_CONTROLLER + "' is instead " +
					controllerModel.getClass().getName(), env);
		}

		if (!(actionModel instanceof TemplateScalarModel)) {
			throw new TemplateException("Expected a scalar model for parameter '" + PARAM_NAME_ACTION + "' is instead " +
					actionModel.getClass().getName(), env);
		}

		final String controllerName = ((TemplateScalarModel) controllerModel).getAsString();
		final String actionName = ((TemplateScalarModel) actionModel).getAsString();
		Map<String, Object> actionParams = getUnwrappedMap(env, paramsModel);

		try {
			controllerRenderer.render(controllerName, actionName, actionParams, env.getOut());
		} catch (Exception e) {
			throw new TemplateException("Could not render the action: " + controllerName + "." + actionName, e, env);
		}
	}

	private Map<String, Object> getUnwrappedMap(Environment env, TemplateModel paramsModel) throws TemplateException {
		Map<String, Object> actionParams = MapUtils.EMPTY_SORTED_MAP;
		if (paramsModel != null) {
			// Convert params to a Map
			final Object unwrapped = DeepUnwrap.unwrap(paramsModel);
			if (!(unwrapped instanceof Map)) {
				throw new TemplateException("Expected '" + PARAM_NAME_PARAMS + "' to unwrap into a java.util.Map. It unwrapped into " +
						unwrapped.getClass().getName() + " instead.", env);
			}
			actionParams = (Map<String, Object>) unwrapped;
		}
		return actionParams;
	}
}
