package k.daniel.timedtask.util;

import java.util.ArrayList;
import java.util.List;

import k.daniel.timedtask.activity.TaskListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationUtil {
	NotificationManager notificationManager;
	Context mContext;
	public static List<Integer> idList=new ArrayList<Integer>();
	
	public NotificationUtil(Context mContext) {
		super();
		this.mContext = mContext;
		notificationManager=(NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
	}


	public void addNotification(int smallIconId,String title,String contentText) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(mContext,TaskListActivity.class);
		PendingIntent contentIntent=PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);	
		addNotification(smallIconId, title, contentText, contentIntent);
	}
	
	public void addNotification(int smallIconId,String title,String contentText,PendingIntent contentIntent) {
		// TODO Auto-generated method stub
		NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(mContext);
		mBuilder.setSmallIcon(smallIconId);		
		mBuilder.setContentTitle(title);
		mBuilder.setContentText(contentText);	
		mBuilder.setContentIntent(contentIntent);
		mBuilder.setProgress(100, 50, false);
		Notification notification=mBuilder.build();
		notification.flags=Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(idList.size(),notification);
		idList.add(idList.size());
		
	}
	

}
