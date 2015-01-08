package com.cduvvuri.hqutam.monitoring.rest.impl;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cduvvuri.hqutam.factory.HandlerFactory;
import com.cduvvuri.hqutam.jmx.JmxJmsHandler;
import com.cduvvuri.hqutam.monitoring.rest.MonitoringRestService;
import com.cduvvuri.hqutam.vo.QueueInfo;
import com.cduvvuri.hqutam.vo.TopicInfo;

@Path("/")
public class MonitoringRestServiceImpl implements MonitoringRestService {
	private static final Log LOGGER = LogFactory.getLog(MonitoringRestService.class);
	@GET
	@Path("/getJmsQInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public List<QueueInfo> getJmsQInfo() {
		LOGGER.info("Retrieve QInfo :: getQInfo");
		JmxJmsHandler jmxJmsHandler = HandlerFactory.getJmxJmsHandler();
		return jmxJmsHandler.getQueuesInfo();
	}

	@GET
	@Path("/getJmsTInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TopicInfo> getTopicInfo() {
		LOGGER.info("Retrieve Topic Info :: getTopicInfo");
		JmxJmsHandler jmxJmsHandler = HandlerFactory.getJmxJmsHandler();
		return jmxJmsHandler.getTopicsInfo();
	}
}
