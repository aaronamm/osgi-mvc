package com.funnic.mvc.core.api.actions;

/**
 * @author Per
 */
public class ForwardToActionResult extends ActionResult {
	private final String controller;
	private final String action;

	public ForwardToActionResult(final String action) {
		this.controller = null;
		this.action = action;
	}

	public ForwardToActionResult(final String controller, final String action) {
		this.controller = controller;
		this.action = action;
	}

	public String getController() {
		return controller;
	}

	public String getAction() {
		return action;
	}
}
