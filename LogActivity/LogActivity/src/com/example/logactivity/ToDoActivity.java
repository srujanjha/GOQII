package com.example.logactivity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class ToDoActivity extends Activity implements OnValueChangeListener,OnClickListener {
	TextView et;CheckBox c1,c2;
	boolean txtStyle[]=new boolean[7];//0=Sunday 6=Saturday false=selected
	int taskno=0;
    public static String alarmText="";
    int Day[]={R.id.Sun,R.id.Mon,R.id.Tue,R.id.Wed,R.id.Thu,R.id.Fri,R.id.Sat};
    protected TaskerDbHelper db;
	List<Task> list;
	Task todo;
	Dialog d;
	NumberPicker np;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_to_do);
		Intent intent = getIntent();
		taskno = intent.getFlags();
		if(taskno!=-1){//Adding already set values from database
			Button bt=(Button)findViewById(R.id.add);
			bt.setText("Update");
			db = new TaskerDbHelper(this);
	        list = db.getAllTasks();
			alarmText=list.get(taskno).getTaskName();
			et = (TextView) findViewById(R.id.Freq);
			et.setText(list.get(taskno).getFrequency()+"");
			int days=list.get(taskno).getDays();
			for(int k=0;k<7;k++)
			{if((days>>(7-k)&1)==0)changeDay(k,Day[k]);}
			int notify=list.get(taskno).getNotify();
			CheckBox cb1=(CheckBox)findViewById(R.id.remind);
			CheckBox cb2=(CheckBox)findViewById(R.id.log);
			if(notify==1)cb1.setChecked(true);
			if(notify==2)cb2.setChecked(true);
			if(notify==3){cb1.setChecked(true);cb2.setChecked(true);}
		}
	}
	public void addTaskNow(View v) {

		et = (TextView) findViewById(R.id.Freq);
		int frequency = Integer.parseInt(et.getText().toString());
		int days = 0;
		for (int i = 0; i < 7; i++)
			if (!txtStyle[i])
				days += Math.pow(2, 7 - i);
		c1 = (CheckBox) findViewById(R.id.remind);
		c2 = (CheckBox) findViewById(R.id.log);
		int notify = 0;
		if (!(c1.isChecked())&& !(c2.isChecked())) {
			notify = 0;
		} else if (c1.isChecked() && !(c2.isChecked())) {
			notify = 1;
		} else if (!(c1.isChecked()) && c2.isChecked()) {
			notify = 2;
		} else {
			notify = 3;
		}
		db = new TaskerDbHelper(this);
		list = db.getAllTasks();
		if(taskno==-1){
			todo = new Task(Logging.txtActivity, Logging.smin, Logging.smax,
				Logging.intensity, Logging.calories, days, frequency, notify);
			db.addTask(todo);
			list.clear();
			list = db.getAllTasks();
			taskno=list.get(list.size()-1).getId();
			alarmText=list.get(list.size()-1).getTaskName();
		setAlarm(frequency, Logging.smin, Logging.smax, c1.isChecked(),c2.isChecked());}
		else {
			todo=new Task(list.get(taskno).getTaskName(),list.get(taskno).getStartTime(),list.get(taskno).getEndTime(),
				list.get(taskno).getIntensity(),list.get(taskno).getCalories(),days,frequency,notify);
			todo.setId(taskno+1);
		db.updateTask(todo);
		
		setAlarm(frequency, list.get(taskno).getStartTime(), list.get(taskno).getEndTime(), c1.isChecked(),c2.isChecked());}
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}

	private void changeDay(int i,int id)
	{
		TextView txt=(TextView)findViewById(id);
		if(txtStyle[i])
			{txt.setTextAppearance(getApplicationContext(), R.style.boldText);txtStyle[i]=false;}
		else
			{txt.setTextAppearance(getApplicationContext(), R.style.normalText);txtStyle[i]=true;}
	}
	public void setDay(View v){changeDay(Integer.parseInt(v.getTag().toString()),v.getId());}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do, menu);
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
	private void setAlarm(int freq, long sT, long eT, boolean remind,boolean logthis)// sT=starTime, eT=endtime
	{
		long time[] = new long[freq];
		long diff = (16 * 60 * 60 * 1000) / freq;
		long sT_eT=eT-sT;
		Calendar rightnow = Calendar.getInstance();
		rightnow.set(rightnow.get(Calendar.YEAR), rightnow.get(Calendar.MONTH),
				rightnow.get(Calendar.DAY_OF_MONTH), 6, 0, 0);
		Calendar rightnow2 = Calendar.getInstance();
		rightnow2.set(rightnow2.get(Calendar.YEAR),
				rightnow2.get(Calendar.MONTH),
				rightnow2.get(Calendar.DAY_OF_MONTH), 22, 0, 0);
		if (sT < rightnow.getTimeInMillis()) {
			sT = rightnow.getTimeInMillis();
			rightnow.set(rightnow.get(Calendar.YEAR),
					rightnow.get(Calendar.MONTH),
					rightnow.get(Calendar.DAY_OF_MONTH), 22, 0, 0);
		} else if (sT > rightnow2.getTimeInMillis()) {
			sT = rightnow.getTimeInMillis() + 24 * 60 * 60 * 1000;
			rightnow.set(rightnow.get(Calendar.YEAR),
					rightnow.get(Calendar.MONTH),
					rightnow.get(Calendar.DAY_OF_MONTH) + 1, 22, 0, 0);
		} else {
			rightnow.set(rightnow.get(Calendar.YEAR),
					rightnow.get(Calendar.MONTH),
					rightnow.get(Calendar.DAY_OF_MONTH), 22, 0, 0);
		}
		for (int i = 0; i < freq; i++) {
			if (sT > rightnow.getTimeInMillis()) {
				sT = sT - rightnow.getTimeInMillis();
				rightnow.set(rightnow.get(Calendar.YEAR),
						rightnow.get(Calendar.MONTH),
						rightnow.get(Calendar.DAY_OF_MONTH) + 1, 6, 0, 0);
				sT += rightnow.getTimeInMillis();
				rightnow.set(rightnow.get(Calendar.YEAR),
						rightnow.get(Calendar.MONTH),
						rightnow.get(Calendar.DAY_OF_MONTH) + 1, 22, 0, 0);
			}
			time[i] = sT;
			sT += diff;
		}
		if (remind) {
			for (int i = 0; i < freq; i++) {
				
				Intent intentAlarm = new Intent(this, AlarmReciever.class);
				intentAlarm.putExtra("AlarmText", "Hey! Its time to "+alarmText);
				intentAlarm.putExtra("Number", taskno*24+i);
				AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time[i],24 * 60 * 60 * 1000, PendingIntent.getBroadcast(this,
								taskno*24+i, intentAlarm,
								PendingIntent.FLAG_ONE_SHOT));
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
			}
		}
		if (logthis) {
			for (int i = 0; i < freq; i++) {
				
				Intent intentAlarm = new Intent(this, AlarmReciever.class);
				intentAlarm.putExtra("AlarmText", "Do you want to log this?\n"+alarmText);
				intentAlarm.putExtra("Number", taskno*24+i+12);
				AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time[i]+sT_eT,24 * 60 * 60 * 1000, PendingIntent.getBroadcast(this,
								taskno*24+i+12, intentAlarm,
								PendingIntent.FLAG_ONE_SHOT));
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
			}
		}

	}
	public void freqDialog(View v) {
		d = new Dialog(this);
		d.setTitle("Frequency");
		d.setContentView(R.layout.frequency);
		Button b1 = (Button) d.findViewById(R.id.button1);
		Button b2 = (Button) d.findViewById(R.id.button2);
		np = (NumberPicker) d.findViewById(R.id.numberPicker1);
		np.setMaxValue(12);
		np.setMinValue(1);
		et = (TextView) findViewById(R.id.Freq);
		np.setValue(Integer.parseInt(et.getText().toString()));
		np.setWrapSelectorWheel(false);
		np.setOnValueChangedListener(this);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);

		d.show();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			et = (TextView) findViewById(R.id.Freq);
			et.setText(String.valueOf(np.getValue()));
			d.dismiss();
			break;
		case R.id.button2:
			d.dismiss();
			break;
		}
	}
	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		// TODO Auto-generated method stub
		
	}

	
}
