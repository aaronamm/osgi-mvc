package com.funnic.mvc.core.impl.controllers;

import com.funnic.mvc.core.impl.predicates.ControllerFromServiceReferencePredicate;
import org.apache.commons.collections4.CollectionUtils;
import org.osgi.framework.ServiceReference;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Per
 */
public class ControllerRepositoryImpl implements ControllerRepository {

	private final Map<String, ControllerInfo> controllers = new HashMap<String, ControllerInfo>();
	private final ControllerInfoFactory controllerInfoFactory;

	public ControllerRepositoryImpl(final ControllerInfoFactory controllerInfoFactory) {
		this.controllerInfoFactory = controllerInfoFactory;
	}

	@Override
	public ControllerInfo getController(final String path) {
		ControllerInfo info = controllers.get(path);
		if (info == null)
			return null;

		return info;
	}

	public void addController(final ServiceReference controllerReference) {
		final ControllerInfo controllerInfo = controllerInfoFactory.create(controllerReference);
		final String rootPath = controllerInfo.getRootPath();
		controllers.put(rootPath, controllerInfo);
	}

	public void removeController(final ServiceReference controllerReference) {
		final Collection<ControllerInfo> result = CollectionUtils.predicatedCollection(controllers.values(), new ControllerFromServiceReferencePredicate(controllerReference));
		for (final ControllerInfo info : result) {
			controllers.remove(info.getRootPath());
			info.destroy();
		}
	}
}
