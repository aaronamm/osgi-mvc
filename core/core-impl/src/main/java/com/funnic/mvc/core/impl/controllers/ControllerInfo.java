package com.funnic.mvc.core.impl.controllers;

import com.funnic.mvc.core.api.*;
import com.funnic.mvc.core.impl.converters.ParameterConverters;
import com.funnic.mvc.core.impl.exceptions.ControllerMethodNotFound;
import com.funnic.mvc.core.impl.exceptions.CouldNotInvokeMethodException;
import com.funnic.mvc.core.impl.servlet.RequestInfo;
import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.io.Writer;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Per
 */
public class ControllerInfo {
	private final BundleContext bundleContext;
	private final Bundle bundle;
	private final Controller controller;
	private final ServiceReference serviceReference;
	private final ParameterConverters parameterConverters;
	private final TemplateEngine templateEngine;
	private Map<MethodInfoKey, MethodInfo> accessibleMethods = new HashMap<MethodInfoKey, MethodInfo>();

	public ControllerInfo(BundleContext bundleContext, final Controller controller, final ServiceReference serviceReference,
						  final ParameterConverters parameterConverters, final TemplateEngine templateEngine) {
		this.bundleContext = bundleContext;
		this.bundle = bundleContext.getBundle();
		this.controller = controller;
		this.serviceReference = serviceReference;
		this.parameterConverters = parameterConverters;
		this.templateEngine = templateEngine;
		for (final MethodInfo methodInfo : controller.getAccessibleMethods()) {
			RequestType[] types = methodInfo.getRequestMethod().types();
			if (types.length == 0)
				types = new RequestType[]{RequestType.GET, RequestType.POST};

			final String path = StringUtils.defaultIfEmpty(methodInfo.getRequestMethod().path(),
					"/" + methodInfo.getMethod().getName());
			for (final RequestType type : types) {
				accessibleMethods.put(new MethodInfoKey(path, type), methodInfo);
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		return serviceReference.equals(obj);
	}

	public String getRootPath() {
		return controller.getRootPath();
	}

	public void destroy() {
		bundleContext.ungetService(serviceReference);
	}

	public void invoke(final RequestInfo requestInfo, final RequestType type, final Writer writer) {
		final String methodPath = requestInfo.getMethodPath();
		final String[] parameters = requestInfo.getParameters();

		final MethodInfo methodInfo = accessibleMethods.get(new MethodInfoKey(methodPath, type));
		if (methodInfo == null)
			throw new ControllerMethodNotFound("Method: '" + methodPath + "' receiving type: " + type + " is not found");

		final Method methodToInvoke = methodInfo.getMethod();
		final Object[] realParameters = parameterConverters.convert(parameters, methodToInvoke.getParameterTypes());
		try {
			final ActionResult result = (ActionResult) methodInfo.invoke(realParameters);
			templateEngine.process(bundle, result.getView(), result.getModels(), writer);
		} catch (Exception e) {
			throw new CouldNotInvokeMethodException("Error while invoking method: '" + methodPath + "' receiving type: " + type, e);
		}
	}
}
