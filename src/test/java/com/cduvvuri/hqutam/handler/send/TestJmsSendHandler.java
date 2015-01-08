package com.cduvvuri.hqutam.handler.send;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Ignore;
import org.junit.Test;

import com.cduvvuri.hqutam.handler.send.impl.JmsSendHandlerImpl;
import com.cduvvuri.hqutam.vo.PublishForm;

/*
chaitanya
*/
public class TestJmsSendHandler {
	
	@Test
	@Ignore
	public void testTextMessageByText() {
		JmsSendHandlerImpl handler = new JmsSendHandlerImpl();

		PublishForm form = new PublishForm();
		form.setMessageInputType("TEXT");
		form.setMessageType("TextMessage");
		form.setDestinationJndiName("java:jboss/exported/queue/QueueA");
		form.setMessageByTextarea("Hi this is Chaitanya");
		
		handler.send(form);
	}

	@Test
	@Ignore
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
	@Ignore
	public void testMapMessage() throws Exception {
		JmsSendHandlerImpl handler = new JmsSendHandlerImpl();

		PublishForm form = new PublishForm();
		form.setMessageInputType("FILE");
		form.setMessageType("MapMessage");
		form.setDestinationJndiName("java:jboss/exported/queue/QueueA");
		form.setMessageInBytes(readFile("test/map_message.json"));
		
		handler.send(form);
	}
	
	@Test
	@Ignore
	public void testStreamMessage() throws Exception {
		JmsSendHandlerImpl handler = new JmsSendHandlerImpl();

		PublishForm form = new PublishForm();
		form.setMessageInputType("FILE");
		form.setMessageType("StreamMessage");
		form.setDestinationJndiName("java:jboss/exported/queue/QueueA");
		form.setMessageInBytes(readFile("test/stream_message.json"));
		
		handler.send(form);
	}
	
	@Test
	@Ignore
	public void testBytesMessage() throws Exception {
		JmsSendHandlerImpl handler = new JmsSendHandlerImpl();

		PublishForm form = new PublishForm();
		form.setMessageInputType("FILE");
		form.setMessageType("BytesMessage");
		form.setDestinationJndiName("java:jboss/exported/queue/QueueA");
		form.setMessageInBytes(readFile("test/bytes_message.json"));
		
		handler.send(form);
	}
	
	@Test
	public void testObjectMessage() throws Exception {
		JmsSendHandlerImpl handler = new JmsSendHandlerImpl();

		PublishForm form = new PublishForm();
		form.setMessageInputType("FILE");
		form.setMessageType("ObjectMessage");
		form.setDestinationJndiName("java:jboss/exported/queue/QueueA");
		form.setFullyQualClassName("com.cduvvuri.hqutam.vo.SampleDto");
		form.setMessageInBytes(readFile("test/object_message.json"));
		handler.send(form);
	}
	
	public byte[] readFile(String fileName) throws Exception {
		FileInputStream fis = null;
		byte[] bytes = null;
		try {
			File file = new File(fileName);
			fis = new FileInputStream(file);
			bytes = new byte[(int)file.length()];
			fis.read(new byte[(int)file.length()]);
			fis.close();
		} finally {
			
		}
		return bytes;
	}
}
