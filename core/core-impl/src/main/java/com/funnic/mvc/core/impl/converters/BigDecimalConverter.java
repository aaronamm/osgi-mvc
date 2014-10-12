package com.funnic.mvc.core.impl.converters;

import com.funnic.mvc.core.api.ParameterConverter;

import java.math.BigDecimal;

/**
 * @author Per
 */
public class BigDecimalConverter implements ParameterConverter {
	@Override
	public boolean accept(final Class<?> parameterType) {
		return BigDecimal.class.isAssignableFrom(parameterType);
	}

	@Override
	public Object convert(final String value) {
		return new BigDecimal(value);
	}
}
