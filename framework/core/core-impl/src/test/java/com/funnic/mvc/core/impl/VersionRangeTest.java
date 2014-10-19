package com.funnic.mvc.core.impl;

import org.junit.Test;
import org.osgi.framework.Version;

import static org.junit.Assert.*;

/**
 * @author Per
 */
public class VersionRangeTest {

	@Test
	public void verifySimpleVersion() {
		String input = "1.0.0";
		VersionRange range = VersionRange.parseRange(input);
		assertEquals("1.0.0", range.getLeftVersion().toString());
	}

	@Test
	public void verifyVersionWithQualifier() {
		String input = "1.0.0.SNAPSHOT";
		VersionRange range = VersionRange.parseRange(input);
		assertEquals("1.0.0.SNAPSHOT", range.getLeftVersion().toString());
	}

	@Test
	public void verifySimpleVersionRange() {
		String input = "[1.0.0,2)";
		VersionRange range = VersionRange.parseRange(input);

		assertEquals("1.0.0", range.getLeftVersion().toString());
		assertEquals(VersionRangeType.EQUALS, range.getLeftType());
		assertEquals("2.0.0", range.getRightVersion().toString());
		assertEquals(VersionRangeType.NOT_EQUALS, range.getRightType());
		assertTrue(range.withinRange(Version.parseVersion("1.0.0")));
		assertTrue(range.withinRange(Version.parseVersion("1.1.0")));
		assertTrue(range.withinRange(Version.parseVersion("1.95.0")));
		assertFalse(range.withinRange(Version.parseVersion("0.9")));
		assertFalse(range.withinRange(Version.parseVersion("2.0.0")));
		assertFalse(range.withinRange(Version.parseVersion("2.0.1")));
	}

	@Test
	public void verifyMoreComplexVersionRange() {
		String input = "[1.0.0,2.1.0]";
		VersionRange range = VersionRange.parseRange(input);

		assertEquals("1.0.0", range.getLeftVersion().toString());
		assertEquals(VersionRangeType.EQUALS, range.getLeftType());
		assertEquals("2.1.0", range.getRightVersion().toString());
		assertEquals(VersionRangeType.EQUALS, range.getRightType());
		assertTrue(range.withinRange(Version.parseVersion("1.0.0")));
		assertTrue(range.withinRange(Version.parseVersion("1.1.0")));
		assertTrue(range.withinRange(Version.parseVersion("1.95.0")));
		assertFalse(range.withinRange(Version.parseVersion("0.9")));
		assertTrue(range.withinRange(Version.parseVersion("2.0.0")));
		assertTrue(range.withinRange(Version.parseVersion("2.0.1")));
		assertTrue(range.withinRange(Version.parseVersion("2.1.0")));
		assertFalse(range.withinRange(Version.parseVersion("2.1.1")));
	}
}
