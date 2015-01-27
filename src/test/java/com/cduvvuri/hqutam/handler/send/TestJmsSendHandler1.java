package com.cduvvuri.hqutam.handler.send;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.schema.JsonSchema;
import org.junit.Ignore;
import org.junit.Test;

import com.cduvvuri.hqutam.handler.send.impl.JmsSendHandlerImpl;
import com.cduvvuri.hqutam.utils.JsonUtils;
import com.cduvvuri.hqutam.vo.MessageInfo;
import com.cduvvuri.hqutam.vo.PublishForm;
import com.cduvvuri.hqutam.vo.SampleDto;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
/*import com.cduvvuri.hqutam.settings.MessageSettings.MessageType;
*/
public class TestJmsSendHandler1 {
	@Test
	@Ignore
	public void testSend() {
/*		JmsSendHandlerImpl handler = new JmsSendHandlerImpl();
		MessageSettings settings = new MessageSettings();
		settings.setDestinationName("testQueue");
		settings.setMessage("Hi this is UTAM");
		settings.setMessageType(MessageType.TEXT);
		handler.send(settings);
*/	
		JmsSendHandlerImpl handler = new JmsSendHandlerImpl();
		PublishForm form = new PublishForm();
		form.setDestinationJndiName("java:jboss/exported/queue/QueueA");
		form.setMessageByTextarea("Hi this is chaitanya");
		handler.send(form);
	}
	
	@Test
	public void testMapMessage() throws Exception {
/*		String jsonMessage = "{\"name\":\"chaitanya\",\"arrayStr\":[\"chaitu\",\"hello\"], \"key\":{\"value\":{\"key\":\"value\"}}}";
		ObjectMapper objMapper = new ObjectMapper();
		HashMap map = objMapper.readValue(jsonMessage, HashMap.class);
		System.out.println(map);
		
		JmsSendHandlerImpl handler = new JmsSendHandlerImpl();
		PublishForm form = new PublishForm();
		form.setDestinationJndiName("java:jboss/exported/queue/QueueA");
		form.setMessageType("MapMessage");
		form.setMessageByTextarea(jsonMessage);
		
		handler.send(form);
*/	
		//String jsonMessage = "[{\"key\":\"key1\", \"type\":\"INTEGER\",\"value\":\"15\"},{\"key\":\"key2\", \"type\":\"BYTE_ARRAY\",\"value\":\"E:/Booked Car Parking List.pdf\"}]";
		
		//String jsonMessage = "[{\"key\":\"key1\", \"type\":\"INTEGER\",\"value\":\"15\"},{\"key\":\"key2\", \"type\":\"BYTE_ARRAY\",\"value\":{\"key\":\"value2\"}}]";
		
		//System.out.println(JsonUtils.convertJSON(jsonMessage, List.class, MessageInfo.class));
		
/*		JmsSendHandlerImpl handler = new JmsSendHandlerImpl();
		PublishForm form = new PublishForm();
		form.setDestinationJndiName("java:jboss/exported/queue/QueueA");
		form.setMessageType("MapMessage");
		form.setMessageByTextarea(jsonMessage);
		handler.send(form);*/

        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        mapper.acceptJsonFormatVisitor(mapper.constructType(SampleDto.class), visitor);
        com.fasterxml.jackson.module.jsonSchema.JsonSchema schema = visitor.finalSchema();
        
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema));
	}
}
