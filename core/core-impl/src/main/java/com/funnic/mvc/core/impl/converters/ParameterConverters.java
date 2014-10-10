package com.funnic.mvc.core.impl.converters;

/**
 * @author Per
 */
public interface ParameterConverters {
	/**
	 * Convert the supplied parameters to the supplied parameter types and return the result
	 *
	 * @param parameters
	 * @param parameterTypes
	 * @return
	 */
	Object[] convert(String[] parameters, Class<?>[] parameterTypes);
}
