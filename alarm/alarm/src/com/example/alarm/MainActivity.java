package com.example.alarm;

import java.util.GregorianCalendar;

import com.example.alarm.AlarmReciever;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	public void scheduleAlarm(View V)
    {
            // time at which alarm will be scheduled here alarm is scheduled at 1 day from current time, 
            // we fetch  the current time in milliseconds and added 1 day time
            // i.e. 24*60*60*1000= 86,400,000   milliseconds in a day        
            Long time = new GregorianCalendar().getTimeInMillis()+2*1000;

            // create an Intent and set the class which will execute when Alarm triggers, here we have
            // given AlarmReciever in the Intent, the onRecieve() method of this class will execute when
            // alarm triggers and 
            //we will write the code to send SMS inside onRecieve() method pf Alarmreciever class
            Intent intentAlarm = new Intent(this, AlarmReciever.class);
       
            // create the object
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

            //set the alarm for particular time
            intentAlarm.setFlags(1);intentAlarm.putExtra("AlarmText","Running");
            alarmManager.set(AlarmManager.RTC_WAKEUP,time+2000, PendingIntent.getBroadcast(this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
            intentAlarm.setFlags(2);intentAlarm.putExtra("AlarmText","Walking");
            alarmManager.set(AlarmManager.RTC_WAKEUP,time+4000, PendingIntent.getBroadcast(this,2,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
            intentAlarm.setFlags(3);intentAlarm.putExtra("AlarmText","Dancing");
            alarmManager.set(AlarmManager.RTC_WAKEUP,time+6000, PendingIntent.getBroadcast(this,3,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
            intentAlarm.setFlags(4);intentAlarm.putExtra("AlarmText","Stretching");
            alarmManager.set(AlarmManager.RTC_WAKEUP,time+8000, PendingIntent.getBroadcast(this,4,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
            //Toast.makeText(this, "Alarm Scheduled for Tommrrow", Toast.LENGTH_LONG).show();
           System.out.println("Hello");
            
         
    }
}
