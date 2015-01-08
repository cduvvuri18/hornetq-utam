package com.cduvvuri.hqutam.locator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.cduvvuri.hqutam.handler.jmx.impl.JmxJmsHandlerImpl;

public class TestJmxJmsMbeanLocator {
	private static final Log LOGGER = LogFactory.getLog(JmxJmsHandlerImpl.class
			.getName());
	
	
	@Test
	public void testDestinationDtls() {
		JmxJmsMbeanLocator locator = new JmxJmsMbeanLocator();
		
		String[] topicNames = locator.getTopicNames();
		
		for (int i = 0; i < topicNames.length; i++) {
			LOGGER.info("Topic name :: "+ topicNames[i]);
		}
		
		LOGGER.info(locator.getTopicSubscriptions("testTopic"));
		LOGGER.info(locator.getTopicMessages("testTopic", null));
	}
}
