package com.funnic.mvc.core.api.actions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Per
 */
public class ForwardToActionResult extends ActionResult {
	private final String controller;
	private final String action;
	private Map<String, Object> parameters;

	public ForwardToActionResult(final String action) {
		this.controller = null;
		this.action = action;
	}

	public ForwardToActionResult(final String action, final Map<String, Object> parameters) {
		this.controller = null;
		this.action = action;
		this.parameters = parameters;
	}

	public ForwardToActionResult(final String controller, final String action) {
		this.controller = controller;
		this.action = action;
	}

	public ForwardToActionResult(final String controller, final String action, final Map<String, Object> parameters) {
		this.controller = controller;
		this.action = action;
		this.parameters = parameters;
	}

	public String getController() {
		return controller;
	}

	public String getAction() {
		return action;
	}

	public Map<String, Object> getParameters() {
		if(parameters == null)
			parameters = new HashMap<String, Object>();
		return parameters;
	}
}
