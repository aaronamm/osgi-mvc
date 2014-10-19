package com.funnic.mvc.examples.example5;

import com.funnic.mvc.core.api.AbstractController;
import com.funnic.mvc.core.api.actions.ActionResult;

/**
 * @author Per
 */
public class Example5Controller extends AbstractController {

	/**
	 * Action method called home. This method is defined as the defaultAction in the blueprint file
	 *
	 * @return
	 */
	public ActionResult home() {
		return View("/views/home.ftl");
	}
}
