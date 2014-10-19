package com.funnic.mvc.core.impl.view;

import com.funnic.mvc.core.api.view.ViewRepositoryFactory;
import org.osgi.framework.Bundle;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Per
 */
public class ViewRepositoryFactoryImpl implements ViewRepositoryFactory {

	@Override
	public List<URL> resolveViews(final Bundle bundle, final String path, final String filePattern) {
		List<URL> views = new ArrayList<>();
		final Enumeration<URL> entries = bundle.findEntries(path, filePattern, true);
		while (entries.hasMoreElements()) {
			final URL entry = entries.nextElement();
			views.add(entry);
		}
		return views;
	}
}
