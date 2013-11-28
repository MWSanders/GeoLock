package edu.uccs.msander6;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;



public class AdminReceiver extends DeviceAdminReceiver {

	public void onEnabled(Context context, Intent intent) {
	    showToast(context, "Lock Screen Admin: enabled");
	    }
	
	    @Override
	    public CharSequence onDisableRequested(Context context, Intent intent) {
	    return "You need to have this marked so GeoLock can work.";
	    }
	
	    @Override
	    public void onDisabled(Context context, Intent intent) {
	    showToast(context, "GeoLock Admin: disabled");
	    }
	    void showToast(Context context, CharSequence msg) {
	        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	
	    }

}