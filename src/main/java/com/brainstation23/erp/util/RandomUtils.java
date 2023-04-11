package com.brainstation23.erp.util;


import org.apache.commons.lang3.RandomStringUtils;

public class RandomUtils {
	public static String generateAlphaNumeric(final int count) {
		return RandomStringUtils.randomAlphanumeric(count);
	}
}
