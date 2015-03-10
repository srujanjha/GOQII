package com.example.alarm;

import com.example.alarm.NotificationReceiverActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlarmReciever extends BroadcastReceiver
{
         @Override
            public void onReceive(Context context, Intent intent)
            {
                    // TODO Auto-generated method stub
                
        	 System.out.println("hYo");
                      // here you can start an activity or service depending on your need
                     // for ex you can start an activity to vibrate phone or to ring the phone   
                                     
                    Intent intent1 = new Intent(context, NotificationReceiverActivity.class);
                    PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent1, 0);
int i=intent.getFlags();String alarmText=intent.getStringExtra("AlarmText");
System.out.println(alarmText);
                   // build notification
                    // the addAction re-use the same intent to keep the example short
                    Notification n  = new Notification.Builder(context)
                            .setContentTitle("Activity")
                            .setContentText(i+alarmText)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentIntent(pIntent)
                            .setAutoCancel(true).build();
                        
                      
                    NotificationManager notificationManager = 
                      (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                    notificationManager.notify(i, n); 
                    System.out.println("Yo");
             }

		
}