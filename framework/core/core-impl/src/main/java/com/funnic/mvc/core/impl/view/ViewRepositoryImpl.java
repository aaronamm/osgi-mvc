package com.funnic.mvc.core.impl.view;

import com.funnic.mvc.core.impl.view.predicate.FindViewByPathPredicate;
import org.apache.commons.collections4.CollectionUtils;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Per
 */
public class ViewRepositoryImpl implements ViewRepository {
	private static final Logger log = LoggerFactory.getLogger(ViewRepositoryImpl.class);

	private final Bundle bundle;
	private final ServiceReference serviceReference;
	private final List<URL> views = new ArrayList<>();

	public ViewRepositoryImpl(final Bundle bundle, ServiceReference serviceReference, final List<URL> views) {
		this.bundle = bundle;
		this.serviceReference = serviceReference;
		this.views.addAll(views);
	}

	@Override
	public URL getView(final String path) {
		return CollectionUtils.find(views, new FindViewByPathPredicate(path));
	}

	@Override
	public Bundle getBundle() {
		return bundle;
	}

	@Override
	public ServiceReference getServiceReference() {
		return serviceReference;
	}
}
