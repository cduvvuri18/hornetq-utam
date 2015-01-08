package com.cduvvuri.hqutam.handler.jmx.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cduvvuri.hqutam.jmx.JmxJmsHandler;
import com.cduvvuri.hqutam.locator.JmxJmsMbeanLocator;
import com.cduvvuri.hqutam.vo.QueueInfo;
import com.cduvvuri.hqutam.vo.TopicInfo;

public class JmxJmsHandlerImpl implements JmxJmsHandler {
	private static final Log LOGGER = LogFactory.getLog(JmxJmsHandlerImpl.class
			.getName());

	public List<QueueInfo> getQueuesInfo() {
		List<QueueInfo> infotList = new ArrayList<QueueInfo>();
		String[] queueNames = JmxJmsMbeanLocator.getQueueNames();
		for (int i = 0; i < queueNames.length; i++) {
			QueueInfo queueInfo = new QueueInfo();
			queueInfo.setName(queueNames[i]);
			queueInfo.setObjects(JmxJmsMbeanLocator.getQueueAttr(queueNames[i]));
			//queueInfo.setMessages(JmxJmsMbeanLocator.getQueueMessages(queueNames[i], null));
			//queueInfo.setConsumers(JmxJmsMbeanLocator.getQueueConsumers(queueNames[i]));
			infotList.add(queueInfo);
		}
		return infotList;
	}
	
	public List<TopicInfo> getTopicsInfo() {
		List<TopicInfo> topicInfoList = new ArrayList<TopicInfo>();
		String[] topicNames = JmxJmsMbeanLocator.getTopicNames();
		for (int i = 0; i < topicNames.length; i++) {
			TopicInfo topicInfo = new TopicInfo();
			topicInfo.setName(topicNames[i]);
			topicInfo.setObjects(JmxJmsMbeanLocator.getTpoicAttr(topicNames[i]));
			//topicInfo.setMessages(JmxJmsMbeanLocator.getTopicMessages(topicNames[i], null));
			//topicInfo.setSubscriptions(JmxJmsMbeanLocator.getTopicSubscriptions(topicNames[i]));
			topicInfoList.add(topicInfo);
		}
		return topicInfoList;
	}
}
