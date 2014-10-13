package com.funnic.mvc.core.api.exceptions;

/**
 * @author Per
 */
public class ActionNotFoundException extends RuntimeException {
	public ActionNotFoundException(String message) {
		super(message);
	}
}
