package com.funnic.mvc.examples.example4;

import com.funnic.mvc.core.api.AbstractController;
import com.funnic.mvc.core.api.actions.ActionResult;

/**
 * @author Per
 */
public class Example4Controller extends AbstractController {

	public ActionResult index() {
		return View("/views/index.ftl");
	}

	public ActionResult displayPartialView() {
		return View("/views/partialView.ftl");
	}
}
