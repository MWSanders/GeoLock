package edu.uccs.msander6;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class InRangeDialog extends Activity {
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        Bundle bundle = getIntent().getExtras();
	        float distance = bundle.getFloat("distance");
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setMessage("You are " + distance + "m from the neareast active area.")
	               .setTitle("Geo Unlock")
	               .setCancelable(false)
	               .setPositiveButton("OK", new DialogInterface.OnClickListener()
	               {
	                   public void onClick(DialogInterface dialog, int id) 
	                   {
	       		        	InRangeDialog.this.finish();
	                   }
	               });
	  
	  
	        AlertDialog alert = builder.create();
	        setContentView(R.layout.activity_main);
	        alert.show();
	    }
}
