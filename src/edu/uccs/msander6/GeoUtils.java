package edu.uccs.msander6;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;

public class GeoUtils {

	public static boolean inRangeOfActiveLocation (boolean failSecure, SharedPreferences sharedPrefs, LocationManager lm) {
		ArrayList<LocationResource> resourceList = generateResourceList(sharedPrefs);
		
		String provider = getMostAccurateProvider(lm);
		Location here = lm.getLastKnownLocation(provider);
		if (here != null) {
			for (LocationResource lr : resourceList){
				if (here.distanceTo(lr.getLocation()) <= lr.getRadius()) {
					return true;
				}
			}
		}
		
		if (failSecure)
			return false;
		else
			return true;
	}
	
	public static ArrayList<LocationResource> generateResourceList(SharedPreferences sharedPrefs) {
		ArrayList<LocationResource> resourceList = new ArrayList<LocationResource>();
		if (sharedPrefs.getBoolean("location_enabled1", false))
			resourceList.add(generateLocationResource("1",sharedPrefs));
		if (sharedPrefs.getBoolean("location_enabled2", false))
			resourceList.add(generateLocationResource("2",sharedPrefs));
		if (sharedPrefs.getBoolean("location_enabled3", false))
			resourceList.add(generateLocationResource("3",sharedPrefs));
		return resourceList;
	}
	
	public static LocationResource generateLocationResource(String key, SharedPreferences sharedPrefs) {
		String location_text = sharedPrefs.getString("location_text" + key, "0.0,0.0");
		String radius_text = sharedPrefs.getString("locaiton_radius" + key, "100");
		int r1 = Integer.parseInt(radius_text);
		
		LocationResource l1r = new LocationResource("Location" + key, location_text, r1);
		return l1r;
	}
	
	
	public static String loc2Str(Location l) {
		double[] gps = new double[2];
		if (l != null) {
			gps[0] = l.getLatitude();
			gps[1] = l.getLongitude();
			return l.getLatitude() + ", " + l.getLongitude();
		} else {
			return " Location not available";
		}
	}
	
	public static float getDistanceToNearest (SharedPreferences sharedPrefs, LocationManager lm) {
		ArrayList<LocationResource> resourceList = generateResourceList(sharedPrefs);
		
		String provider = getMostAccurateProvider(lm);
		Location here = lm.getLastKnownLocation(provider);
		float distance = Float.MAX_VALUE;
		if (here != null) {
			for (LocationResource lr : resourceList){
				if (here.distanceTo(lr.getLocation()) <= distance) {
					distance = here.distanceTo(lr.getLocation());
				}
			}
		}
		
		return distance;
	}
	
	public static String getMostAccurateProvider(LocationManager lm) { 
		List<String> providers = lm.getProviders(true);
		String bestProvider = null;
		float bestAccuracy = Integer.MAX_VALUE;
		for (String p : providers) {
			Location here = lm.getLastKnownLocation(p);
			if (here != null) {
				if (here.getAccuracy() < bestAccuracy) {
					bestProvider = p;
					bestAccuracy = here.getAccuracy();
				}
			}
		}
		return bestProvider;
	}
}
