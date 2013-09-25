package k.daniel.timedtask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import k.daniel.timedtask.app.MyApplication;
import k.daniel.timedtask.bean.ExecutableTask;
import k.daniel.timedtask.bean.TimedTask;
import k.daniel.timedtask.datasource.SqliteDataSource;
import k.daniel.timedtask.datasource.TaskDataSources;
import k.daniel.timedtask.receiver.ProcessTaskReceiver;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class TaskProcesser extends Thread {
	public static final int REQ_NORMAL = 1;

	public static final int ADD_TASK = 1;
	public static final int DELETE_TASK = 2;
	TimedTask mTask;
	private int action = -1;

	public TaskProcesser(int action, TimedTask task) {
		super();
		this.action = action;
		this.mTask = task;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		switch (action) {
		case ADD_TASK:
			addTaskToAlarmManager(MyApplication.appContext);
			break;
		case DELETE_TASK:
			if (mTask != null) {
				deleteTaskFromAlarmManager(MyApplication.appContext, mTask);
			}
			break;
		default:
			break;
		}

	}

	private void deleteTaskFromAlarmManager(Context appContext, TimedTask task) {
		// TODO Auto-generated method stub
		ExecutableTask executableTask = convertTimedTaskToExecuteableTask(task);
		// 若task为24小时内的任务，那么删除alarmManager中已经添加的任务
		if (executableTask != null) {
			PendingIntent intent = getPendingIntentByExecuteTask(appContext,
					executableTask);
			AlarmManager alarmManager = (AlarmManager) appContext
					.getSystemService(Context.ALARM_SERVICE);
			alarmManager.cancel(intent);
		}

	}

	public void addTaskToAlarmManager(Context context) {
		TaskDataSources dataSources = new SqliteDataSource();
		List<TimedTask> tasks = dataSources.getAllTask();
		dataSources.close();
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		List<ExecutableTask> executableTasks = convertAllTimedTaskToExecutableTask(tasks);
		for (int i = 0; i < executableTasks.size(); i++) {
			ExecutableTask executableTask = executableTasks.get(i);

			PendingIntent pendingIntent = getPendingIntentByExecuteTask(
					context, executableTask);

			alarmManager.set(AlarmManager.RTC_WAKEUP, executableTask.getTime(),
					pendingIntent);
			System.out.println("Add task to Alarm" + executableTask.getTime()
					+ " " + executableTask.getTaskType());
		}
	}

	private PendingIntent getPendingIntentByExecuteTask(Context context,
			ExecutableTask executableTask) {
		Intent intent = new Intent(MyApplication.appContext,
				ProcessTaskReceiver.class);
		intent.addCategory(executableTask.getId() + "");
		intent.putExtra(ProcessTaskReceiver.PARAMETER_TASK_TYPE,
				executableTask.getTaskType());
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
				REQ_NORMAL, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		return pendingIntent;
	}

	private List<ExecutableTask> convertAllTimedTaskToExecutableTask(
			List<TimedTask> tasks) {
		// TODO Auto-generated method stub
		List<ExecutableTask> executableTasks = new ArrayList<ExecutableTask>();
		ExecutableTask executableTask = null;
		for (int i = 0; i < tasks.size(); i++) {
			executableTask = convertTimedTaskToExecuteableTask(tasks.get(i));
			if (executableTask != null) {
				executableTasks.add(executableTask);
			}
		}

		return executableTasks;
	}

	private ExecutableTask convertTimedTaskToExecuteableTask(TimedTask task) {
		ExecutableTask executableTask = null;
		// 若任务已启动
		if (task.isAvaliable()) {
			// 获取当前时间
			Calendar calendar = Calendar.getInstance();
			int tHour = task.getHour();
			int tMnute = task.getMinute();
			int cHour = calendar.get(Calendar.HOUR_OF_DAY);
			int cMinute = calendar.get(Calendar.MINUTE);

			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			// task的数组周一对应index=0，所以要进行转换
			dayOfWeek = dayOfWeek - 2 < 0 ? 6 : dayOfWeek - 2;
			if (tHour * 60 + tMnute <= cHour * 60 + cMinute) {
				// 任务预定时间小于等于当前时间，预定下一天的
				dayOfWeek = dayOfWeek + 1 > 6 ? 7 : dayOfWeek + 1;
				if (task.getWeekState()[dayOfWeek]) {
					// week状态为有效，进行转换
					calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek + 2 > 7 ? 0
							: dayOfWeek + 2);
					calendar.set(Calendar.HOUR_OF_DAY, tHour);
					calendar.set(Calendar.MINUTE, tMnute);
					calendar.set(Calendar.SECOND, 0);
					executableTask = new ExecutableTask();
					executableTask.setId(task.getId());
					executableTask.setTaskType(task.getTaskType());
					executableTask.setTime(calendar.getTimeInMillis());

				}
			} else {
				// 任务时间大于当前时间，预定当天的
				if (task.getWeekState()[dayOfWeek]) {
					// week状态为有效，进行转换
					calendar.set(Calendar.HOUR_OF_DAY, tHour);
					calendar.set(Calendar.MINUTE, tMnute);
					calendar.set(Calendar.SECOND, 0);
					executableTask = new ExecutableTask();
					executableTask.setId(task.getId());
					executableTask.setTaskType(task.getTaskType());
					executableTask.setTime(calendar.getTimeInMillis());
				}
			}
		}
		return executableTask;
	}

}
