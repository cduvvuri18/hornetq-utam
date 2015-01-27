package com.cduvvuri.hqutam.handler.send;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.Test;

import com.cduvvuri.hqutam.handler.send.impl.JmsSendHandlerImpl;
import com.cduvvuri.hqutam.locator.JmsServiceLocator;
import com.cduvvuri.hqutam.utils.JndiUtils;
import com.cduvvuri.hqutam.vo.PublishForm;

/*
 chaitanya
 */
public class TestJmsSendHandler {
	private static Log LOGGER = LogFactory.getLog(TestJmsSendHandler.class.getName());
	
	@AfterClass
	public static void after() {
		try {
			JndiUtils.closeContext();
		}catch(Exception e) {
			LOGGER.error(e);
		}
		try {
			JmsServiceLocator.getConnection().close();
		}catch(Exception e) {
			LOGGER.error(e);
		}
	}
	
	@Test
	//@Ignore
	public void testTextMessageByText() throws Exception {
		JmsSendHandlerImpl handler = new JmsSendHandlerImpl();

		PublishForm form = new PublishForm();
		form.setMessageInputType("TEXT");
		form.setMessageType("TextMessage");
		form.setDestinationJndiName("java:jboss/exported/queue/QueueA");
		form.setMessageByTextarea("Hi this is Chaitanya");
		form.setMessagePropertiesInBytes(readFile("message_properties.json"));
		handler.send(form);
	}

	@Test
	//@Ignore
	public void testTextMessageByFile() {
		JmsSendHandlerImpl handler = new JmsSendHandlerImpl();

		PublishForm form = new PublishForm();
		form.setMessageInputType("FILE");
		form.setMessageType("TextMessage");
		form.setDestinationJndiName("java:jboss/exported/queue/QueueA");
		form.setMessageInBytes("Hi this is Chaitanya".getBytes());

		handler.send(form);
	}

	@Test
	//@Ignore
	public void testMapMessage() throws Exception {
		JmsSendHandlerImpl handler = new JmsSendHandlerImpl();

		PublishForm form = new PublishForm();
		form.setMessageInputType("FILE");
		form.setMessageType("MapMessage");
		form.setDestinationJndiName("java:jboss/exported/queue/QueueA");
		form.setMessageInBytes(readFile("map_message.json"));

		handler.send(form);
	}

	@Test
	//@Ignore
	public void testStreamMessage() throws Exception {
		JmsSendHandlerImpl handler = new JmsSendHandlerImpl();

		PublishForm form = new PublishForm();
		form.setMessageInputType("FILE");
		form.setMessageType("StreamMessage");
		form.setDestinationJndiName("java:jboss/exported/queue/QueueA");
		form.setMessageInBytes(readFile("stream_message.json"));

		handler.send(form);
	}

	@Test
	//@Ignore
	public void testBytesMessage() throws Exception {
		JmsSendHandlerImpl handler = new JmsSendHandlerImpl();

		PublishForm form = new PublishForm();
		form.setMessageInputType("FILE");
		form.setMessageType("BytesMessage");
		form.setDestinationJndiName("java:jboss/exported/queue/QueueA");
		form.setMessageInBytes(readFile("bytes_message.json"));

		handler.send(form);
	}

	@Test
	//@Ignore
	public void testObjectMessage() throws Exception {
		JmsSendHandlerImpl handler = new JmsSendHandlerImpl();

		PublishForm form = new PublishForm();
		form.setMessageInputType("FILE");
		form.setMessageType("ObjectMessage");
		form.setDestinationJndiName("java:jboss/exported/queue/QueueA");
		form.setFullyQualClassName("com.cduvvuri.hqutam.vo.SampleDto");
		form.setMessageInBytes(readFile("object_message.json"));
		handler.send(form);
	}

	public byte[] readFile(String fileName) throws Exception {
		return Files.readAllBytes(Paths.get(TestJmsSendHandler.class
				.getClassLoader().getResource(fileName).toURI()));
	}
}
