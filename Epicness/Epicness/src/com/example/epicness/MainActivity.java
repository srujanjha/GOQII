package com.example.epicness;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {
	int i=0,j=0,k=0,z=0;
	int st1[]=new int[10];
	int Ids=R.id.imageView1;
	int Ids1=R.id.shape1;
	int Ids2=R.id.shapex1;
	
	//int Ids[]={R.id.imageView1,R.id.imageView2,R.id.imageView3,R.id.imageView4,R.id.imageView5,R.id.imageView6,R.id.imageView7,R.id.imageView8,R.id.imageView9,R.id.imageView10};//,R.id.imageView11,R.id.imageView12,R.id.imageView13,R.id.imageView14,R.id.imageView15,R.id.imageView16,R.id.imageView17,R.id.imageView18,R.id.imageView19,R.id.imageView20};
	//int Ids1[]={R.id.shape1,R.id.shape2,R.id.shape3,R.id.shape4,R.id.shape5,R.id.shape6,R.id.shape7,R.id.shape8,R.id.shape9,R.id.shape10};//,R.id.shape11,R.id.shape12,R.id.shape13,R.id.shape14,R.id.shape15,R.id.shape16,R.id.shape17,R.id.shape18,R.id.shape19,R.id.shape20};
	//int Ids2[]={R.id.shapex1,R.id.shapex2,R.id.shapex3,R.id.shapex4,R.id.shapex5,R.id.shapex6,R.id.shapex7,R.id.shapex8,R.id.shapex9,R.id.shapex10};//,R.id.shape11,R.id.shape12,R.id.shape13,R.id.shape14,R.id.shape15,R.id.shape16,R.id.shape17,R.id.shape18,R.id.shape19,R.id.shape20};
	ImageView img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	public void Animate(View v)
	{
		final View v1=v;
		Handler refresh = new Handler(Looper.myLooper());
		refresh.post(new Runnable() {
	    public void run()
	    {
	        timerStart(v1);
	    }
		});
	}
	public void timerStart(View v)//Raining
	{
		try{
			RelativeLayout window=(RelativeLayout)findViewById(R.id.mainWindow);
			int ww=window.getWidth();
		TextView txt=(TextView)findViewById(R.id.textView2);
			ImageView img1[]=new ImageView[10];
			ImageView img2[]=new ImageView[10];
			AnimatorSet set[]=new AnimatorSet[10];
			AnimatorSet setText=new AnimatorSet();
			ObjectAnimator animt[]=new ObjectAnimator[2];
			
			AnimatorSet set1[]=new AnimatorSet[10];
			AnimatorSet setp[]=new AnimatorSet[10];
			AnimatorSet setf[]=new AnimatorSet[10];
			AnimatorSet set1f[]=new AnimatorSet[10];
		Random r = new Random();
		ObjectAnimator anim[]=new ObjectAnimator[2];
		ObjectAnimator anim1[]=new ObjectAnimator[2];
		//ObjectAnimator animp[][]=new ObjectAnimator[10][200];
		ObjectAnimator animp[]=new ObjectAnimator[2];
		ObjectAnimator animf[]=new ObjectAnimator[2];
		ObjectAnimator anim1f[]=new ObjectAnimator[2];
		for( k=0;k<5;k++)
		{
			st1[k]=-ww;
			int min = -(ww*7)/8;
			int max = -(ww*3)/4;
			int width=0;
			set[k]=new AnimatorSet();
			set1[k]=new AnimatorSet();
			setp[k]=new AnimatorSet();
			setf[k]=new AnimatorSet();
			set1f[k]=new AnimatorSet();
			img1[k]=(ImageView)findViewById(Ids1);//[k]);
			img2[k]=(ImageView)findViewById(Ids2);//[k]);
			img=(ImageView)findViewById(Ids);//[k]);
           // lp1[k]=(LayoutParams)img.getLayoutParams();
			for(j=0;j<ww;j++)
			{
				int r1=r.nextInt(max - min + 1) + min,r2=r.nextInt(max - min + 1) + min;
				anim[j%2]=ObjectAnimator.ofFloat(img1[k], "translationX",r1,st1[k]-ww/10);
				anim1[j%2]=ObjectAnimator.ofFloat(img2[k], "translationX",r2,st1[k]-ww/10);
				anim[j%2].setDuration(((r1-st1[k]+10)*ww)/180);
				anim1[j%2].setDuration(((r2-st1[k]+10)*ww)/180);
				anim[j%2].setStartDelay(100);
				anim1[j%2].setStartDelay(100);
				
				animf[j%2]=ObjectAnimator.ofFloat(img1[k], View.ALPHA,0,1);
				animf[j%2].setDuration(((r1-st1[k]-min+10)*ww)/240);
				anim1f[j%2]=ObjectAnimator.ofFloat(img2[k], View.ALPHA,0,1);
				anim1f[j%2].setDuration(((r2-st1[k]-min+10)*ww)/240);
				
				width++;
				if(j==0)animp[j%2]=ObjectAnimator.ofFloat(img,View.SCALE_X,0,1);
				else animp[j%2]=ObjectAnimator.ofFloat(img,View.SCALE_X,width-1,width);
				animp[j%2].setDuration(((r1-st1[k]+10)*ww)/180);
				animp[j%2].setStartDelay(100);
				
				animt[j%2]=ObjectAnimator.ofObject(new AnimatedTextView((TextView) findViewById(R.id.textView2)),"Text",new TypeEvaluator<String>() {
			        @Override
			        public String evaluate(float fraction, String startValue, String endValue) {
			            return (fraction < 0.5)? startValue:endValue;
			        }
			    },((float)(((j-1)*100)/ww))+"",((float)((j*100)/ww))+"");
				animt[j%2].setDuration(((r1-st1[k]+10)*ww)/180);
				
				st1[k]++;
				if(j!=0)
				{
					set[k].play(anim[(j-1)%2]).before(anim[j%2]);
					setText.play(animt[(j-1)%2]).before(animt[j%2]);
					set1[k].play(anim1[(j-1)%2]).before(anim1[j%2]);
					setp[k].play(animp[(j-1)%2]).before(animp[j%2]);
					setf[k].play(animf[(j-1)%2]).before(animf[j%2]);
					set1f[k].play(anim1f[(j-1)%2]).before(anim1f[j%2]);
				}
				min++;max++;
			}
			setf[k].start();
			set1f[k].start();
			set[k].start();set1[k].start();setp[k].start();
			Ids++;Ids1++;Ids2++;
		}
		setText.start();
		} catch (Exception e) {
        	System.out.println(k);
			e.printStackTrace();}
		
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
	
}
