package com.funnic.mvc.core.api.exceptions;

/**
 * @author Per
 */
public class ControllerNotFoundException extends Exception {
	public ControllerNotFoundException() {
	}

	public ControllerNotFoundException(String message) {
		super(message);
	}

	public ControllerNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ControllerNotFoundException(Throwable cause) {
		super(cause);
	}

	public ControllerNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
