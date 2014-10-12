package com.funnic.mvc.core.spi;

import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.Version;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Per
 */
public class VersionRange {

	private static final String PATTERN = "([\\[\\(]?)([\\d\\.a-zA-Z\\-]*)(,?)([\\d\\.a-zA-Z\\-]*)([\\]\\)]?)";

	// Range that fits all versions
	public static final VersionRange ALL = new VersionRange(VersionRangeType.EQUALS, Version.emptyVersion,
			VersionRangeType.EQUALS, new Version(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE));

	private final VersionRangeType leftType;
	private final Version leftVersion;
	private final VersionRangeType rightType;
	private final Version rightVersion;

	public VersionRange(VersionRangeType leftType, Version leftVersion, VersionRangeType rightType, Version rightVersion) {
		this.leftType = leftType;
		this.leftVersion = leftVersion;
		this.rightType = rightType;
		this.rightVersion = rightVersion;
	}

	public VersionRangeType getLeftType() {
		return leftType;
	}

	public Version getLeftVersion() {
		return leftVersion;
	}

	public VersionRangeType getRightType() {
		return rightType;
	}

	public Version getRightVersion() {
		return rightVersion;
	}

	public static VersionRange parseRange(String s) {
		s = s.replaceAll("\\s", "");
		final Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			String leftRangeTypeStr = matcher.group(1);
			String leftVersionStr = matcher.group(2);
			Version leftVersion = null;
			if (StringUtils.isNotBlank(leftVersionStr))
				leftVersion = Version.parseVersion(leftVersionStr);

			VersionRangeType leftRangeType = VersionRangeType.EQUALS;
			if (StringUtils.equals(leftRangeTypeStr, "("))
				leftRangeType = VersionRangeType.NOT_EQUALS;

			String split = matcher.group(3);

			String rightVersionStr = matcher.group(4);
			String rightRangeTypeStr = matcher.group(5);
			Version rightVersion = null;
			if (StringUtils.isNotBlank(rightVersionStr))
				rightVersion = Version.parseVersion(rightVersionStr);

			VersionRangeType rightRangeType = VersionRangeType.EQUALS;
			if (StringUtils.equals(rightRangeTypeStr, ")"))
				rightRangeType = VersionRangeType.NOT_EQUALS;

			return new VersionRange(leftRangeType, leftVersion, rightRangeType, rightVersion);
		}

		return null;
	}

	public boolean withinRange(final Version version) {
		if (leftVersion.compareTo(version) > 0)
			return false;
		if (leftVersion.compareTo(version) == 0 && leftType != VersionRangeType.EQUALS)
			return false;

		if (rightVersion.compareTo(version) < 0)
			return false;
		if (rightVersion.compareTo(version) == 0 && rightType != VersionRangeType.EQUALS)
			return false;

		return true;
	}
}
