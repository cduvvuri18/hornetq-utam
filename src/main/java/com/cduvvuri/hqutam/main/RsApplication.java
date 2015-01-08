package com.cduvvuri.hqutam.main;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.cduvvuri.hqutam.monitoring.rest.impl.MonitoringRestServiceImpl;
import com.cduvvuri.hqutam.ut.rest.impl.UnitTestingRestServiceImpl;

public class RsApplication extends Application {
	public RsApplication() {
		
	}
	public Set<Object> getSingletons() {
		Set<Object> services = new HashSet<Object>();
		services.add(new UnitTestingRestServiceImpl());
		services.add(new MonitoringRestServiceImpl());
		return services;
	}
}
