package k.daniel.timedtask.view;

import java.io.Serializable;
import java.util.List;

import k.daniel.timedtask.R;
import k.daniel.timedtask.TaskProcesser;
import k.daniel.timedtask.activity.CreateTaskActivity;
import k.daniel.timedtask.bean.TimedTask;
import k.daniel.timedtask.datasource.SqliteDataSource;
import k.daniel.timedtask.datasource.TaskDataSources;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class TaskListAdapter extends BaseAdapter implements OnClickListener {
	List<TimedTask> tasks;
	Context mContext;

	public int getCount() {
		// TODO Auto-generated method stub
		return tasks.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return tasks.get(arg0);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyViewHolder viewHolder;
		if (convertView == null) {
			convertView = View.inflate(mContext,
					R.layout.activity_task_list_item, null);
			viewHolder = initialViewHolder(convertView);

		} else {
			viewHolder = (MyViewHolder) convertView.getTag();
		}
		// get the string data from array.xml
		String weekArray[] = mContext.getResources().getStringArray(
				R.array.task_week);
		String typeArray[] = mContext.getResources().getStringArray(
				R.array.task_type_value);

		TimedTask task = tasks.get(position);

		// set Task Type
		viewHolder.typeTextView.setText(typeArray[task.getTaskType()]);

		// Convert the task time as 9:03
		String timeString = task.getHour()
				+ ":"
				+ (task.getMinute() < 10 ? "0" + task.getMinute() : task
						.getMinute());
		viewHolder.timeTextView.setText(timeString);

		// convert the week state to day name
		String week = "";
		for (int i = 0; i < task.getWeekState().length; i++) {
			if (task.getWeekState()[i]) {
				week = week + " " + weekArray[i];
			}
		}
		viewHolder.weekTextView.setText(week);
		viewHolder.editView.setTag(position);
		viewHolder.deleteView.setTag(position);
		if (task.isAvaliable()) {
			viewHolder.editView
					.setBackgroundResource(R.drawable.edit_area_avaliable);
		} else {
			viewHolder.editView
					.setBackgroundResource(R.drawable.edit_area_disable);
		}
		return convertView;
	}

	private MyViewHolder initialViewHolder(View convertView) {
		MyViewHolder viewHolder;
		viewHolder = new MyViewHolder();
		viewHolder.typeTextView = (TextView) convertView
				.findViewById(R.id.textview_task_type);
		viewHolder.timeTextView = (TextView) convertView
				.findViewById(R.id.textview_task_time);
		viewHolder.weekTextView = (TextView) convertView
				.findViewById(R.id.textview_task_week);
		viewHolder.editView = (LinearLayout) convertView
				.findViewById(R.id.edit_area);
		viewHolder.deleteView = (LinearLayout) convertView
				.findViewById(R.id.delete_area);
		viewHolder.editView.setOnClickListener(this);
		viewHolder.deleteView.setOnClickListener(this);
		convertView.setTag(viewHolder);
		return viewHolder;
	}

	public TaskListAdapter(Context mContext) {
		super();

		updateTasksFromDataSources();
		this.mContext = mContext;
	}

	public TaskListAdapter(List<TimedTask> tasks, Context mContext) {
		super();
		this.tasks = tasks;
		this.mContext = mContext;
	}

	SimpleAdapter simpleAdapter;

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.edit_area:
			clickEditArea(arg0);
			break;
		case R.id.delete_area:
			clickDeleteArea(arg0);
			break;
		default:
			break;
		}
	}

	private void clickDeleteArea(View arg0) {
		int position;
		position = (Integer) arg0.getTag();
		if (position != -1) {
			TaskProcesser taskProcesser=new TaskProcesser(TaskProcesser.DELETE_TASK, tasks.get(position));
			taskProcesser.start();
			TaskDataSources data = new SqliteDataSource();
			data.deleteTask(tasks.get(position));
			data.close();
			updateTasksFromDataSources();
			notifyDataSetChanged();
		}
	}
	private void clickEditArea(View arg0) {
		int position;
		position = (Integer) arg0.getTag();
		if (position != -1) {
			Intent toCreateIntent = new Intent(mContext,
					CreateTaskActivity.class);
			Serializable serializableTask = tasks.get(position);
			toCreateIntent.putExtra(CreateTaskActivity.PARAMETER_TASK,
					serializableTask);
			mContext.startActivity(toCreateIntent);
		}
	}

	public void updateTasksFromDataSources() {
		TaskDataSources data = new SqliteDataSource();
		tasks = data.getAllTask();
		data.close();
	}

	public class MyViewHolder {
		public TextView typeTextView;
		public TextView timeTextView;
		public TextView weekTextView;
		LinearLayout editView;
		LinearLayout deleteView;
	}
}
