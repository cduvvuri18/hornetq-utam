package com.cduvvuri.hqutam.ut.rest.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.cduvvuri.hqutam.factory.HandlerFactory;
import com.cduvvuri.hqutam.handler.send.SendHandler;
import com.cduvvuri.hqutam.type.HornetQType;
import com.cduvvuri.hqutam.ut.rest.UnitTestingRestService;
import com.cduvvuri.hqutam.vo.PublishForm;

@Path("/")
public class UnitTestingRestServiceImpl implements UnitTestingRestService {
	private static final Log LOGGER = LogFactory.getLog(UnitTestingRestService.class);
	public UnitTestingRestServiceImpl() {
		super();
	}
	
	@POST
	@Path("/pub")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	public String publishMessage(@MultipartForm PublishForm form) {
		LOGGER.info("Publish Form :: publishMessage() :: "+form);
		SendHandler sendHandler = HandlerFactory.getSendHandler(HornetQType.valueOf(form.getHqType()));
		sendHandler.send(form);
		return "success";
	}
}
