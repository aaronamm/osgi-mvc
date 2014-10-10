package com.funnic.mvc.core.impl.exceptions;

/**
 * @author Per
 */
public class CouldNotInvokeMethodException extends RuntimeException {
	public CouldNotInvokeMethodException(final String message, final Exception e) {
		super(message, e);
	}
}
