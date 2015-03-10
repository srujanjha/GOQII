package com.example.charts;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
public class MainActivity extends Activity {
	WebView browser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
        browser = (WebView) findViewById(R.id.webView1);
        browser.setWebViewClient(new WebViewClient());
        browser.getSettings().setJavaScriptEnabled(true);
        browser.clearCache(true);
        browser.setWebChromeClient(new WebChromeClient());
        browser.loadUrl("file:///android_asset/index.htm");
	}
	
	public void done(View v)
	{
		String type="line";
		boolean thd=false;
		RadioButton rg1=(RadioButton)findViewById(R.id.r1);
		RadioButton rg2=(RadioButton)findViewById(R.id.r2);
		RadioButton rg3=(RadioButton)findViewById(R.id.r3);
		RadioButton rg4=(RadioButton)findViewById(R.id.r4);
		if(rg1.isChecked())type="line";
		if(rg2.isChecked())type="area";
		if(rg3.isChecked())type="column";
		if(rg4.isChecked())type="pie";
		LinearLayout lay=(LinearLayout)findViewById(R.id.lay);
		EditText txt=(EditText)findViewById(R.id.txt);
		ToggleButton tb=(ToggleButton)findViewById(R.id.toggleButton1);
		thd=tb.isChecked();
		//browser.getSettings().setLoadWithOverviewMode(true);
		browser.loadUrl("javascript:draw('{\"arg\":[" +txt.getText().toString() + "],\"width\":"+lay.getWidth()/2+",\"chart\":\""+type+"\",\"thd\":"+thd+"}');");
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
}