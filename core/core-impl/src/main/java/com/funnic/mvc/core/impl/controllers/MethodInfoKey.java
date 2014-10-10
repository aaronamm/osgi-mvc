package com.funnic.mvc.core.impl.controllers;

import com.funnic.mvc.core.api.RequestType;
import org.apache.commons.lang3.Validate;

/**
 * @author Per
 */
public class MethodInfoKey {
	private final String path;
	private final RequestType requestType;

	public MethodInfoKey(String path, RequestType requestType) {
		Validate.notEmpty(path, "Parameter 'path' cannot be null or empty");
		Validate.notNull(requestType, "Parameter 'requestType' cannot be null");
		this.path = path;
		this.requestType = requestType;

	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof MethodInfoKey) {
			MethodInfoKey rhs = (MethodInfoKey) obj;
			return rhs.path.equals(path) && rhs.requestType.equals(requestType);
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		int result = path.hashCode();
		result = 31 * result + requestType.hashCode();
		return result;
	}
}
