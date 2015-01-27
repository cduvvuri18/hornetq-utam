package com.cduvvuri.hqutam.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cduvvuri.hqutam.locator.JmsServiceLocator;

public class PropertyUtils {
	private static final Log LOGGER = LogFactory.getLog(JmsServiceLocator.class
			.getName());
	private static Properties prop;

	public static Properties getProps() {
		try {
			if(prop != null) {
				return prop;
			}
			prop = new Properties();
			prop.load(new FileInputStream("utam.properties"));
			LOGGER.info("Properties have been loaded successfully : " + prop);
			return prop;
		} catch (IOException e) {
			LOGGER.error("Failed to load utam.properties", e);
			throw new RuntimeException(e);

		} catch (Exception e) {
			LOGGER.error("Failed to load utam.properties", e);
			throw new RuntimeException(e);
		}
	}
}
