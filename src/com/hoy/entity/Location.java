package com.hoy.entity;

public class Location {
	private String name;
	private float distance;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public boolean equal(Location lo){
		if(lo == null) return false;
		return this.name.equals(lo.getName());
	}
}
