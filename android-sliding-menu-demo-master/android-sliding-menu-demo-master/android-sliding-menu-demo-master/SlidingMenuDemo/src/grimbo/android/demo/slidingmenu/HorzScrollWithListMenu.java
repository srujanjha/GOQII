/*
 * #%L
 * SlidingMenuDemo
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2012 Paul Grime
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package grimbo.android.demo.slidingmenu;

import grimbo.android.demo.slidingmenu.MyHorizontalScrollView.SizeCallback;
import grimbo.android.demo.slidingmenu.SimpleGestureFilter.SimpleGestureListener;

import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This demo uses a custom HorizontalScrollView that ignores touch events, and
 * therefore does NOT allow manual scrolling.
 * 
 * The only scrolling allowed is scrolling in code triggered by the menu button.
 * 
 * When the button is pressed, both the menu and the app will scroll. So the
 * menu isn't revealed from beneath the app, it adjoins the app and moves with
 * the app.
 */
public class HorzScrollWithListMenu extends Activity implements
		SimpleGestureListener {
	int flag = 0,p=0;
	MyHorizontalScrollView scrollView;
	View menu;
	View menu1;
	View app;
	ImageView i1, i2;
	TextView t1, t2;
	ImageView btnSlide;
	ImageView btnSlide1;
	View l;
	View t;
	View s;
	boolean menuOut = false;
	boolean menuOut1 = false;
	Handler handler = new Handler();
	Handler handler1 = new Handler();
	static int btnWidth;
	static int menuWidth = 0;
	ListView list;
	String[] web = { "HOME", "PROFILE", "GOAL AND TARGET", "MY HISTORY",
			"KARMA", "SOCIAL", "FEEDBACK" };
	String[] web1 = { "DEVICES", "GOQii Connected", "Send Data to Coach",
			"Sync With GOQii Band", "ALARM", "LOG", "LOG ACTIVITY", "LOG FOOD",
			"LOG SLEEP", "LOG WATER", "LOG WEIGHT" };
	ListView list1;

	Integer[] imageId = { R.drawable.home_w, R.drawable.profile_w,
			R.drawable.goal_w, R.drawable.history_w, R.drawable.karma_w,
			R.drawable.social_w, R.drawable.help_w

	};
	Integer[] imageId1 = { R.drawable.device_plug_w, R.drawable.sync_w,
			R.drawable.device_plug_w, R.drawable.device_plug_w,
			R.drawable.alarm_navbar_w, R.drawable.ic_launcher,
			R.drawable.log_activity_w, R.drawable.log_food_w,
			R.drawable.log_sleep_w, R.drawable.log_water_w,
			R.drawable.log_weight_w };
	private String syncStatus = "Last Synced @ 16:00";
	private SimpleGestureFilter detector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();

		LayoutInflater inflater = LayoutInflater.from(this);
		scrollView = (MyHorizontalScrollView) inflater.inflate(
				R.layout.horz_scroll_with_list_menu, null);
		setContentView(scrollView);

		detector = new SimpleGestureFilter(this, this);
		menu = inflater.inflate(R.layout.horz_scroll_menu, null);
		menu1 = inflater.inflate(R.layout.horz_scroll_menu1, null);
		app = inflater.inflate(R.layout.horz_scroll_app, null);
		ViewGroup tabBar = (ViewGroup) app.findViewById(R.id.tabBar);
		CustomList adapter = new CustomList(HorzScrollWithListMenu.this, web,
				imageId, false, 0, null);

		list = (ListView) menu.findViewById(R.id.list);
		list.setAdapter(adapter);
		// t = (View) list.getChildAt(list.getFirstVisiblePosition());
		System.out.println(t);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (flag == 0) {
					t = (View) findViewById(R.id.layout);
					s = list.getChildAt(0);
					i2 = (ImageView) findViewById(R.id.img);
					t2 = (TextView) findViewById(R.id.txt);
				}
				t.setBackgroundColor(Color.rgb(20, 21, 23));
				s.setBackgroundColor(Color.rgb(20, 21, 23));
				i2.setAlpha(0.3f);
				t2.setAlpha(0.3f);
				Toast.makeText(HorzScrollWithListMenu.this,
						"You Clicked at " + web[+position], Toast.LENGTH_SHORT)
						.show();
				view.setBackgroundColor(Color.BLACK);
				l = (View) view.findViewById(R.id.layout);
				i1 = (ImageView) view.findViewById(R.id.img);
				t1 = (TextView) view.findViewById(R.id.txt);
				l.setBackgroundColor(Color.rgb(247, 138, 40));
				i1.setAlpha(1.0f);
				t1.setAlpha(1.0f);
				t = l;
				i2 = i1;
				t2 = t1;
				flag = 1;
				s = view;
			}
		});
		CustomList adapter1 = new CustomList(HorzScrollWithListMenu.this, web1,
				imageId1, true, R.drawable.b8, syncStatus);
		list1 = (ListView) menu1.findViewById(R.id.list1);
		list1.setAdapter(adapter1);
		list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position != 5 ){
				if (flag == 0 ) {
					t = (View) findViewById(R.id.layout);
					s = list.getChildAt(0);
					i2 = (ImageView) findViewById(R.id.img);
					t2 = (TextView) findViewById(R.id.txt);
					p=0;
				}
				t.setBackgroundColor(Color.rgb(20, 21, 23));
				s.setBackgroundColor(Color.rgb(20, 21, 23));
				if (p!=1){
				i2.setAlpha(0.3f);
				t2.setAlpha(0.3f);}
				Toast.makeText(HorzScrollWithListMenu.this,
						"You Clicked at " + web1[+position], Toast.LENGTH_SHORT)
						.show();
				view.setBackgroundColor(Color.BLACK);
				l = (View) view.findViewById(R.id.layout);
				i1 = (ImageView) view.findViewById(R.id.img);
				t1 = (TextView) view.findViewById(R.id.txt);
				l.setBackgroundColor(Color.rgb(247, 138, 40));
				i1.setAlpha(1.0f);
				t1.setAlpha(1.0f);
				t=l;
				i2 = i1;
				t2 = t1;
				flag = 1;
				s = view;
				p=position;
			}
				else{
					view.setBackgroundColor(Color.rgb(20, 21, 23));
					}
				}
		});
		btnSlide = (ImageView) tabBar.findViewById(R.id.BtnSlide);
		// btnSlide.setOnClickListener(new ClickListenerForScrolling(scrollView,
		// menu));

		btnSlide1 = (ImageView) tabBar.findViewById(R.id.BtnSlide1);
		// btnSlide1.setOnClickListener(new
		// ClickListenerForScrolling1(scrollView,menu1));

		final View[] children = new View[] { menu, app, menu1 };

		// Scroll to app (view[1]) when layout finished.
		int scrollToViewIdx = 1;
		// menuWidth = 624;
		scrollView.initViews(children, scrollToViewIdx,
				new SizeCallbackForMenu(btnSlide1));
		// Detect touched area

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me) {
		// Call onTouchEvent of SimpleGestureFilter class
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	@Override
	public void onSwipe(int direction) {
		switch (direction) {
		case SimpleGestureFilter.SWIPE_RIGHT:
			if (menuOut1)
				onRight();
			else if (!menuOut)
				onLeft();
			break;
		case SimpleGestureFilter.SWIPE_LEFT:
			if (menuOut)
				onLeft();
			else if (!menuOut1)
				onRight();
			break;
		}
		// Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	public void onLeft() {
		Context context = menu.getContext();
		String msg = "Slide " + new Date();
		
		System.out.println(msg);
		menuWidth = menu.getMeasuredWidth();
		// Ensure menu is visible
		menu.setVisibility(View.VISIBLE);
		if (!menuOut) {
			// Scroll to 0 to reveal menu
			scrollView.smoothScrollBy(-menuWidth, 0);

		} else {
			// Scroll to menuWidth so menu isn't on screen.
			scrollView.smoothScrollBy(menuWidth, 0);
		}
		menuOut = !menuOut;
	}

	public void Left(View v) {
		onLeft();
	}

	public void onRight() {
		Context context = menu1.getContext();
		String msg = "Slide1 " + new Date();
		
		System.out.println(msg);
		menuWidth = menu.getMeasuredWidth();
		// Ensure menu is visible
		menu1.setVisibility(View.VISIBLE);

		if (!menuOut1) {
			// Scroll to 0 to reveal menu
			scrollView.smoothScrollBy(menuWidth, 0);
		} else {
			// Scroll to menuWidth so menu isn't on screen.
			scrollView.smoothScrollBy(-menuWidth, 0);
		}
		menuOut1 = !menuOut1;
	}

	public void Right(View v) {
		onRight();
	}

	/*
	 * 
	 * static class ClickListenerForScrolling implements OnClickListener {
	 * HorizontalScrollView scrollView; View menu;
	 * 
	 * boolean menuOut = false;
	 * 
	 * public ClickListenerForScrolling(HorizontalScrollView scrollView, View
	 * menu) { super(); this.scrollView = scrollView; this.menu = menu; }
	 * 
	 * @Override public void onClick(View v) { Context context =
	 * menu.getContext(); String msg = "Slide " + new Date();
	 * Toast.makeText(context, msg, 1000).show(); System.out.println(msg);
	 * 
	 * menuWidth = menu.getMeasuredWidth();
	 * 
	 * // Ensure menu is visible menu.setVisibility(View.VISIBLE); //
	 * LinearLayout window=(LinearLayout)v.findViewById(R.id.top); // int
	 * ww=window.getWidth(); // System.out.println(ww); if (!menuOut) { //
	 * Scroll to 0 to reveal menu int left = menuWidth;
	 * scrollView.smoothScrollBy(-menuWidth, 0);
	 * 
	 * } else { // Scroll to menuWidth so menu isn't on screen. int left =
	 * menuWidth; scrollView.smoothScrollBy(menuWidth, 0); } menuOut = !menuOut;
	 * }
	 * 
	 * }
	 * 
	 * static class ClickListenerForScrolling1 implements OnClickListener {
	 * HorizontalScrollView scrollView; View menu1; /** Menu must NOT be
	 * out/shown to start with.
	 * 
	 * boolean menuOut1 = false;
	 * 
	 * public ClickListenerForScrolling1(HorizontalScrollView scrollView, View
	 * menu1) { super(); this.scrollView = scrollView; this.menu1 = menu1; }
	 * 
	 * @Override public void onClick(View v) { Context context =
	 * menu1.getContext(); String msg = "Slide1 " + new Date();
	 * Toast.makeText(context, msg, 1000).show(); System.out.println(msg);
	 * 
	 * // Ensure menu is visible menu1.setVisibility(View.VISIBLE);
	 * 
	 * if (!menuOut1) { // Scroll to 0 to reveal menu int right = menuWidth;
	 * scrollView.smoothScrollBy(menuWidth, 0); } else { // Scroll to menuWidth
	 * so menu isn't on screen. int right = menuWidth;
	 * scrollView.smoothScrollBy(-menuWidth, 0); } menuOut1 = !menuOut1; } }
	 */
	/**
	 * Helper that remembers the width of the 'slide' button, so that the
	 * 'slide' button remains in view, even when the menu is showing.
	 */
	static class SizeCallbackForMenu implements SizeCallback {
		int btnWidth;
		View btnSlide;

		public SizeCallbackForMenu(View btnSlide) {
			super();

			this.btnSlide = btnSlide;
		}

		@Override
		public void onGlobalLayout() {
			btnWidth = btnSlide.getMeasuredWidth();
			System.out.println("btnWidth=" + btnWidth);
		}

		@Override
		public void getViewSize(int idx, int w, int h, int[] dims) {
			dims[0] = w;
			dims[1] = h;
			final int menuIdx = 0;
			if (idx == menuIdx) {
				dims[0] = w - btnWidth;
			}
		}
	}

	@Override
	public void onDoubleTap() {
		// TODO Auto-generated method stub

	}
}
