package k.daniel.timedtask.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class MyApplication extends Application{
	public static Context appContext;
	
	private static List<Activity> activities=new ArrayList<Activity>();
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		appContext = getApplicationContext();
		/*Configuration configuration=this.getResources().getConfiguration();
		configuration.locale=new Locale("en");
		getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());*/
		
	}
	public static void pop(Activity activity)
	{
		activities.remove(activity);
	}
	public static  void push(Activity activity)
	{
		activities.add(activity);
	}
	public static void exit(boolean isClear)
	{
		for (Activity activity:activities) {
			activity.finish();
		}
		if (isClear) {
			System.exit(0);
		}
		
	}
	public static boolean isSdcardAvaliable()
	{
		return true;
	}

}
