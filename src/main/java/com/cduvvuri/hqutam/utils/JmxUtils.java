package com.cduvvuri.hqutam.utils;

import java.io.IOException;
import java.util.Hashtable;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JmxUtils {
	private static JMXConnector connector; 
	public static JMXConnector getJMXConnector() {
		try {
			if(connector != null) {
				return connector;
			}
			String JMX_URL = PropertyUtils.getProps().getProperty("jmx.url");
			Hashtable<String, String[]> env = new Hashtable<String, String[]>();

			String[] credentials = new String[] {
					PropertyUtils.getProps().getProperty("jmx.username"),
					PropertyUtils.getProps().getProperty("jmx.password") };
			env.put(JMXConnector.CREDENTIALS, credentials);
			connector = JMXConnectorFactory.connect(new JMXServiceURL(JMX_URL),
					env);
			return connector;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void closeConnection(JMXConnector jmxConnector) {
/*		try {
			//jmxConnector.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
*/	}
	
	public static void close() {
		try {
			connector.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
