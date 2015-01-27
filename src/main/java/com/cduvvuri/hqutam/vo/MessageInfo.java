package com.cduvvuri.hqutam.vo;

public class MessageInfo {
	public enum MessageDataType {
		BYTE("BYTE"), LONG("LONG"), INTEGER("INTEGER"), BOOLEAN("BOOLEAN"), SHORT("SHORT"), CHARACTER(
				"CHARACTER"), BYTE_ARRAY("BYTE_ARRAY"), FLOAT("FLOAT"), DOUBLE("DOUBLE"), STRING("STRING");

		private String dataType;

		MessageDataType(String dataType) {
			this.dataType = dataType;
		}		
	}

	private String key;
	private MessageDataType type;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public MessageDataType getType() {
		return type;
	}

	public void setType(MessageDataType type) {
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
		return "MapMessageInfo [key=" + key + ", type=" + type + ", value="
				+ value + "]";
	}

}
