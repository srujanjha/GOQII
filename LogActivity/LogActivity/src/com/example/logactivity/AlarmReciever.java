package com.example.logactivity;


import java.util.Calendar;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReciever extends BroadcastReceiver
{
	public static int i;
	protected TaskerDbHelper db;
    List<Task> list;
         @Override
            public void onReceive(Context context, Intent intent)
            {
        	 		i=intent.getIntExtra("Number", 1);
            		String alarmText=intent.getStringExtra("AlarmText");
            		int taskno=i%24;
            		int taskid=i/24;
            		//System.out.println(i+" "+taskno+" "+taskid);
            		Notification n ;
            		Notification.Builder n1=new Notification.Builder(context);
            		n1.setContentTitle("Activity");
                    n1.setContentText(alarmText);
                    n1.setSmallIcon(R.drawable.seek_thumb_normal);
                    n1.setAutoCancel(true);
            		if(taskno>12)
            		{
            			//logthis
            			intent.addFlags(taskid);
                        Intent yes=new Intent(context,Logging.class);
                        yes.addFlags(taskid);
                        PendingIntent pyes=PendingIntent.getActivity(context, 0, yes, 0);
                        n1.setContentIntent(pyes);
            		}
            		n=n1.build();
            		
            		Calendar rightnow=Calendar.getInstance();
                    int k=rightnow.get(Calendar.DAY_OF_WEEK);
                    db = new TaskerDbHelper(context);
                    list = db.getAllTasks();
                    Task tsk=new Task();
                    for(int z=0;z<list.size();z++)	if(list.get(z).getId()==taskid)tsk=list.get(z);
                    int days=tsk.getDays();
                    if((days>>(7-k)&1)==1)
                    {
                    NotificationManager notificationManager = 
                      (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(i, n);
                   }
              }

		
}