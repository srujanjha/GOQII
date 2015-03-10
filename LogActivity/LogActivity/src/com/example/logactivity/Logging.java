package com.example.logactivity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.logactivity.RangeSeekBar;
import com.example.logactivity.RangeSeekBar.OnRangeSeekBarChangeListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class Logging extends Activity implements OnTouchListener {
	protected LogDbHelper db;
	List<LogTask> list;
	int IntenseClick = 0;
	public static long smin = 0, smax = 0;
	long offset = 10 * 60 * 1000;
	RangeSeekBar<Long> seekBar;
	private Handler mHandler;
	private boolean mDown;
	public static String txtActivity="",intensity="",calories="";
	View view;
	private final Runnable mRunnable = new Runnable() {
		public void run() {
			if (mDown) {
				setMinMax(view);
				mHandler.postDelayed(this, 100);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_logging);
		
		WebView wView1 = (WebView)findViewById(R.id.Act1); 
		wView1.loadUrl("file:///android_asset/Running.gif"); 
		WebView wView2 = (WebView)findViewById(R.id.Act2); 
		wView2.loadUrl("file:///android_asset/Walking.gif"); 
		WebView wView3 = (WebView)findViewById(R.id.Act3); 
		wView3.loadUrl("file:///android_asset/Dance.gif"); 
		WebView wView4 = (WebView)findViewById(R.id.Act4); 
		wView4.loadUrl("file:///android_asset/Yoga.gif"); 
		WebView wView5 = (WebView)findViewById(R.id.Act5); 
		wView5.loadUrl("file:///android_asset/Cycling.gif"); 
		WebView wView6 = (WebView)findViewById(R.id.Act6); 
		wView6.loadUrl("file:///android_asset/Swimming.gif"); 
		WebView wView7 = (WebView)findViewById(R.id.Act7); 
		wView7.loadUrl("file:///android_asset/Skipping.gif"); 
		WebView wView8 = (WebView)findViewById(R.id.Act8); 
		wView8.loadUrl("file:///android_asset/Yoga.gif"); 
		wView1.setOnTouchListener(this);
		wView2.setOnTouchListener(this);
		wView3.setOnTouchListener(this);
		wView4.setOnTouchListener(this);
		wView5.setOnTouchListener(this);
		wView6.setOnTouchListener(this);
		wView7.setOnTouchListener(this);
		wView8.setOnTouchListener(this);
		mHandler = new Handler();
		Calendar rightnow = Calendar.getInstance();
		smin = rightnow.getTimeInMillis();
		smax = rightnow.getTimeInMillis() + 1000 * 60 * 60;
		rightnow.set(rightnow.get(Calendar.YEAR), rightnow.get(Calendar.MONTH),
				rightnow.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		Date maxDate = new Date();
		smax = maxDate.getTime() + 1000 * 1 * 60 * 60;
		seekBar = new RangeSeekBar<Long>(rightnow.getTimeInMillis() / offset,
				(rightnow.getTimeInMillis() + 24 * 60 * 60 * 1000) / offset,
				getBaseContext());
		seekBar.setSelectedMinValue(smin / offset);
		seekBar.setSelectedMaxValue(smax / offset);
		TextView ax = (TextView) (findViewById(R.id.Max));
		TextView in = (TextView) (findViewById(R.id.Min));
		ax.setText("End Time:"
				+ change24to12(new Date(smax).toString().substring(11, 16)));
		in.setText("Start Time:"
				+ change24to12(new Date(smin).toString().substring(11, 16)));
		seekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Long>() {
			@Override
			public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
					Long minValue, Long maxValue) {
				try {
					TextView ax = (TextView) (findViewById(R.id.Max));
					TextView in = (TextView) (findViewById(R.id.Min));
					smin = minValue * offset;
					smax = maxValue * offset;
					ax.setText("End Time:"
							+ change24to12(new Date(smax).toString().substring(
									11, 16)));
					in.setText("Start Time:"
							+ change24to12(new Date(smin).toString().substring(
									11, 16)));
				} catch (Exception e) {
				}
			}
		});
		ViewGroup layout = (ViewGroup) findViewById(R.id.seek);
		layout.addView(seekBar);
		Spinner spinner = (Spinner) findViewById(R.id.intensity_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.intensity_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		MyButton bt1 = (MyButton) findViewById(R.id.LMin);
		MyButton bt2 = (MyButton) findViewById(R.id.RMin);
		MyButton bt3 = (MyButton) findViewById(R.id.LMax);
		MyButton bt4 = (MyButton) findViewById(R.id.RMax);
		bt1.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				mDown = true;
				mHandler.post(mRunnable);
				view = v;
				return true;
			}
		});
		bt1.setSampleLongpress(this);
		bt2.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				mDown = true;
				mHandler.post(mRunnable);
				view = v;
				return true;
			}
		});
		bt2.setSampleLongpress(this);
		bt3.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				mDown = true;
				mHandler.post(mRunnable);
				view = v;
				return true;
			}
		});
		bt3.setSampleLongpress(this);
		bt4.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				mDown = true;
				mHandler.post(mRunnable);
				view = v;
				return true;
			}
		});
		bt4.setSampleLongpress(this);
	}

	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logging, menu);

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
	private void addToDatabase()
	{
		EditText txtAct = (EditText) (findViewById(R.id.txtActivity));
		EditText txtCal = (EditText) (findViewById(R.id.caloriesText));
		Spinner spinner = (Spinner) findViewById(R.id.intensity_spinner);
		LogTask task = new LogTask(txtAct.getText().toString(), smin, smax,
				spinner.getSelectedItem().toString(), txtCal.getText()
						.toString());
		db = new LogDbHelper(this);
		db.addTask(task);
	}
	public void addTaskNow(View v) {
		RadioButton rg=(RadioButton)findViewById(R.id.rg1);
		if(rg.isChecked()){
			addToDatabase();
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
		EditText txtAct = (EditText) (findViewById(R.id.txtActivity));
		EditText txtCal = (EditText) (findViewById(R.id.caloriesText));
		Spinner spinner = (Spinner) findViewById(R.id.intensity_spinner);
		txtActivity=txtAct.getText().toString();
		intensity=spinner.getSelectedItem().toString();
		calories=txtCal.getText().toString();
		rg=(RadioButton)findViewById(R.id.rg2);
		if(rg.isChecked()){
			Intent intent = new Intent(this, ToDoActivity.class);
	    	intent.addFlags(-1);
	    	startActivity(intent);
		}
		rg=(RadioButton)findViewById(R.id.rg3);
		if(rg.isChecked()){
			addToDatabase();
			Intent intent = new Intent(this, ToDoActivity.class);
	    	intent.addFlags(-1);
	    	startActivity(intent);
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

	public void setActivity(View v) {
		System.out.println("Yes");
		String str = v.getTag().toString();
		EditText txt = (EditText) findViewById(R.id.txtActivity);
		txt.setText(str);
		int vd = v.getId();
		WebView v1;
		for (int i = R.id.Act1; i <= R.id.Act8; i++) {
			if (i == vd) {
				v1 = (WebView) findViewById(vd);
				v1.setAlpha(1.0f);
			} else {
				v1 = (WebView) findViewById(i);
				v1.setAlpha(0.5f);
			}
		}
		if (str.equals("Other")) {
			txt.setEnabled(true);
			txt.setFreezesText(false);
		} else {
			txt.setFreezesText(true);
			txt.setEnabled(false);
			txt.setTextColor(Color.BLACK);
		}
	}

	public void setMinMax(View v) {
		//System.out.println(seekBar.getAbsoluteMinValue()+" "+(smax/offset)+" "+(smin/offset)+" "+seekBar.getAbsoluteMaxValue());
			switch (Integer.parseInt(v.getTag().toString())) {
			case 0: {
				if((smax - smin >= 0) && ((smin/offset)>seekBar.getAbsoluteMinValue())){
				smin -= (offset);
				seekBar.setSelectedMinValue(smin / offset);}
				break;
			}
			case 1: {
				if((smax - smin > 0)){
				smin += offset;
				seekBar.setSelectedMinValue(smin / offset);}
				break;
			}
			case 2: {
				if((smax - smin > 0)){
					smax -= offset;
				seekBar.setSelectedMaxValue(smax / offset);}
				break;
			}
			case 3: {
				if((smax - smin >= 0) && ((smax/offset)<seekBar.getAbsoluteMaxValue())){
					smax += offset;
				seekBar.setSelectedMaxValue(smax / offset);}
				break;
			}			
		}
		TextView ax = (TextView) (findViewById(R.id.Max));
		TextView in = (TextView) (findViewById(R.id.Min));
		ax.setText("End Time:"
				+ change24to12(new Date(smax).toString().substring(11, 16)));
		in.setText("Start Time:"
				+ change24to12(new Date(smin).toString().substring(11, 16)));

	}

	public void cancelLongPress() {
		mDown = false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		EditText txt = (EditText) findViewById(R.id.txtActivity);
		txt.setText(v.getTag().toString());
		if (v.getTag().toString().equals("Other")) {
			txt.setEnabled(true);
			txt.setFreezesText(false);
		} else {
			txt.setFreezesText(true);
			txt.setEnabled(false);
			txt.setTextColor(Color.BLACK);
		}
		return false;
	}

}
