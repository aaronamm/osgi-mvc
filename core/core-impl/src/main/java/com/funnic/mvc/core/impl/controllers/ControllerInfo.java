package com.funnic.mvc.core.impl.controllers;

import com.funnic.mvc.core.api.ActionInfo;
import com.funnic.mvc.core.api.Controller;
import com.funnic.mvc.core.api.RequestType;
import com.funnic.mvc.core.api.exceptions.ActionNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

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
	private Map<MethodInfoKey, ActionInfo> accessibleMethods = new HashMap<MethodInfoKey, ActionInfo>();

	public ControllerInfo(BundleContext bundleContext, final Controller controller, final ServiceReference serviceReference) {
		this.bundleContext = bundleContext;
		this.bundle = bundleContext.getBundle();
		this.controller = controller;
		this.serviceReference = serviceReference;
		for (final ActionInfo actionInfo : controller.getActions()) {
			RequestType[] types = actionInfo.getRequestMethod().types();
			if (types.length == 0)
				types = new RequestType[]{RequestType.GET, RequestType.POST};

			final String actionName = StringUtils.defaultIfEmpty(actionInfo.getRequestMethod().name(),
					actionInfo.getMethod().getName());
			for (final RequestType type : types) {
				accessibleMethods.put(new MethodInfoKey(actionName, type), actionInfo);
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		return serviceReference.equals(obj);
	}

	public String getRootPath() {
		return controller.getControllerName();
	}

	public void destroy() {
		bundleContext.ungetService(serviceReference);
	}

	public Bundle getBundle() {
		return bundle;
	}

	public ActionInfo getAction(final String action) throws ActionNotFoundException {
		for (Map.Entry<MethodInfoKey, ActionInfo> entry : accessibleMethods.entrySet()) {
			if(entry.getKey().getName().equals(action)) {
				return entry.getValue();
			}
		}

		throw new ActionNotFoundException("Could not find action: " + action);
	}

	public ActionInfo getAction(final String action, final RequestType type) throws ActionNotFoundException {
		ActionInfo actionInfo = accessibleMethods.get(new MethodInfoKey(action, type));
		if(actionInfo == null)
			throw new ActionNotFoundException("Could not find action: " + action);

		return actionInfo;
	}
}
