package k.daniel.timedtask.receiver;

import java.util.Calendar;

import k.daniel.timedtask.R;
import k.daniel.timedtask.TaskProcesser;
import k.daniel.timedtask.util.NotificationUtil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;

public class ProcessTaskReceiver extends BroadcastReceiver{
public static final String ACTION_STRING="k.daniel.timedtask.ProcessTask";
public static final String PARAMETER_TASK_TYPE="TaskType";
public static final int NOTIFICATION_ID=228;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		int taskType=intent.getIntExtra(PARAMETER_TASK_TYPE,-1);
		
		AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);	
		System.out.println("Process Task:"+taskType);
		addNotification(context, taskType);
		
		switch (taskType) {
		case 0:
			//changeMode(audio, AudioManager.RINGER_MODE_NORMAL);
			ringAndVibrate(audio);
			break;
		case 1:
			//changeMode(audio, AudioManager.RINGER_MODE_SILENT);
			silent(audio);
			break;
		case 2:
			//changeMode(audio, AudioManager.RINGER_MODE_VIBRATE);
			vibrate(audio);
			break;
		case 3:
			ring(audio);
			break;
		case 4:
			if (!IsAirModeOn(context)) {
				setAirplaneMode(context, true);
			}
			break;
		case 5:
			if (IsAirModeOn(context)) {
				setAirplaneMode(context,false);
			}
			break;
		case 6:
			changeWifiState(context, true);
			break;
			
		case 7:
			changeWifiState(context, false);
			break;
		case -1:
			break;
		default:
			break;
		}
		//new a thread to add all task to alarmManager
		new TaskProcesser(TaskProcesser.ADD_TASK,null).start();
	}
	
	/**
	 * To change the wifi state
	 * @param context
	 * @param open if is true,open the wifi. else close it.
	 */
	public void changeWifiState(Context context,boolean open)
	{
		WifiManager wifiManager=(WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if (open) {
			if (!wifiManager.isWifiEnabled()) {
				wifiManager.setWifiEnabled(open);
			}
		}else {
			if (wifiManager.isWifiEnabled()) {
				wifiManager.setWifiEnabled(open);
			}
		}
	}
	public void changeMode(AudioManager audio,int mode)
	{
		audio.setRingerMode(mode);	
	}
	//铃声和震动
    protected void ringAndVibrate(AudioManager audio) {
    	audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);
        audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_ON);
    }
  //铃声
    protected void ring(AudioManager audio) {
    	audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_OFF);
        audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_OFF);
        
    }
  //震动
    protected void vibrate(AudioManager audio) {
    	audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_ON);
        audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_ON);   
        
    }
  //静音
    protected void silent(AudioManager audio) {
    	audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, AudioManager.VIBRATE_SETTING_OFF);
        audio.setVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION, AudioManager.VIBRATE_SETTING_OFF);   
    }
    
    private void addNotification(Context context,int taskType)
    {
    	String oprations[]=context.getResources().getStringArray(R.array.task_type_value);
		Calendar calendar=Calendar.getInstance();
    	int smallIcon=R.drawable.clock;
    	int hour=calendar.get(Calendar.HOUR_OF_DAY);
    	int minute=calendar.get(Calendar.MINUTE);
    	String minuteString=minute<10? "0"+minute:""+minute;
    	String contentTitleString=context.getString(R.string.notification_title_timedtask);
    	String contentText=hour+":"+minuteString+" "+context.getString(R.string.content_executed)+" "+oprations[taskType];
    	new NotificationUtil(context).addNotification(smallIcon, contentTitleString, contentText);

    }
    /**
     * 判断飞行模式是否已开启
     * @param context
     * @return
     */
    public static boolean IsAirModeOn(Context context) {
		return (Settings.System.getInt(context.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) == 1 ? true : false);
	}
    /**
     * 设置飞行模式
     * @param context
     * @param enabling enabling=true为开启飞行模式，false关闭 
     */
	public static void setAirplaneMode(Context context, boolean enabling) {
		Settings.System.putInt(context.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, enabling ? 1 : 0);
		Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
		intent.putExtra("state", enabling);
		context.sendBroadcast(intent);
	}
}
