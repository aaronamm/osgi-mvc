package com.funnic.mvc.demo.controllers;

import com.funnic.mvc.core.api.AbstractController;
import com.funnic.mvc.core.api.ActionResult;
import com.funnic.mvc.core.api.RequestType;
import com.funnic.mvc.core.api.annotations.Path;
import com.funnic.mvc.core.api.annotations.RequestMethod;
import com.funnic.mvc.core.api.annotations.RequestParam;
import com.funnic.mvc.demo.models.HomeModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Per
 */
@Path("/home")
public class HomeController extends AbstractController {

	@RequestMethod
	public ActionResult index() {
		Map<String, Object> models = new HashMap<String, Object>();
		models.put("model", new HomeModel("John Doe"));
		return new ActionResult("/views/index.ftl", models);
	}

	@RequestMethod
	public ActionResult global() {
		Map<String, Object> models = new HashMap<String, Object>();
		models.put("model", new HomeModel("John Doe"));
		return new ActionResult("bundle://:/views/global.ftl", models);
	}

	@RequestMethod(types = RequestType.POST)
	public ActionResult login(@RequestParam("username") final String username,
							  @RequestParam("password") final String password) {
		boolean loginOk = "admin".equals(username) && "pass".equals(password);
		Map<String, Object> models = new HashMap<String, Object>();
		models.put("loginStatus", loginOk);
		return new ActionResult("/views/login.ftl", models);
	}
}
