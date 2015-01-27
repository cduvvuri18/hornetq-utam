package com.cduvvuri.hqutam.jmx;

import com.cduvvuri.hqutam.vo.QueueInfo;
import com.cduvvuri.hqutam.vo.TopicInfo;

public interface JmxJmsHandler {
	QueueInfo getQueueInfo(String qName);
	TopicInfo getTopicInfo(String tName);
	
	String[] getAllQueueNames();
	String[] getAllTopicNames();
}
