package edu.uccs.msander6;

import android.location.Location;

public class LocationResource {
	private String location_text;
	private int radius;
	private String name;
	private Location location;
	
	public LocationResource() {
		super();
	}
	public LocationResource(String name, String location_text, int radius) {
		super();
		this.name = name;
		this.location_text = location_text;
		String[] values = location_text.split(",");
		this.radius = radius;
		this.location = new Location(name);
		location.setLatitude(Double.parseDouble(values[0]));
		location.setLongitude(Double.parseDouble(values[1]));
	}
	public String getLocation_text() {
		return location_text;
	}
	public void setLocation_text(String location_text) {
		this.location_text = location_text;
	}
	public double getLat() {
		return location.getLatitude();
	}
	public void setLat(double lat) {
		this.location.setLatitude(lat);
	}
	public double getLon() {
		return this.location.getLongitude();
	}
	public void setLon(double lon) {
		this.location.setLongitude(lon);
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	
}
