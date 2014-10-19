package com.funnic.mvc.core.impl.view.predicate;

import org.apache.commons.collections4.Predicate;

import java.net.URL;

/**
 * @author Per
 */
public class FindViewByPathPredicate implements Predicate<URL> {
	private final String findPath;

	public FindViewByPathPredicate(final String findPath) {
		this.findPath = findPath;
	}

	@Override
	public boolean evaluate(URL object) {
		final String urlPath = object.getPath();
		return urlPath.equals(findPath);
	}
}
