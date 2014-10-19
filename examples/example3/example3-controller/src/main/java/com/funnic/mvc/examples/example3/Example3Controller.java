package com.funnic.mvc.examples.example3;

import com.funnic.mvc.core.api.AbstractController;
import com.funnic.mvc.core.api.actions.ActionResult;

/**
 * @author Per
 */
public class Example3Controller extends AbstractController {

	public ActionResult index() {
		return View("/views/index.ftl");
	}

	public ActionResult displayViewFromViewBundle() {
		// Load the view from a separate bundle.
		return View("bundle://com.funnic.mvc.examples.example3.example3-views:[0,1)/views/index.ftl");
	}

	public ActionResult displayViewFromAnyBundle() {
		// Load the view from any separate bundle within version range [0, 1). Select the bundle with the highest version
		return View("bundle://:[0,1)/views/index.ftl");
	}

	public ActionResult displayViewFromAnyBundleAndAnyRange() {
		// Load the view from any bundle with any version. Select the bundle with the highest version
		return View("bundle://:/views/index.ftl");
	}
}
