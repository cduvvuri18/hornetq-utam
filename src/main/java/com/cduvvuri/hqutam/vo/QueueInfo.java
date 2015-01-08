package com.cduvvuri.hqutam.vo;

import java.util.Map;

public class QueueInfo {
	String name;
	Map<String, Object> objects;
	String messages;
	String consumers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getObjects() {
		return objects;
	}

	public void setObjects(Map<String, Object> objects) {
		this.objects = objects;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public String getConsumers() {
		return consumers;
	}

	public void setConsumers(String consumers) {
		this.consumers = consumers;
	}

	@Override
	public String toString() {
		return "QueueInfo [name=" + name + ", objects=" + objects
				+ ", messages=" + messages + ", consumers=" + consumers + "]";
	}
}
