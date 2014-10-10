package com.funnic.mvc.core.impl.converters;

import com.funnic.mvc.core.api.ParameterConverter;

/**
 * @author Per
 */
public class StringConverter implements ParameterConverter {
	@Override
	public boolean accept(final Class<?> parameterType) {
		return String.class.isAssignableFrom(parameterType);
	}

	@Override
	public Object convert(final String value) {
		return value;
	}
}
