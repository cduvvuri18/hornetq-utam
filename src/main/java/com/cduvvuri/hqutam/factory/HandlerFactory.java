package com.cduvvuri.hqutam.factory;

import com.cduvvuri.hqutam.handler.jmx.impl.JmxJmsHandlerImpl;
import com.cduvvuri.hqutam.handler.send.SendHandler;
import com.cduvvuri.hqutam.handler.send.impl.JmsSendHandlerImpl;
import com.cduvvuri.hqutam.jmx.JmxJmsHandler;
import com.cduvvuri.hqutam.type.HornetQType;

public class HandlerFactory {
	private static SendHandler jmsSendHandler = new JmsSendHandlerImpl();
	private static JmxJmsHandler jmxJmsHandler = new JmxJmsHandlerImpl();

	public static SendHandler getSendHandler(HornetQType hornetQType) {
		switch(hornetQType) {
			case JMS:
				return jmsSendHandler;
			case CORE:
				return null;
		}
		return null;
	}
	
	public static JmxJmsHandler getJmxJmsHandler() {
		return jmxJmsHandler;
	}
}
