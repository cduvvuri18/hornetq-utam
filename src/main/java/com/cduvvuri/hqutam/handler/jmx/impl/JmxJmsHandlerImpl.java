package com.cduvvuri.hqutam.handler.jmx.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cduvvuri.hqutam.jmx.JmxJmsHandler;
import com.cduvvuri.hqutam.locator.JmxJmsMbeanLocator;
import com.cduvvuri.hqutam.vo.QueueInfo;
import com.cduvvuri.hqutam.vo.TopicInfo;

public class JmxJmsHandlerImpl implements JmxJmsHandler {
	private static final Log LOGGER = LogFactory.getLog(JmxJmsHandlerImpl.class
			.getName());

	public QueueInfo getQueueInfo(String qName) {
		LOGGER.info("QueueInfo :: getQueueInfo :: QName :: "+qName);
		QueueInfo queueInfo = new QueueInfo();
		queueInfo.setName(qName);
		queueInfo.setObjects(JmxJmsMbeanLocator.getQueueAttr(qName));
		// queueInfo.setMessages(JmxJmsMbeanLocator.getQueueMessages(queueNames[i],
		// null));
		// queueInfo.setConsumers(JmxJmsMbeanLocator.getQueueConsumers(queueNames[i]));
		LOGGER.info("QueueInfo :: getQueueInfo :: QName :: "+queueInfo);
		return queueInfo;
	}

	public TopicInfo getTopicInfo(String tName) {
		LOGGER.info("TopicInfo :: getTopicInfo :: TName :: "+tName);
		TopicInfo topicInfo = new TopicInfo();
		topicInfo.setName(tName);
		topicInfo
				.setObjects(JmxJmsMbeanLocator.getTpoicAttr(tName));
		// topicInfo.setMessages(JmxJmsMbeanLocator.getTopicMessages(topicNames[i],
		// null));
		// topicInfo.setSubscriptions(JmxJmsMbeanLocator.getTopicSubscriptions(topicNames[i]));
		LOGGER.info("TopicInfo :: getTopicInfo :: TName :: "+tName);
		return topicInfo;
	}

	public String[] getAllQueueNames() {
		String[] qNames = JmxJmsMbeanLocator.getQueueNames();
		if(LOGGER.isDebugEnabled()) {
			for (int i = 0; i < qNames.length; i++) {
				LOGGER.debug("AllQNames :: getAllQueueNames :: "+qNames[i]);
			}
		}
		return qNames;
	}

	public String[] getAllTopicNames() {
		String[] tNames = JmxJmsMbeanLocator.getTopicNames();
		if(LOGGER.isDebugEnabled()) {
			for (int i = 0; i < tNames.length; i++) {
				LOGGER.debug("AllTopics :: getAllTopicNames :: "+tNames[i]);
			}
		}
		return JmxJmsMbeanLocator.getTopicNames();
	}
}
