package com.example.logactivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	protected LogDbHelper db;
	protected TaskerDbHelper db1;
	List<LogTask> list;
	List<Task> list1;
	MyAdapter adapt;
	MyAdapter2 adapt2;
	int taskno = 0;
	int typeView = 1;
	Set<Integer> ids = new HashSet<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		Calendar rightnow1 = Calendar.getInstance();
		rightnow1.set(rightnow1.get(Calendar.YEAR),
				rightnow1.get(Calendar.MONTH),
				rightnow1.get(Calendar.DAY_OF_MONTH), 16, 0, 0);

		
		TabHost host = (TabHost) findViewById(R.id.tabhost);
		host.setup();

		TabSpec spec = host.newTabSpec("Tab One");
		spec.setContent(R.id.tab1);
		spec.setIndicator("Logging");
		host.addTab(spec);

		spec = host.newTabSpec("Tab Two");
		spec.setContent(R.id.tab2);
		spec.setIndicator("ToDo");
		host.addTab(spec);

		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(AlarmReciever.i);
		typeView = 1;
		Calendar rightnow = Calendar.getInstance();
		int k = rightnow.get(Calendar.DAY_OF_WEEK);
		
		db = new LogDbHelper(this);
		list = db.getAllTasks();
		adapt = new MyAdapter(this, R.layout.list_inner_view, list);
		ListView listTask = (ListView) findViewById(R.id.listView1);
		listTask.setAdapter(adapt);

		db1 = new TaskerDbHelper(this);
		list1 = db1.getAllTasks();
		adapt2 = new MyAdapter2(this, R.layout.list_inner_view1, list1);
		ListView listTask2 = (ListView) findViewById(R.id.listView2);
		listTask2.setAdapter(adapt2);
		listTask2.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent(getBaseContext(), ToDoActivity.class);
				intent.addFlags(position);
				startActivity(intent);
			}
		});
			
	}

	public void addTaskNow(View v) {
		Intent intent = new Intent(this, Logging.class);
		intent.addFlags(-1);
		startActivity(intent);
	}

	public void cancelTask(int id) {
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(this, AlarmReciever.class);
		PendingIntent sender = PendingIntent.getBroadcast(this, id, intent, 0);
		am.cancel(sender);

	}

	public void deleteTaskNow(View v) {
		if (typeView == 1) {
			db1 = new TaskerDbHelper(this);
			list1 = db1.getAllTasks();
			taskno = list1.size();
			adapt2 = new MyAdapter2(this, R.layout.list_inner_view2, list1);
			ListView listTask = (ListView) findViewById(R.id.listView2);
			listTask.setAdapter(adapt2);
			typeView = 2;
		} else {
			Iterator<Integer> i = ids.iterator();

			while (i.hasNext()) {
				typeView = i.next(); // typeView was temporarily not used
				Task task = new Task();
				task.setId(typeView);
				db1 = new TaskerDbHelper(this);
				db1.deleteTask(task);
				for(int j=0;j<24;j++)cancelTask(typeView*24+j);
			}
			typeView = 1;
			db1 = new TaskerDbHelper(this);
			list1 = db1.getAllTasks();
			taskno = list.size();
			adapt2 = new MyAdapter2(this, R.layout.list_inner_view, list1);
			ListView listTask = (ListView) findViewById(R.id.listView2);
			listTask.setAdapter(adapt2);
			listTask.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
		
					Intent intent = new Intent(getBaseContext(), ToDoActivity.class);
					intent.addFlags(position);
					startActivity(intent);
				}
			});
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class MyAdapter extends ArrayAdapter<LogTask> {
		Context context;
		List<LogTask> taskList = new ArrayList<LogTask>();
		int layoutResourceId;

		public MyAdapter(Context context, int layoutResourceId,
				List<LogTask> objects) {
			super(context, layoutResourceId, objects);
			this.layoutResourceId = layoutResourceId;
			this.taskList = objects;
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView chk = null;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.list_inner_view,
						parent, false);
				chk = (TextView) convertView.findViewById(R.id.textView1);
				convertView.setTag(chk);

			} else {
				chk = (TextView) convertView.getTag();
			}
			LogTask current = taskList.get(position);
			chk.setText(current.getId()
					+ " "
					+ current.getTaskName()
					+ " "
					+ change24to12(new Date(current.getStartTime()).toString()
							.substring(11, 16))
					+ " "
					+ change24to12(new Date(current.getEndTime()).toString()
							.substring(11, 16)) + " " + current.getIntensity()
					+ " " + current.getCalories());
			chk.setTag(current);
			return convertView;
		}
	}

	private class MyAdapter2 extends ArrayAdapter<Task> {
		Context context;
		List<Task> taskList = new ArrayList<Task>();
		int layoutResourceId;

		public MyAdapter2(Context context, int layoutResourceId,
				List<Task> objects) {
			super(context, layoutResourceId, objects);
			this.layoutResourceId = layoutResourceId;
			this.taskList = objects;
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView chk = null;
			CheckBox chk1 = null;
			if (convertView == null) {
				if (typeView == 1) {
					LayoutInflater inflater = (LayoutInflater) context
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = inflater.inflate(R.layout.list_inner_view1,
							parent, false);
					chk = (TextView) convertView.findViewById(R.id.textView1);
					convertView.setTag(chk);
				} else {
					LayoutInflater inflater = (LayoutInflater) context
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = inflater.inflate(R.layout.list_inner_view2,
							parent, false);
					chk1 = (CheckBox) convertView.findViewById(R.id.textView2);
					convertView.setTag(chk1);
					chk1.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							CheckBox cb = (CheckBox) v;
							Task changeTask = (Task) cb.getTag();
							if (cb.isChecked())
								ids.add(changeTask.getId());
							else
								ids.remove(changeTask.getId());
						}
					});
				}
			} else {
				if (typeView == 1)
					chk = (TextView) convertView.getTag();
				else
					chk1 = (CheckBox) convertView.getTag();
			}
			Task current = taskList.get(position);
			if (typeView == 1) {
				chk.setText(current.getId()
						+ " "
						+ current.getTaskName()
						+ " "
						+ change24to12(new Date(current.getStartTime())
								.toString().substring(11, 16))
						+ " "
						+ change24to12(new Date(current.getEndTime())
								.toString().substring(11, 16)) + " "
						+ current.getIntensity() + " " + current.getCalories()+ " " + current.getFrequency()+ " " + current.getNotify());
				chk.setTag(current);
			} else {
				chk1.setText(current.getId()
						+ " "
						+ current.getTaskName()
						+ " "
						+ change24to12(new Date(current.getStartTime())
								.toString().substring(11, 16))
						+ " "
						+ change24to12(new Date(current.getEndTime())
								.toString().substring(11, 16)) + " "
						+ current.getIntensity() + " " + current.getCalories()+ " " + current.getFrequency()+ " " + current.getNotify());
				chk1.setTag(current);
			}
			return convertView;
		}
	}

	private String change24to12(String s) {
		int t = Integer.parseInt(s.substring(0, 2));
		if (t == 0)
			return "12" + s.substring(2) + " AM";
		else if (t < 12)
			return s + " AM";
		else if (t == 12)
			return "12" + s.substring(2) + " PM";
		else
			return (t - 12) + s.substring(2) + " PM";

	}

	
}
