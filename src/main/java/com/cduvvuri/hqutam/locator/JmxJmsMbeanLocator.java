package com.cduvvuri.hqutam.locator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.hornetq.api.core.management.ObjectNameBuilder;
import org.hornetq.api.jms.management.JMSServerControl;

import com.cduvvuri.hqutam.utils.PropertyUtils;

public class JmxJmsMbeanLocator {
	private static final String QUEUE_LIST_MESSAGES_AS_JSON = "listMessagesAsJSON";
	private static final String QUEUE_CONSUMERS_AS_JSON = "listConsumersAsJSON";
	private static final String TOPIC_LIST_MESSAGES_AS_JSON = "listMessagesForSubscriptionAsJSON";
	private static final String TOPIC_LIST_ALL_SUBSCRIPTIONS_AS_JSON = "listAllSubscriptionsAsJSON";

	public static String[] getQueueNames() {
		JMXConnector connector = getJMXConnector();
		try {
			ObjectName on = ObjectNameBuilder.DEFAULT.getJMSServerObjectName();
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
			JMSServerControl serverControl = MBeanServerInvocationHandler
					.newProxyInstance(mbsc, on, JMSServerControl.class, false);
			return serverControl.getQueueNames();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(connector);
		}
	}

	public static String[] getTopicNames() {
		JMXConnector connector = getJMXConnector();
		try {
			ObjectName on = ObjectNameBuilder.DEFAULT.getJMSServerObjectName();
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
			JMSServerControl serverControl = MBeanServerInvocationHandler
					.newProxyInstance(mbsc, on, JMSServerControl.class, false);
			return serverControl.getTopicNames();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(connector);
		}
	}

	public static Map<String, Object> getQueueAttr(String name) {
		Map<String, Object> attr = new HashMap<String, Object>();
		JMXConnector connector = getJMXConnector();
		try {
			ObjectName on = ObjectNameBuilder.DEFAULT
					.getJMSQueueObjectName(name);
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
			MBeanInfo info = mbsc.getMBeanInfo(on);
			for (MBeanAttributeInfo mbean : info.getAttributes()) {
				attr.put(mbean.getName(),
						mbsc.getAttribute(on, mbean.getName()));
			}
			return attr;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(connector);
		}
	}

	public static Map<String, Object> getTpoicAttr(String name) {
		Map<String, Object> attr = new HashMap<String, Object>();
		JMXConnector connector = getJMXConnector();
		try {
			ObjectName on = ObjectNameBuilder.DEFAULT
					.getJMSTopicObjectName(name);
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
			MBeanInfo info = mbsc.getMBeanInfo(on);
			for (MBeanAttributeInfo mbean : info.getAttributes()) {
				attr.put(mbean.getName(),
						mbsc.getAttribute(on, mbean.getName()));
			}
			return attr;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(connector);
		}
	}

	public static String getQueueMessages(String name, String filter) {
		JMXConnector connector = getJMXConnector();
		try {
			ObjectName on = ObjectNameBuilder.DEFAULT
					.getJMSQueueObjectName(name);
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
			return (String) mbsc.invoke(on, QUEUE_LIST_MESSAGES_AS_JSON,
					new Object[] { filter },
					new String[] { String.class.getName() });
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(connector);
		}
	}

	public static String getQueueConsumers(String name) {
		JMXConnector connector = getJMXConnector();
		try {
			ObjectName on = ObjectNameBuilder.DEFAULT
					.getJMSQueueObjectName(name);
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
			return (String) mbsc.invoke(on, QUEUE_CONSUMERS_AS_JSON,
					null,
					null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(connector);
		}
	}

	public static String getTopicMessages(String name, String filter) {
		JMXConnector connector = getJMXConnector();
		try {
			ObjectName on = ObjectNameBuilder.DEFAULT
					.getJMSTopicObjectName(name);
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
/*			HornetQDestination.createQueueNameForDurableSubscription(clientID,
                    subscriptionName)
			http://massapi.com/source/hornetq-2.2.5.Final-src/tests/src/org/hornetq/tests/
			integration/jms/server/management/TopicControlTest.java.html                    
*/			return (String) mbsc.invoke(on, TOPIC_LIST_MESSAGES_AS_JSON,
					new Object[] { name },
					new String[] { String.class.getName() });
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(connector);
		}
	}

	public static String getTopicSubscriptions(String name) {
		JMXConnector connector = getJMXConnector();
		try {
			ObjectName on = ObjectNameBuilder.DEFAULT
					.getJMSTopicObjectName(name);
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
			return (String) mbsc.invoke(on, TOPIC_LIST_ALL_SUBSCRIPTIONS_AS_JSON,
					null,
					null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(connector);
		}
	}

	private static JMXConnector getJMXConnector() {
		JMXConnector connector = null;
		try {
			String JMX_URL = PropertyUtils.getProps().getProperty("jmx.url");
			Hashtable<String, String[]> env = new Hashtable<String, String[]>();

			String[] credentials = new String[] { "admin", "admin@123" };
			env.put(JMXConnector.CREDENTIALS, credentials);
			connector = JMXConnectorFactory.connect(new JMXServiceURL(JMX_URL),
					env);
			return connector;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void closeConnection(JMXConnector jmxConnector) {
		try {
			jmxConnector.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
