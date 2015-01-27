package com.cduvvuri.hqutam.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JndiUtils {
	private static Log LOGGER = LogFactory.getLog(JndiUtils.class);
	private static Context ctx;

	public static Context getContext() {
		try {
			if(ctx != null) {
				return ctx;
			}
			ctx = new InitialContext(PropertyUtils.getProps());
			LOGGER.info("JNDIContext initialized : " + ctx);
			return ctx;
		} catch (NamingException e) {
			LOGGER.error("Failed to create JNDI Context", e);
			throw new RuntimeException(e);
		} catch (Exception e) {
			LOGGER.error("Failed to create JNDI Context", e);
			throw new RuntimeException(e);
		}
	}

	public static void closeContext() {
		try {
			if (ctx != null) {
				ctx.close();
			}
		} catch (NamingException e) {
			LOGGER.error("Failed to close JNDI Context", e);
		}
	}
}
