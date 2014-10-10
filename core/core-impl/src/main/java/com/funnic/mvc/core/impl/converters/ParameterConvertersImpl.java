package com.funnic.mvc.core.impl.converters;

import com.funnic.mvc.core.api.ParameterConverter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Per
 */
public class ParameterConvertersImpl implements ParameterConverters {

	private List<ParameterConverter> converters = new ArrayList<ParameterConverter>();

	public ParameterConvertersImpl(final List<ParameterConverter> converters) {
		this.converters.addAll(converters);
	}

	@Override
	public Object[] convert(String[] parameters, Class<?>[] parameterTypes) {
		if (parameters.length == 0)
			return new Object[0];

		final Object[] result = new Object[parameters.length];
		for (int i = 0; i < parameters.length; ++i) {
			final Class<?> type = parameterTypes[i];
			if (boolean.class.isAssignableFrom(type)) {
				result[i] = Boolean.parseBoolean(parameters[i]);
				continue;
			} else if (char.class.isAssignableFrom(type)) {
				result[i] = parameters[i].charAt(0);
				continue;
			} else if (short.class.isAssignableFrom(type)) {
				result[i] = Short.parseShort(parameters[i]);
				continue;
			} else if (int.class.isAssignableFrom(type)) {
				result[i] = Integer.parseInt(parameters[i]);
				continue;
			} else if (long.class.isAssignableFrom(type)) {
				result[i] = Long.parseLong(parameters[i]);
				continue;
			} else if (float.class.isAssignableFrom(type)) {
				result[i] = Float.parseFloat(parameters[i]);
				continue;
			} else if (double.class.isAssignableFrom(type)) {
				result[i] = Double.parseDouble(parameters[i]);
				continue;
			}

			final ParameterConverter converter = CollectionUtils.find(converters, new GetParameterConverter(type));
			if (converter == null)
				result[i] = converter.convert(parameters[i]);
			else
				result[i] = null;
		}

		return new Object[0];
	}

	public void addConverter(final ParameterConverter converter) {
		converters.add(converter);
	}

	public void removeConverter(final ParameterConverter converter) {
		converters.remove(converter);
	}

}
