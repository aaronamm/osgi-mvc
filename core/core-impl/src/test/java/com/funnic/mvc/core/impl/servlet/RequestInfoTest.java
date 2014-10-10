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

		assertEquals("/home", unitToTest.getControllerPath());
		assertEquals("/index", unitToTest.getMethodPath());
		assertEquals(0, unitToTest.getParameters().length);
	}

	@Test
	public void verifyDefaultMethodPathOnEmptyMethodPath() {
		final String input = "/controllerPath";

		final RequestInfo unitToTest = new RequestInfo(input);

		assertEquals("/controllerPath", unitToTest.getControllerPath());
		assertEquals("/index", unitToTest.getMethodPath());
		assertEquals(0, unitToTest.getParameters().length);
	}

	@Test
	public void verifyControllerAndMethodPathOnRequestPath() {
		final String input = "/controllerPath/methodPath";

		final RequestInfo unitToTest = new RequestInfo(input);

		assertEquals("/controllerPath", unitToTest.getControllerPath());
		assertEquals("/methodPath", unitToTest.getMethodPath());
		assertEquals(0, unitToTest.getParameters().length);
	}

	@Test
	public void verifyParametersOnRequestPath() {
		final String input = "/controllerPath/methodPath/p1/p2";

		final RequestInfo unitToTest = new RequestInfo(input);

		assertEquals("/controllerPath", unitToTest.getControllerPath());
		assertEquals("/methodPath", unitToTest.getMethodPath());
		assertEquals(2, unitToTest.getParameters().length);
		assertEquals("p1", unitToTest.getParameters()[0]);
		assertEquals("p2", unitToTest.getParameters()[1]);
	}
}
