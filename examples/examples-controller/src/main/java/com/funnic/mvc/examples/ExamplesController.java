package com.funnic.mvc.examples;

import com.funnic.mvc.core.api.AbstractController;
import com.funnic.mvc.core.api.Controller;
import com.funnic.mvc.core.api.actions.ActionResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Per
 */
public class ExamplesController extends AbstractController {

	private List<Controller> controllers = new ArrayList<>();

	public ActionResult index() {
		Map<String, Object> models = new HashMap<>();
		models.put("controllers", controllers);
		return View("/views/index.ftl", models);
	}

	public void addController(Controller controller) {
		controllers.add(controller);
	}

	public void removeController(Controller controller) {
		controllers.remove(controller);
	}
}
