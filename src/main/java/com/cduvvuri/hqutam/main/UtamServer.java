package com.cduvvuri.hqutam.main;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

public class UtamServer {
	private static final Log LOGGER = LogFactory.getLog(UtamServer.class
			.getName());

	public UtamServer() {

	}

	public static void main(String[] args) {
		Server server = new Server(getPort());

		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[] { getRsContext(),
				getStaticWebContent() });

		server.setHandler(contexts);

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			LOGGER.error(e);
		}

	}

	private static final ServletContextHandler getRsContext() {
		ServletContextHandler cxt = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		cxt.setContextPath("/hqutam");
		cxt.setInitParameter("resteasy.servlet.mapping.prefix", "/rs");

		ServletHolder h = new ServletHolder(new HttpServletDispatcher());
		h.setInitParameter("javax.ws.rs.Application",
				"com.cduvvuri.hqutam.main.RsApplication");

		cxt.addServlet(h, "/rs/*");
		return cxt;
	}

	private static final ContextHandler getStaticWebContent() {
		ContextHandler cxt = new ContextHandler();
		cxt.setContextPath("/html");
		ResourceHandler rh = new ResourceHandler();
		File dir = new File("./html");
		if (!dir.exists()) {
			throw new RuntimeException("Directory 'html' does not exist");
		}
		rh.setBaseResource(Resource.newResource(dir));
		cxt.setHandler(rh);
		return cxt;
	}

	private static final int getPort() {
		try {
			return Integer.parseInt(System.getProperty("port"));
		} catch (NumberFormatException e) {
			LOGGER.warn("Starting on default port : 9091 :: For custom port provide system property in CLI :: -Dport=<port>");
			return 9091;
		}
	}
}
