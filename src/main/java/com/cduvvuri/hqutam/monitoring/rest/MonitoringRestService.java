package com.cduvvuri.hqutam.monitoring.rest;

import com.cduvvuri.hqutam.vo.QueueInfo;
import com.cduvvuri.hqutam.vo.TopicInfo;

public interface MonitoringRestService {
	public String[] getAllQNames();
	public String[] getAllTopicNames();
	public QueueInfo getQueueInfo(String qName);
	public TopicInfo getTopicInfo(String tName);
}
