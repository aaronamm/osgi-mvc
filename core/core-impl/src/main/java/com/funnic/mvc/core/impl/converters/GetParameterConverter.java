package com.funnic.mvc.core.impl.converters;

import com.funnic.mvc.core.api.ParameterConverter;
import org.apache.commons.collections4.Predicate;

/**
 * @author Per
 */
public class GetParameterConverter implements Predicate<ParameterConverter> {
	private final Class<?> searchForType;

	public GetParameterConverter(final Class<?> searchForType) {
		this.searchForType = searchForType;
	}

	@Override
	public boolean evaluate(ParameterConverter object) {
		return object.accept(searchForType);
	}
}
