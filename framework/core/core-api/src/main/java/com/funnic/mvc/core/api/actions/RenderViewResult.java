package com.funnic.mvc.core.api.actions;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Per
 */
public class RenderViewResult extends ActionResult {
	private final String view;
	private Map<String, Object> models;

	public RenderViewResult(final String view) {
		this.view = view;
	}

	public RenderViewResult(final String view, final Map<String, Object> models) {
		this.view = view;
		this.models = models;
	}

	public String getView() {
		return view;
	}

	public Map<String, Object> getModels() {
		if (models == null)
			models = new HashMap<String, Object>();
		return models;
	}
}
