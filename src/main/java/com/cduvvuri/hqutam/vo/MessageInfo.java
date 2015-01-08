package com.cduvvuri.hqutam.vo;

public class MessageInfo {
	public enum MapMessageDataType {
		BYTE("BYTE"), LONG("LONG"), INTEGER("INTEGER"), BOOLEAN("BOOLEAN"), SHORT("SHORT"), CHARACTER(
				"CHARACTER"), BYTE_ARRAY("BYTE_ARRAY"), FLOAT("FLOAT"), DOUBLE("DOUBLE"), STRING("STRING");

		private String dataType;

		MapMessageDataType(String dataType) {
			this.dataType = dataType;
		}		
	}

	String key;
	MapMessageDataType type;
	String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public MapMessageDataType getType() {
		return type;
	}

	public void setType(MapMessageDataType type) {
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
