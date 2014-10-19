package com.funnic.mvc.examples.example2;

import com.funnic.mvc.core.api.AbstractController;
import com.funnic.mvc.core.api.RequestType;
import com.funnic.mvc.core.api.actions.ActionResult;
import com.funnic.mvc.core.api.annotations.Action;
import com.funnic.mvc.core.api.annotations.ActionParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Per
 */
public class Example2Controller extends AbstractController {

	public ActionResult index() {
		Map<String, Object> models = new HashMap<>();
		final boolean loggedIn = isLoggedIn();
		models.put("loggedIn", loggedIn);
		if (loggedIn) {
			models.put("user", new UserModel("John Doe"));
		}
		return View("/views/index.ftl", models);
	}

	@Action(types = RequestType.POST)
	public ActionResult login(@ActionParam("username") final String username, @ActionParam("password") final String password) {
		return RedirectToAction("index");
	}

	public ActionResult logout() {
		return RedirectToAction("index");
	}

	public boolean isLoggedIn() {
		return false;
	}
}
