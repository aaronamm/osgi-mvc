package com.funnic.mvc.core.impl.controllers;

import com.funnic.mvc.core.api.ActionInfo;
import com.funnic.mvc.core.api.LinkBuilder;
import com.funnic.mvc.core.api.RequestType;
import com.funnic.mvc.core.api.actions.ActionResult;
import com.funnic.mvc.core.api.actions.ForwardToActionResult;
import com.funnic.mvc.core.api.actions.RediectToActionResult;
import com.funnic.mvc.core.api.actions.RenderViewResult;
import com.funnic.mvc.core.api.annotations.ActionParam;
import com.funnic.mvc.core.api.exceptions.RenderException;
import com.funnic.mvc.core.api.renderer.ControllerRenderer;
import com.funnic.mvc.core.api.templating.TemplateEngineManager;
import com.funnic.mvc.core.impl.servlet.ServletInfo;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.osgi.framework.Bundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Per
 */
public class ControllerRendererImpl implements ControllerRenderer {

	private final ControllerRepository controllerRepository;
	private final TemplateEngineManager templateEngine;
	private final LinkBuilder linkBuilder;

	public ControllerRendererImpl(final ControllerRepository controllerRepository, final TemplateEngineManager templateEngine, final LinkBuilder linkBuilder) {
		this.controllerRepository = controllerRepository;
		this.templateEngine = templateEngine;
		this.linkBuilder = linkBuilder;
	}

	@Override
	public void render(final String controller, final String action, final Map<String, Object> parameters, final Writer writer) {
		final ControllerInfo controllerInfo = this.controllerRepository.getController(controller);
		final ActionInfo actionInfo = controllerInfo.getAction(action);
		List<Object> resolvedParameters = resolveParameters(actionInfo, parameters);

		try {
			processAction(controllerInfo.getBundle(), controllerInfo, actionInfo, resolvedParameters.toArray(new Object[]{}), writer);
		} catch (InvocationTargetException e) {
			throw new RenderException("Could not render action: " + controller + "." + action, e);
		} catch (IllegalAccessException e) {
			throw new RenderException("Could not render action: " + controller + "." + action, e);
		} catch (IOException e) {
			throw new RenderException("Could not render action: " + controller + "." + action, e);
		}
	}

	@Override
	public void render(String controller, String action, RequestType type, Writer writer) {
		final ControllerInfo controllerInfo = this.controllerRepository.getController(controller);
		final ActionInfo actionInfo = controllerInfo.getAction(action, type);
		List<Object> resolvedParameters = resolveParameters(actionInfo);

		try {
			processAction(controllerInfo.getBundle(), controllerInfo, actionInfo, resolvedParameters.toArray(new Object[]{}), writer);
		} catch (InvocationTargetException e) {
			throw new RenderException("Could not render action: " + controller + "." + action, e);
		} catch (IllegalAccessException e) {
			throw new RenderException("Could not render action: " + controller + "." + action, e);
		} catch (IOException e) {
			throw new RenderException("Could not render action: " + controller + "." + action, e);
		}
	}

	private void processAction(Bundle bundle, ControllerInfo controllerInfo, ActionInfo action, Object[] parameters, final Writer writer) throws
			InvocationTargetException, IllegalAccessException, IOException {
		final ActionResult result = (ActionResult) action.invoke(parameters);
		if (result instanceof RenderViewResult) {
			final RenderViewResult view = (RenderViewResult) result;
			templateEngine.process(bundle, view.getView(), view.getModels(), writer);
		} else if (result instanceof RediectToActionResult) {
			final RediectToActionResult redirect = (RediectToActionResult) result;
			final String controller = StringUtils.defaultString(redirect.getController(), controllerInfo.getRootPath());
			final HttpServletResponse response = ServletInfo.getResponse();
			final String href = linkBuilder.getHref(controller, redirect.getAction());
			response.sendRedirect(href);
		} else if (result instanceof ForwardToActionResult) {
			final ForwardToActionResult forward = (ForwardToActionResult) result;
			final String controller = StringUtils.defaultString(forward.getController(), controllerInfo.getRootPath());
			render(controller, forward.getAction(), forward.getParameters(), writer);
		}
	}

	private List<Object> resolveParameters(ActionInfo actionInfo) {
		//
		// TODO Put this into a service
		//

		final Method method = actionInfo.getMethod();
		final Class<?>[] paramTypes = method.getParameterTypes();
		final Annotation[][] paramAnnot = method.getParameterAnnotations();
		Validate.isTrue(paramTypes.length == paramAnnot.length, "The number of parameters do not match the number of annotations");

		final HttpServletRequest request = ServletInfo.getRequest();

		List<Object> parameters = new ArrayList<Object>();
		int length = paramTypes.length;
		for (int i = 0; i < length; ++i) {
			final Class<?> type = paramTypes[i];

			final String parameterName = getParameterName(paramAnnot[i]);
			final String parameterValue = request.getParameter(parameterName);

			final Object parameter = convert(type, parameterValue);

			parameters.add(parameter);
		}
		return parameters;
	}

	private Object convert(final Class<?> type, final String parameterValue) {
		return ConvertUtils.convert(parameterValue, type);
	}

	private List<Object> resolveParameters(ActionInfo actionInfo, Map<String, Object> parameters) {
		final Method method = actionInfo.getMethod();
		final Class<?>[] paramTypes = method.getParameterTypes();
		final Annotation[][] paramAnnot = method.getParameterAnnotations();
		Validate.isTrue(paramTypes.length == paramAnnot.length, "The number of parameters do not match the number of annotations");

		List<Object> resolvedParameters = new ArrayList<Object>();
		int length = paramTypes.length;
		for (int i = 0; i < length; ++i) {
			final Class<?> type = paramTypes[i];

			final String parameterName = getParameterName(paramAnnot[i]);
			final Object parameterValue = parameters.get(parameterName);

			resolvedParameters.add(ConvertUtils.convert(parameterValue, type));
		}
		return resolvedParameters;
	}

	private String getParameterName(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			final Class<?> annotationClass = annotation.getClass();
			if (ActionParam.class.isAssignableFrom(annotationClass)) {
				return ((ActionParam) annotation).value();
			}
		}

		throw new IllegalStateException("Could not resolve parameter name");
	}
}

