package com.cduvvuri.hqutam.enums;

public enum MessageType {
	TEXT_MESSAGE("TextMessage"), MAP_MESSAGE("MapMessage"), STREAM_MESSAGE(
			"StreamMessage"), BYTES_MESSAGE("BytesMessage"), OBJECT_MESSAGE(
			"ObjectMessage");

	private String value;

	MessageType(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public static MessageType getType(String value) {
		for (MessageType messageInputType : MessageType.values()) {
			if (messageInputType.getValue().equals(value)) {
				return messageInputType;
			}
		}
		return null;
	}
}
