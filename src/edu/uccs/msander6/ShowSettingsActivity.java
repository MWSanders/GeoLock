package edu.uccs.msander6;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class ShowSettingsActivity extends Activity {
	private LocationManager lm;
	private LocationListener mLocationListener = new LocationListener() {
        private Location mLocation;

		@Override
        public synchronized void onLocationChanged(Location l) {
                mLocation = l;
                //stopSelf();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
};
	private Timer mStopTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		setContentView(R.layout.show_settings_layout);

		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		ArrayList<LocationResource> resourceList = GeoUtils.generateResourceList(sharedPrefs);

		StringBuilder builder = new StringBuilder();

		builder.append("\nPerform Updates: " + sharedPrefs.getBoolean("perform_updates", false));
		builder.append("\nUpdate Interval: " + sharedPrefs.getString("updates_interval", "-1"));
		builder.append("\nWelcome Message: " + sharedPrefs.getString("welcome_message", "NULL"));
		
		String provider = GeoUtils.getMostAccurateProvider(lm);
		
		Location here = lm.getLastKnownLocation(provider);
		if (here != null) {
			builder.append("\nLocation Provider: " + provider + "\nCurrent Location: " + GeoUtils.loc2Str(here) +"\n");
			builder.append("Accuracy: " + here.getAccuracy() + "m\n");
			for (LocationResource lr : resourceList){
				buildMessage(lr, builder, here);
			}
		} else {
			builder.append("No active location provider found");
		}

		TextView settingsTextView = (TextView) findViewById(R.id.settings_text_view);
		settingsTextView.setText(builder.toString());

	}


	private void buildMessage(LocationResource lr, StringBuilder builder,
			Location here) {
		builder.append("\nDistance to " + lr.getName() + ": " + here.distanceTo(lr.getLocation()) + "m");
		boolean within = here.distanceTo(lr.getLocation()) <= lr.getRadius();
		builder.append("\n" + lr.getName() + " within " + lr.getRadius() + "m of current locaiton: " + within);
		builder.append("\n");
	}



	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
				mLocationListener);

		mStopTimer = new Timer();
		mStopTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				// stop after 1 minute, regardless of
				// whether we successfully got the location
				// or not
				//this.stopSelf();
				mStopTimer = null;
			}
		}, 1000 * 60);
	}
	 

}
