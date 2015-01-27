package com.cduvvuri.hqutam.handler.send.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.StreamMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cduvvuri.hqutam.enums.MessageType;
import com.cduvvuri.hqutam.handler.send.SendHandler;
import com.cduvvuri.hqutam.locator.JmsServiceLocator;
import com.cduvvuri.hqutam.utils.FormUtils;
import com.cduvvuri.hqutam.utils.JsonUtils;
import com.cduvvuri.hqutam.utils.StringUtils;
import com.cduvvuri.hqutam.vo.MessageInfo;
import com.cduvvuri.hqutam.vo.MessagePropertyInfo;
import com.cduvvuri.hqutam.vo.PublishForm;

public class JmsSendHandlerImpl implements SendHandler {
	private static final Log LOGGER = LogFactory
			.getLog(JmsSendHandlerImpl.class.getName());

	public boolean send(PublishForm form) {
		Connection connection = null;
		Session session = null;
		try {
			Destination dest = JmsServiceLocator.getDestination(StringUtils
					.getJndiName(form.getDestinationJndiName()));
			LOGGER.info("destinatation looked up seccessfully " + dest);
			connection = JmsServiceLocator.getConnection();
			LOGGER.info("connection created successfully " + connection);
			session = connection.createSession(true,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer publisher = session.createProducer(dest);
			Message message = null;
			switch (MessageType.getType(form.getMessageType())) {
			case TEXT_MESSAGE:
				message = session.createTextMessage(FormUtils
						.getTextMessage(form));
				break;
			case MAP_MESSAGE:
				MapMessage mapMessage = session.createMapMessage();
				Collection<MessageInfo> info = JsonUtils
						.convertJSON(form.getMessageInBytes(), List.class,
								MessageInfo.class);
				fillMapMessage(mapMessage, info);
				message = mapMessage;
				break;
			case STREAM_MESSAGE:
				StreamMessage streamMessage = session.createStreamMessage();
				info = JsonUtils.convertJSON(form.getMessageInBytes(),
						List.class, MessageInfo.class);
				fillStreamMessage(streamMessage, info);
				message = streamMessage;
				break;
			case BYTES_MESSAGE:
				BytesMessage bytesMessage = session.createBytesMessage();
				info = JsonUtils.convertJSON(form.getMessageInBytes(),
						List.class, MessageInfo.class);
				fillBytesMessage(bytesMessage, info);
				message = bytesMessage;
				break;
			case OBJECT_MESSAGE:
				ObjectMessage objMessage = session.createObjectMessage();
				objMessage.setObject((Serializable) JsonUtils.convertJSON(
						form.getMessageInBytes(),
						Class.forName(form.getFullyQualClassName())));
				message = objMessage;
				break;
			default:
				break;
			}
			if (form.getMessagePropertiesInBytes() != null && form.getMessagePropertiesInBytes().length != 0) {
				LOGGER.debug("message properties :: "+form.getMessagePropertiesInBytes());
				Collection<MessagePropertyInfo> propertyInfos = JsonUtils
						.convertJSON(form.getMessagePropertiesInBytes(),
								List.class, MessagePropertyInfo.class);
				fillMessageProperties(message, propertyInfos);
			}
			publisher.send(message);
			session.commit();
			return true;
		} catch (Exception e) {
			LOGGER.error("Failed to send Message", e);
			try {
				session.rollback();
			} catch (JMSException e1) {
				LOGGER.error("Failed to close session", e);
			}
			throw new RuntimeException("Failed to send Message", e);
		} finally {
			try {
				session.close();
			} catch (JMSException e) {
				LOGGER.error("Failed to close session", e);
			}
		}
	}

	private void fillMessageProperties(Message message,
			Collection<MessagePropertyInfo> propertyInfos)
			throws NumberFormatException, JMSException {
		for (Iterator<MessagePropertyInfo> iterator = propertyInfos.iterator(); iterator
				.hasNext();) {
			MessagePropertyInfo propertyInfo = iterator.next();
			switch (propertyInfo.getType()) {
			case INTEGER:
				message.setIntProperty(propertyInfo.getKey(),
						Integer.valueOf(propertyInfo.getValue()));
				break;
			case BYTE:
				message.setByteProperty(propertyInfo.getKey(),
						Byte.valueOf(propertyInfo.getValue()));
				break;
			case LONG:
				message.setLongProperty(propertyInfo.getKey(),
						Long.valueOf(propertyInfo.getValue()));
				break;
			case BOOLEAN:
				message.setBooleanProperty(propertyInfo.getKey(),
						Boolean.valueOf(propertyInfo.getValue()));
				break;
			case FLOAT:
				message.setFloatProperty(propertyInfo.getKey(),
						Float.valueOf(propertyInfo.getValue()));
				break;
			case DOUBLE:
				message.setDoubleProperty(propertyInfo.getKey(),
						Double.valueOf(propertyInfo.getValue()));
				break;
			case SHORT:
				message.setShortProperty(propertyInfo.getKey(),
						Short.valueOf(propertyInfo.getValue()));
				break;
			case STRING:
				message.setStringProperty(propertyInfo.getKey(),
						propertyInfo.getValue());
			}
		}
	}

	private void fillBytesMessage(BytesMessage bytesMessage,
			Collection<MessageInfo> info) throws NumberFormatException,
			JMSException, IOException {
		for (Iterator<MessageInfo> iterator = info.iterator(); iterator
				.hasNext();) {
			MessageInfo messageInfo = iterator.next();
			switch (messageInfo.getType()) {
			case INTEGER:
				bytesMessage.writeInt(Integer.valueOf(messageInfo.getValue()));
				break;
			case BYTE:
				bytesMessage.writeByte(Byte.valueOf(messageInfo.getValue()));
				break;
			case LONG:
				bytesMessage.writeLong(Long.valueOf(messageInfo.getValue()));
				break;
			case BOOLEAN:
				bytesMessage.writeBoolean(Boolean.valueOf(messageInfo
						.getValue()));
				break;
			case FLOAT:
				bytesMessage.writeFloat(Float.valueOf(messageInfo.getValue()));
				break;
			case DOUBLE:
				bytesMessage
						.writeDouble(Double.valueOf(messageInfo.getValue()));
				break;
			case SHORT:
				bytesMessage.writeShort(Short.valueOf(messageInfo.getValue()));
				break;
			case CHARACTER:
				bytesMessage.writeChar(Character.valueOf(messageInfo.getValue()
						.charAt(0)));
				break;
			case BYTE_ARRAY:
				File file = new File(messageInfo.getValue());
				FileInputStream fin = new FileInputStream(file);
				byte[] fileContent = new byte[(int) file.length()];
				try {
					fin.read(fileContent);
				} finally {
					fin.close();
				}
				bytesMessage.writeBytes(fileContent);
				break;

			case STRING:
				bytesMessage.writeUTF(messageInfo.getValue());
				break;
			}

		}

	}

	private void fillStreamMessage(StreamMessage streamMessage,
			Collection<MessageInfo> info) throws NumberFormatException,
			JMSException, IOException {
		for (Iterator<MessageInfo> iterator = info.iterator(); iterator
				.hasNext();) {
			MessageInfo messageInfo = iterator.next();
			switch (messageInfo.getType()) {
			case INTEGER:
				streamMessage.writeInt(Integer.valueOf(messageInfo.getValue()));
				break;
			case BYTE:
				streamMessage.writeByte(Byte.valueOf(messageInfo.getValue()));
				break;
			case LONG:
				streamMessage.writeLong(Long.valueOf(messageInfo.getValue()));
				break;
			case BOOLEAN:
				streamMessage.writeBoolean(Boolean.valueOf(messageInfo
						.getValue()));
				break;
			case FLOAT:
				streamMessage.writeFloat(Float.valueOf(messageInfo.getValue()));
				break;
			case DOUBLE:
				streamMessage
						.writeDouble(Double.valueOf(messageInfo.getValue()));
				break;
			case SHORT:
				streamMessage.writeShort(Short.valueOf(messageInfo.getValue()));
				break;
			case CHARACTER:
				streamMessage.writeChar(Character.valueOf(messageInfo
						.getValue().charAt(0)));
				break;
			case BYTE_ARRAY:
				File file = new File(messageInfo.getValue());
				FileInputStream fin = new FileInputStream(file);
				byte[] fileContent = new byte[(int) file.length()];
				try {
					fin.read(fileContent);
				} finally {
					fin.close();
				}
				streamMessage.writeBytes(fileContent);
				break;

			case STRING:
				streamMessage.writeString(messageInfo.getValue());
				break;

			}

		}

	}

	private void fillMapMessage(MapMessage mapMessage,
			Collection<MessageInfo> info) throws NumberFormatException,
			JMSException, IOException {
		for (Iterator<MessageInfo> iterator = info.iterator(); iterator
				.hasNext();) {
			MessageInfo messageInfo = iterator.next();
			switch (messageInfo.getType()) {
			case INTEGER:
				mapMessage.setInt(messageInfo.getKey(),
						Integer.valueOf(messageInfo.getValue()));
				break;
			case BYTE:
				mapMessage.setByte(messageInfo.getKey(),
						Byte.valueOf(messageInfo.getValue()));
				break;
			case LONG:
				mapMessage.setLong(messageInfo.getKey(),
						Long.valueOf(messageInfo.getValue()));
				break;
			case BOOLEAN:
				mapMessage.setBoolean(messageInfo.getKey(),
						Boolean.valueOf(messageInfo.getValue()));
				break;
			case FLOAT:
				mapMessage.setFloat(messageInfo.getKey(),
						Float.valueOf(messageInfo.getValue()));
				break;
			case DOUBLE:
				mapMessage.setDouble(messageInfo.getKey(),
						Double.valueOf(messageInfo.getValue()));
				break;
			case SHORT:
				mapMessage.setShort(messageInfo.getKey(),
						Short.valueOf(messageInfo.getValue()));
				break;
			case CHARACTER:
				mapMessage.setChar(messageInfo.getKey(),
						Character.valueOf(messageInfo.getValue().charAt(0)));
				break;
			case BYTE_ARRAY:
				File file = new File(messageInfo.getValue());
				FileInputStream fin = new FileInputStream(file);
				byte[] fileContent = new byte[(int) file.length()];
				try {
					fin.read(fileContent);
				} finally {
					fin.close();
				}
				mapMessage.setBytes(messageInfo.getKey(), fileContent);
				break;

			case STRING:
				mapMessage.setString(messageInfo.getKey(),
						messageInfo.getValue());
				break;

			}

		}

	}
}
