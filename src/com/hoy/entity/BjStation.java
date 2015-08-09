package com.hoy.entity;

import java.util.Calendar;

public final class BjStation extends Station {

	private static BjStation bjStation = null;
	private BjStation(String name, Location location,Calendar timer) {
		super(name, location,timer);
		bjStation = this;
	}
	public static BjStation getInstance(String name, Location location,Calendar timer){
		if(bjStation == null){
			bjStation = new BjStation(name, location,timer);
		}
		return bjStation;
	}
}
