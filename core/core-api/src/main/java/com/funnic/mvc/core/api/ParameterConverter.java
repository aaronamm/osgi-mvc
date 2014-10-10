package com.funnic.mvc.core.api;

/**
 * @author Per
 */
public interface ParameterConverter {

	boolean accept(Class<?> parameterType);

	Object convert(String value);

}
