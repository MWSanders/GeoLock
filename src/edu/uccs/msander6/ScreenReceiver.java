package edu.uccs.msander6;



import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {

    public static boolean wasScreenOn = true;

    @Override
    public void onReceive(final Context context, final Intent intent) {
    	Log.e("test","onReceive: Intent: " + intent.getAction());

    	if (!LockService.running) {
	    	Intent i = new Intent(context, LockService.class);
	    	context.startService(i);
    	}
        
    	if(intent.getAction().equals(Intent.ACTION_USER_PRESENT) || intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Log.e("test","userpresent");
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            float distance = GeoUtils.getDistanceToNearest(sharedPrefs, lm);
            boolean inRange = GeoUtils.inRangeOfActiveLocation(true, sharedPrefs, lm);
            if (!inRange) {
            	if (sharedPrefs.getBoolean("error_dialog", true)) {
	            	Intent startIntent = new Intent(context, OutOfRangeDialog.class);
	                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                startIntent.putExtra("distance", distance);
	                context.startActivity(startIntent);
            	} else {
	            	DevicePolicyManager mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
			        mDPM.lockNow();
            	}
            } else {
            	if (sharedPrefs.getBoolean("error_dialog", true)) {
	            	Intent startIntent = new Intent(context, InRangeDialog.class);
	            	startIntent.putExtra("distance", distance);
	                startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                context.startActivity(startIntent);
            	}
            }
        }
    }
}