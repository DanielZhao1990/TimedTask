package k.daniel.timedtask.receiver;

import k.daniel.timedtask.TaskProcesser;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		//new a thread to add all task to alarmManager
				new TaskProcesser(TaskProcesser.ADD_TASK,null).start();
	}

}
