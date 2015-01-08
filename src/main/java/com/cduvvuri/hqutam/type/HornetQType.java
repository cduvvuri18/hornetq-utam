package com.cduvvuri.hqutam.type;

public enum HornetQType {
	CORE("CORE"),
	JMS("JMS");
	
	String type;
	HornetQType(String type) {
		this.type = type;
	}
}
