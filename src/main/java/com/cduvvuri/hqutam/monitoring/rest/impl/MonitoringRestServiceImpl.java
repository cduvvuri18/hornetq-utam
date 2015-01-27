package com.cduvvuri.hqutam.monitoring.rest.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
	@Path("/getAllQNames")
	@Produces(MediaType.APPLICATION_JSON)
	public String[] getAllQNames() {
		LOGGER.info("Retrieve QNames :: getQAllQNames");
		JmxJmsHandler jmxJmsHandler = HandlerFactory.getJmxJmsHandler();
		return jmxJmsHandler.getAllQueueNames();
	}	
	
	@GET
	@Path("/getAllTNames")
	@Produces(MediaType.APPLICATION_JSON)
	public String[] getAllTopicNames() {
		LOGGER.info("Retrieve Topic Info :: getAllTopicNames");
		JmxJmsHandler jmxJmsHandler = HandlerFactory.getJmxJmsHandler();
		return jmxJmsHandler.getAllTopicNames();
	}

	@GET
	@Path("/getQInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public QueueInfo getQueueInfo(@QueryParam("dName") String qName) {
		LOGGER.info("Retrieve Topic Info :: getQueueInfo :: "+qName);
		JmxJmsHandler jmxJmsHandler = HandlerFactory.getJmxJmsHandler();
		return jmxJmsHandler.getQueueInfo(qName);
	}

	@GET
	@Path("/getTInfo")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public TopicInfo getTopicInfo(@QueryParam("dName") String tName) {
		LOGGER.info("Retrieve Topic Info :: getTopicInfo :: "+tName);
		JmxJmsHandler jmxJmsHandler = HandlerFactory.getJmxJmsHandler();
		return jmxJmsHandler.getTopicInfo(tName);
	}
}
