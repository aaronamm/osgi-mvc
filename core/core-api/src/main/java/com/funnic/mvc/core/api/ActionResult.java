package com.funnic.mvc.core.api;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Per
 */
public class ActionResult {
	private final String view;
	private Map<String, Object> models;

	public ActionResult(String view) {
		this.view = view;
	}

	public ActionResult(String view, Map<String, Object> models) {
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
