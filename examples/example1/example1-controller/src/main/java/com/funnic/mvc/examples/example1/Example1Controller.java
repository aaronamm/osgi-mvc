package com.funnic.mvc.examples.example1;

import com.funnic.mvc.core.api.AbstractController;
import com.funnic.mvc.core.api.actions.ActionResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller class which displays a "Hello World" message
 *
 * @author Per
 */
public class Example1Controller extends AbstractController {

	public ActionResult index() {
		Map<String, Object> models = new HashMap<>();
		models.put("greeting", "Hello World!!!");
		return View("/views/index.ftl", models);
	}

}
