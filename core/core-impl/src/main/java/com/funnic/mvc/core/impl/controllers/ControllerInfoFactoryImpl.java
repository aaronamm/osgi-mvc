package com.funnic.mvc.core.impl.controllers;

import com.funnic.mvc.core.api.Controller;
import com.funnic.mvc.core.api.TemplateEngine;
import com.funnic.mvc.core.impl.converters.ParameterConverters;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Per
 */
public class ControllerInfoFactoryImpl implements ControllerInfoFactory {

	private final ParameterConverters parameterConverters;
	private final TemplateEngine templateEngine;

	public ControllerInfoFactoryImpl(final ParameterConverters parameterConverters, final TemplateEngine templateEngine) {
		this.parameterConverters = parameterConverters;
		this.templateEngine = templateEngine;
	}

	@Override
	public ControllerInfo create(final ServiceReference controllerReference) {
		final Bundle bundle = controllerReference.getBundle();
		final BundleContext bundleContext = bundle.getBundleContext();

		final Controller controller = (Controller) bundleContext.getService(controllerReference);
		return new ControllerInfo(bundleContext, controller, controllerReference, parameterConverters, templateEngine);
	}
}
