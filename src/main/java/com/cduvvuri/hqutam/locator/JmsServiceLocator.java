package com.cduvvuri.hqutam.locator;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cduvvuri.hqutam.utils.PropertyUtils;

public class JmsServiceLocator {
	private static final Log LOGGER = LogFactory
			.getLog(JmsServiceLocator.class.getName());
	private static Context ctx;
	private static ConnectionFactory connectionFactory;
	
	public static ConnectionFactory getConnectionFactory() {
		if(connectionFactory != null) {
			return connectionFactory;
		}
		synchronized(JmsServiceLocator.class) {
			if(connectionFactory == null) {
				try {
					connectionFactory = (ConnectionFactory) getContext().lookup(PropertyUtils.getProps()
							.getProperty("remote.jms.jndi.name.connectionfactory"));
					return connectionFactory;
				}  catch (NamingException e) {
					LOGGER.error("Failed to create JNDI Context", e);
					throw new RuntimeException(e);
				} catch (Exception e) {
					LOGGER.error("Failed to create JNDI Context", e);
					throw new RuntimeException(e);
				}
			} else {
				return connectionFactory;
			}
		}
	}

	public static Destination getDestination(String name) {
		try {
			return (Destination) ctx.lookup(name);
		} catch(NamingException e) {
			LOGGER.error("Failed to locate Destination", e);
			throw new RuntimeException(e);
		}
	}

	private static final synchronized Context getContext() {
		if(ctx != null) {
			return ctx;
		}
		try {
			ctx = new InitialContext(PropertyUtils.getProps());
			LOGGER.info("JNDIContext initialized : "+ctx);
			connectionFactory = (ConnectionFactory) ctx.lookup(PropertyUtils.getProps()
					.getProperty("remote.jms.jndi.name.connectionfactory"));
			return ctx;
		}  catch (NamingException e) {
			LOGGER.error("Failed to create JNDI Context", e);
			throw new RuntimeException(e);
		} catch (Exception e) {
			LOGGER.error("Failed to create JNDI Context", e);
			throw new RuntimeException(e);
		}
	}
}
