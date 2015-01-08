package com.cduvvuri.hqutam.vo;

import java.util.Arrays;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import com.cduvvuri.hqutam.enums.MessageInputType;

public class PublishForm {
	private String hqType;
	private String destinationName;
	private String destinationJndiName;
	private String messageType;
	private String messageInputType;
	private String messageByTextarea;
	private String destinationType;
	private String fullyQualClassName;
	private byte[] messageInBytes;

	public PublishForm() {
	}

	public String getHqType() {
		return hqType;
	}

	@FormParam("hqType")
	public void setHqType(String hqType) {
		this.hqType = hqType;
	}

	public String getMessageType() {
		return messageType;
	}

	@FormParam("messageType")
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public MessageInputType getMessageInputType() {
		return MessageInputType.valueOf(messageInputType);
	}

	@FormParam("messageInputType")
	public void setMessageInputType(String messageInputType) {
		this.messageInputType = messageInputType;
	}

	public String getMessageByTextarea() {
		return messageByTextarea;
	}

	@FormParam("messageByTextarea")
	public void setMessageByTextarea(String messageByTextarea) {
		this.messageByTextarea = messageByTextarea;
	}

	public String getDestinationName() {
		return destinationName;
	}

	@FormParam("destinationName")
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationJndiName() {
		return destinationJndiName;
	}

	@FormParam("destinationJndiName")
	public void setDestinationJndiName(String destinationJndiName) {
		this.destinationJndiName = destinationJndiName;
	}

	public String getDestinationType() {
		return destinationType;
	}

	@FormParam("destinationType")
	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}

	public byte[] getMessageInBytes() {
		return messageInBytes;
	}

	@FormParam("uploadedFile")
	@PartType("application/octet-stream")
	public void setMessageInBytes(byte[] messageInBytes) {
		this.messageInBytes = messageInBytes;
	}

	public String getFullyQualClassName() {
		return fullyQualClassName;
	}

	@FormParam("fullyQualClassName")
	public void setFullyQualClassName(String fullyQualClassName) {
		this.fullyQualClassName = fullyQualClassName;
	}

	@Override
	public String toString() {
		return "PublishForm [hqType=" + hqType + ", destinationName="
				+ destinationName + ", destinationJndiName="
				+ destinationJndiName + ", messageType=" + messageType
				+ ", messageInputType=" + messageInputType
				+ ", messageByTextarea=" + messageByTextarea
				+ ", destinationType=" + destinationType
				+ ", fullyQualClassName=" + fullyQualClassName
				+ ", messageInBytes=" + Arrays.toString(messageInBytes) + "]";
	}
}
