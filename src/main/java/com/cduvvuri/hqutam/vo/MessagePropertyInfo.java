package com.cduvvuri.hqutam.vo;

public class MessagePropertyInfo {
	public enum MessagePropertyDataType {
		BYTE("BYTE"), LONG("LONG"), INTEGER("INTEGER"), BOOLEAN("BOOLEAN"), SHORT(
				"SHORT"), FLOAT("FLOAT"), DOUBLE("DOUBLE"), STRING("STRING");

		@SuppressWarnings("unused")
		private String dataType;

		MessagePropertyDataType(String dataType) {
			this.dataType = dataType;
		}
	}

	private String key;
	private MessagePropertyDataType type;
	private String value;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public MessagePropertyDataType getType() {
		return type;
	}

	public void setType(MessagePropertyDataType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "MapMessageInfo [type=" + type + ", value=" + value + "]";
	}

}
