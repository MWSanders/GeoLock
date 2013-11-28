package edu.uccs.msander6;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends PreferenceActivity implements OnClickListener{

	private Button buttonSettings;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuto);
        if (!LockService.running) {
        	startService(new Intent(getApplicationContext(), LockService.class));
        }
        buttonSettings = (Button)findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(this);
        addPreferencesFromResource(R.xml.preferences);  
    }


	public void onClick(View arg0) {
		if(arg0.equals(buttonSettings)){
			Intent settings = new Intent(Settings.ACTION_SECURITY_SETTINGS);
			startActivity(settings);
		}
	}
		
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    	menu.add(Menu.NONE, 0, 0, "Show location information");
	    	return super.onCreateOptionsMenu(menu);
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    	switch (item.getItemId()) {
	    		case 0:
	    			startActivity(new Intent(this, ShowSettingsActivity.class));
	    			return true;
	    	}
	    	return false;
	    }

    
}
