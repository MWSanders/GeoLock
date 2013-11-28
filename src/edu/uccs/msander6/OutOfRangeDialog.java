package edu.uccs.msander6;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class OutOfRangeDialog extends Activity {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Bundle bundle = getIntent().getExtras();
	        float distance = bundle.getFloat("distance");
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setMessage("You are " + distance + "m from the neareast active area.")
	               .setTitle("Error")
	               .setCancelable(false)
	               .setPositiveButton("OK", new DialogInterface.OnClickListener()
	               {
	                   public void onClick(DialogInterface dialog, int id) 
	                   {
	                	   DevicePolicyManager mDPM = (DevicePolicyManager) getBaseContext().getSystemService(Context.DEVICE_POLICY_SERVICE);
	       		        	mDPM.lockNow();
	                	   OutOfRangeDialog.this.finish();
	                   }
	               });
	  
	        AlertDialog alert = builder.create();
	        setContentView(R.layout.activity_main);
	        alert.show();
	    }
}
