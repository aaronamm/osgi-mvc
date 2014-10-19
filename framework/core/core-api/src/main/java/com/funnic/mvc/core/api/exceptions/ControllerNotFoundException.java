package com.funnic.mvc.core.api.exceptions;

/**
 * Exception thrown if a controller is not found
 *
 * @author Per
 */
public class ControllerNotFoundException extends RuntimeException {
	public ControllerNotFoundException(String message) {
		super(message);
	}
}
