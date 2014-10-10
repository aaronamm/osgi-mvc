package com.funnic.mvc.core.impl.exceptions;

/**
 * @author Per
 */
public class ControllerMethodNotFound extends RuntimeException {
	public ControllerMethodNotFound(final String message) {
		super(message);
	}
}
