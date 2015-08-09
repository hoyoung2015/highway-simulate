package com.hoy.entity;

import java.util.Calendar;

public final class XnStation extends Station {
	private static XnStation xnStation = null;
	private XnStation(String name, Location location,Calendar timer) {
		super(name, location,timer);
		xnStation = this;
	}
	public static XnStation getInstance(String name, Location location,Calendar timer){
		if(xnStation == null){
			xnStation = new XnStation(name, location,timer);
		}
		return xnStation;
	}
}
