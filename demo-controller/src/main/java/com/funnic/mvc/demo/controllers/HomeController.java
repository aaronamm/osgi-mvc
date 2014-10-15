package com.funnic.mvc.demo.controllers;

import com.funnic.mvc.core.api.AbstractController;
import com.funnic.mvc.core.api.RequestType;
import com.funnic.mvc.core.api.actions.ActionResult;
import com.funnic.mvc.core.api.annotations.Action;
import com.funnic.mvc.core.api.annotations.RequestParam;
import com.funnic.mvc.demo.models.HomeModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Per
 */
public class HomeController extends AbstractController {

	@Action
	public ActionResult index() {
		Map<String, Object> models = new HashMap<String, Object>();
		models.put("model", new HomeModel("John Doe"));
		return View("/views/index.ftl", models);
	}

	@Action
	public ActionResult index2(@RequestParam("param1") int param1,
							   @RequestParam("param2") String param2) {
		Map<String, Object> models = new HashMap<String, Object>();
		models.put("param1", param1);
		models.put("param2", param2);
		return View("/views/index2.ftl", models);
	}

	@Action
	public ActionResult index3(@RequestParam("model") HomeModel model) {
		Map<String, Object> models = new HashMap<String, Object>();
		models.put("model", model);
		return View("/views/index3.ftl", models);
	}

	@Action
	public ActionResult global() {
		Map<String, Object> models = new HashMap<String, Object>();
		models.put("model", new HomeModel("John Doe"));
		return View("bundle://:/views/global.ftl", models);
	}

	@Action(types = RequestType.POST)
	public ActionResult login(@RequestParam("username") final String username,
							  @RequestParam("password") final String password) {
		boolean loginOk = "admin".equals(username) && "pass".equals(password);
		Map<String, Object> models = new HashMap<String, Object>();
		models.put("loginStatus", loginOk);
		return View("/views/login.ftl", models);
	}

	@Action
	public ActionResult logout() {
		return ForwardToAction("index");
	}
}
