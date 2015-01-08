package com.cduvvuri.hqutam.locator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import com.cduvvuri.hqutam.locator.JmsServiceLocator;

public class TestServiceLocator {
	private static final Log LOGGER = LogFactory
			.getLog(TestServiceLocator.class.getName());

	@Test
	public void testInitiailize() {
		LOGGER.info("connection factory :: "+JmsServiceLocator.getConnectionFactory());
		Assert.assertNotNull(JmsServiceLocator.getConnectionFactory());
	}
}
