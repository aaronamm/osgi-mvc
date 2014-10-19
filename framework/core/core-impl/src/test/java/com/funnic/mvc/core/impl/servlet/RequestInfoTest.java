package com.funnic.mvc.core.impl.servlet;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Per
 */
public class RequestInfoTest {

	@Test
	public void verifyDefaultRequestInfoOnEmptyPath() {
		final String input = "";

		final RequestInfo unitToTest = new RequestInfo(input);

		assertEquals("home", unitToTest.getControllerName());
		assertEquals("index", unitToTest.getActionName());
	}

	@Test
	public void verifyDefaultMethodPathOnEmptyMethodPath() {
		final String input = "/controllerPath";

		final RequestInfo unitToTest = new RequestInfo(input);

		assertEquals("controllerPath", unitToTest.getControllerName());
		assertEquals("index", unitToTest.getActionName());
	}

	@Test
	public void verifyControllerAndMethodPathOnRequestPath() {
		final String input = "/controllerPath/methodPath";

		final RequestInfo unitToTest = new RequestInfo(input);

		assertEquals("controllerPath", unitToTest.getControllerName());
		assertEquals("methodPath", unitToTest.getActionName());
	}

	@Test
	public void verifyParametersOnRequestPath() {
		final String input = "/controllerPath/methodPath/p1/p2";

		final RequestInfo unitToTest = new RequestInfo(input);

		assertEquals("controllerPath", unitToTest.getControllerName());
		assertEquals("methodPath", unitToTest.getActionName());
	}
}
