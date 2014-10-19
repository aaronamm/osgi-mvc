package com.funnic.mvc.core.api.exceptions;

/**
 * @author Per
 */
public class RenderException extends RuntimeException {
	public RenderException(final String message) {
		super(message);
	}

	public RenderException(final String s, final Throwable cause) {
		super(s, cause);
	}
}
