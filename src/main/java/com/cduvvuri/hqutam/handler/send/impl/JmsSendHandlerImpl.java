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
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cduvvuri.hqutam.enums.MessageType;
import com.cduvvuri.hqutam.handler.send.SendHandler;
import com.cduvvuri.hqutam.locator.JmsServiceLocator;
import com.cduvvuri.hqutam.utils.FormUtils;
import com.cduvvuri.hqutam.utils.JsonUtils;
import com.cduvvuri.hqutam.utils.StringUtils;
import com.cduvvuri.hqutam.vo.MessageInfo;
import com.cduvvuri.hqutam.vo.PublishForm;

public class JmsSendHandlerImpl implements SendHandler {
	private static final Log LOGGER = LogFactory
			.getLog(JmsSendHandlerImpl.class.getName());

	public boolean send(PublishForm form) {
		ConnectionFactory cf = null;
		Connection connection = null;
		try {
			cf = JmsServiceLocator.getConnectionFactory();
			Destination dest = JmsServiceLocator.getDestination(StringUtils
					.getJndiName(form.getDestinationJndiName()));
			LOGGER.info("destinatation looked up seccessfully " + dest);
			connection = cf.createConnection();
			LOGGER.info("connection created successfully " + connection);
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer publisher = session.createProducer(dest);
			connection.start();

			switch (MessageType.getType(form.getMessageType())) {
			case TEXT_MESSAGE:
				TextMessage txtMessage = session.createTextMessage(FormUtils.getTextMessage(form
						));
				publisher.send(txtMessage);
				break;
			case MAP_MESSAGE:
				MapMessage mapMessage = session.createMapMessage();
				Collection<MessageInfo> info = JsonUtils.convertJSON(
						form.getMessageInBytes(), List.class,
						MessageInfo.class);
				fillMapMessage(mapMessage, info);
				publisher.send(mapMessage);
				break;
			case STREAM_MESSAGE:
				StreamMessage streamMessage = session.createStreamMessage();
				info = JsonUtils.convertJSON(form.getMessageInBytes(),
						List.class, MessageInfo.class);
				fillStreamMessage(streamMessage, info);
				publisher.send(streamMessage);
				break;
			case BYTES_MESSAGE:
				BytesMessage bytesMessage = session.createBytesMessage();
				info = JsonUtils.convertJSON(form.getMessageInBytes(),
						List.class, MessageInfo.class);
				fillBytesMessage(bytesMessage, info);
				publisher.send(bytesMessage);
				break;
			case OBJECT_MESSAGE:
				ObjectMessage objMessage = session.createObjectMessage();
				objMessage.setObject((Serializable)JsonUtils.convertJSON(form.getMessageInBytes(),
						Class.forName(form.getFullyQualClassName()))); 
				publisher.send(objMessage);
				break;
			default:
				break;
			}
			return true;
		} catch (Exception e) {
			LOGGER.error("Failed to send Message", e);
			throw new RuntimeException("Failed to send Message", e);
		} finally {
			closeConnection(connection);
		}
	}

	private void fillBytesMessage(BytesMessage bytesMessage,
			Collection<MessageInfo> info) throws NumberFormatException,
			JMSException, IOException {
		for (Iterator<MessageInfo> iterator = info.iterator(); iterator
				.hasNext();) {
			MessageInfo mapMessageInfo = iterator.next();
			switch (mapMessageInfo.getType()) {
			case INTEGER:
				bytesMessage
						.writeInt(Integer.valueOf(mapMessageInfo.getValue()));
				break;
			case BYTE:
				bytesMessage.writeByte(Byte.valueOf(mapMessageInfo.getValue()));
				break;
			case LONG:
				bytesMessage.writeLong(Long.valueOf(mapMessageInfo.getValue()));

			case BOOLEAN:
				bytesMessage.writeBoolean(Boolean.valueOf(mapMessageInfo
						.getValue()));

			case FLOAT:
				bytesMessage
						.writeFloat(Float.valueOf(mapMessageInfo.getValue()));

			case DOUBLE:
				bytesMessage.writeDouble(Double.valueOf(mapMessageInfo
						.getValue()));

			case SHORT:
				bytesMessage
						.writeShort(Short.valueOf(mapMessageInfo.getValue()));

			case CHARACTER:
				bytesMessage.writeChar(Character.valueOf(mapMessageInfo
						.getValue().charAt(0)));

			case BYTE_ARRAY:
				File file = new File(mapMessageInfo.getValue());
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
				bytesMessage.writeUTF(mapMessageInfo.getValue());
				break;

			}

		}

	}

	private void fillStreamMessage(StreamMessage streamMessage,
			Collection<MessageInfo> info) throws NumberFormatException,
			JMSException, IOException {
		for (Iterator<MessageInfo> iterator = info.iterator(); iterator
				.hasNext();) {
			MessageInfo mapMessageInfo = iterator.next();
			switch (mapMessageInfo.getType()) {
			case INTEGER:
				streamMessage.writeInt(Integer.valueOf(mapMessageInfo
						.getValue()));
				break;
			case BYTE:
				streamMessage
						.writeByte(Byte.valueOf(mapMessageInfo.getValue()));
				break;
			case LONG:
				streamMessage
						.writeLong(Long.valueOf(mapMessageInfo.getValue()));

			case BOOLEAN:
				streamMessage.writeBoolean(Boolean.valueOf(mapMessageInfo
						.getValue()));

			case FLOAT:
				streamMessage.writeFloat(Float.valueOf(mapMessageInfo
						.getValue()));

			case DOUBLE:
				streamMessage.writeDouble(Double.valueOf(mapMessageInfo
						.getValue()));

			case SHORT:
				streamMessage.writeShort(Short.valueOf(mapMessageInfo
						.getValue()));

			case CHARACTER:
				streamMessage.writeChar(Character.valueOf(mapMessageInfo
						.getValue().charAt(0)));

			case BYTE_ARRAY:
				File file = new File(mapMessageInfo.getValue());
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
				streamMessage.writeString(mapMessageInfo.getValue());
				break;

			}

		}

	}

	private void fillMapMessage(MapMessage mapMessage,
			Collection<MessageInfo> info) throws NumberFormatException,
			JMSException, IOException {
		for (Iterator<MessageInfo> iterator = info.iterator(); iterator
				.hasNext();) {
			MessageInfo mapMessageInfo = iterator.next();
			switch (mapMessageInfo.getType()) {
			case INTEGER:
				mapMessage.setInt(mapMessageInfo.getKey(),
						Integer.valueOf(mapMessageInfo.getValue()));
				break;
			case BYTE:
				mapMessage.setByte(mapMessageInfo.getKey(),
						Byte.valueOf(mapMessageInfo.getValue()));
				break;
			case LONG:
				mapMessage.setLong(mapMessageInfo.getKey(),
						Long.valueOf(mapMessageInfo.getValue()));

			case BOOLEAN:
				mapMessage.setBoolean(mapMessageInfo.getKey(),
						Boolean.valueOf(mapMessageInfo.getValue()));

			case FLOAT:
				mapMessage.setFloat(mapMessageInfo.getKey(),
						Float.valueOf(mapMessageInfo.getValue()));

			case DOUBLE:
				mapMessage.setDouble(mapMessageInfo.getKey(),
						Double.valueOf(mapMessageInfo.getValue()));

			case SHORT:
				mapMessage.setShort(mapMessageInfo.getKey(),
						Short.valueOf(mapMessageInfo.getValue()));

			case CHARACTER:
				mapMessage.setChar(mapMessageInfo.getKey(),
						Character.valueOf(mapMessageInfo.getValue().charAt(0)));

			case BYTE_ARRAY:
				File file = new File(mapMessageInfo.getValue());
				FileInputStream fin = new FileInputStream(file);
				byte[] fileContent = new byte[(int) file.length()];
				try {
					fin.read(fileContent);
				} finally {
					fin.close();
				}
				mapMessage.setBytes(mapMessageInfo.getKey(), fileContent);
				break;

			case STRING:
				mapMessage.setString(mapMessageInfo.getKey(),
						mapMessageInfo.getValue());
				break;

			}

		}

	}

	private void closeConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (JMSException jmse) {
			System.out.println("Could not close connection " + con
					+ " exception was " + jmse);
		}
	}
}
