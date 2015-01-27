package com.cduvvuri.hqutam.locator;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cduvvuri.hqutam.utils.JndiUtils;
import com.cduvvuri.hqutam.utils.PropertyUtils;

public class JmsServiceLocator {
	private static final Log LOGGER = LogFactory.getLog(JmsServiceLocator.class
			.getName());
	
	private static Map<String, Destination> destinations = new HashMap<String, Destination>();
	//TODO check socket status using socket sniff/TCPView
	private static Connection connection;

	public static Connection getConnection() {
		if (connection != null) {
			return connection;
		}
		try {
			ConnectionFactory connectionFactory = (ConnectionFactory) JndiUtils.getContext().lookup(PropertyUtils
					.getProps().getProperty(
							"remote.jms.jndi.name.connectionfactory"));
			connection = connectionFactory.createConnection();
			connection.start();
			return connection;
		} catch (NamingException e) {
			LOGGER.error("Failed to create JNDI Context", e);
			throw new RuntimeException(e);
		} catch (Exception e) {
			LOGGER.error("Failed to create JNDI Context", e);
			throw new RuntimeException(e);
		} finally {
			LOGGER.error("Closing initial context");
		}
	}

	public static synchronized Destination getDestination(String name) {
		try {
			if(destinations.containsKey(name)) {
				return destinations.get(name);
			} else {
				destinations.put(name, (Destination) JndiUtils.getContext().lookup(name));
				return destinations.get(name);
			}
		} catch (NamingException e) {
			LOGGER.error("Failed to locate Destination", e);
			throw new RuntimeException(e);
		}
	}

}
