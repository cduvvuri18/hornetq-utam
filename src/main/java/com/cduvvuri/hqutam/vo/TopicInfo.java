package com.cduvvuri.hqutam.vo;

import java.util.Map;

public class TopicInfo {
	String name;
	String subscriptions;
	String messages;
	Map<String, Object> objects;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(String subscriptions) {
		this.subscriptions = subscriptions;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public Map<String, Object> getObjects() {
		return objects;
	}

	public void setObjects(Map<String, Object> objects) {
		this.objects = objects;
	}

	@Override
	public String toString() {
		return "TopicInfo [name=" + name + ", subscriptions=" + subscriptions
				+ ", messages=" + messages + "]";
	}
}
