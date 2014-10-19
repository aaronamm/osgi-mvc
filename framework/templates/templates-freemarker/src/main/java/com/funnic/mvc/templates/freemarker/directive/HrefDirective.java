package com.funnic.mvc.templates.freemarker.directive;

import com.funnic.mvc.core.api.LinkBuilder;
import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.util.Map;

/**
 * @author Per
 */
public class HrefDirective implements TemplateDirectiveModel {
	private static final String PARAM_NAME_CONTROLLER = "controller";
	private static final String PARAM_NAME_ACTION = "action";

	private final LinkBuilder linkBuilder;

	public HrefDirective(final LinkBuilder linkBuilder) {
		this.linkBuilder = linkBuilder;
	}

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		if (params.isEmpty()) {
			throw new TemplateModelException("href directive requires parameters.");
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

		env.getOut().write(linkBuilder.getHref(controllerName, actionName));
	}
}
