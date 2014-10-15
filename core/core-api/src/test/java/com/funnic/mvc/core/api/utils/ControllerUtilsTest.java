package com.funnic.mvc.core.api.utils;

import com.funnic.mvc.core.api.AbstractController;
import com.funnic.mvc.core.api.annotations.ComponentName;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Per
 */
public class ControllerUtilsTest {

	public class TestController extends AbstractController {

	}

	@Test
	public void verifyComponentNameBaseOnClass() {
		final String name = ControllerUtils.getControllerName(TestController.class);
		assertEquals("test", name);
	}

	@ComponentName("Hello")
	public class Test2Controller extends AbstractController {

	}

	@Test
	public void verifyComponentNameBasedOnAnnotation() {
		final String name = ControllerUtils.getControllerName(Test2Controller.class);
		assertEquals("hello", name);
	}
}
