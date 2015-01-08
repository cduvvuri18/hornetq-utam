package com.cduvvuri.hqutam.utils;

public class StringUtils {
	public static String getJndiName(String jndiName) {
		if (jndiName.contains("exported")) {
			return jndiName.substring(jndiName.lastIndexOf("exported")
					+ "exported".length() + 1, jndiName.length());
		} else
			return jndiName;
	}
}
