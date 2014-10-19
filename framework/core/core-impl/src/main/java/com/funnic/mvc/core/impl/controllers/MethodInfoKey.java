package com.funnic.mvc.core.impl.controllers;

import com.funnic.mvc.core.api.RequestType;
import org.apache.commons.lang3.Validate;

/**
 * @author Per
 */
public class MethodInfoKey {
	private final String name;
	private final RequestType requestType;

	public MethodInfoKey(final String name, final RequestType requestType) {
		Validate.notEmpty(name, "Parameter 'name' cannot be null or empty");
		Validate.notNull(requestType, "Parameter 'requestType' cannot be null");
		this.name = name.toLowerCase();
		this.requestType = requestType;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof MethodInfoKey) {
			MethodInfoKey rhs = (MethodInfoKey) obj;
			return rhs.name.equals(name) && rhs.requestType.equals(requestType);
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + requestType.hashCode();
		return result;
	}

	public String getName() {
		return this.name;
	}
}
