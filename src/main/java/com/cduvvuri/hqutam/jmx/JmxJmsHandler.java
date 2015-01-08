package com.cduvvuri.hqutam.jmx;

import java.util.List;

import com.cduvvuri.hqutam.vo.QueueInfo;
import com.cduvvuri.hqutam.vo.TopicInfo;

public interface JmxJmsHandler {
	List<QueueInfo> getQueuesInfo();
	List<TopicInfo> getTopicsInfo();
}
