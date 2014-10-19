package com.funnic.mvc.core.api.exceptions;

/**
 * Exception thrown if an action is not found
 *
 * @author Per
 */
public class ActionNotFoundException extends RuntimeException {
	public ActionNotFoundException(String message) {
		super(message);
	}
}
