package k.daniel.timedtask.activity;

import k.daniel.timedtask.R;
import k.daniel.timedtask.TaskProcesser;
import k.daniel.timedtask.datasource.SqliteDataSource;
import k.daniel.timedtask.datasource.TaskDataSources;
import k.daniel.timedtask.view.TaskListAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class TaskListActivity extends TimedBaseActivity {
	ListView contentListView;
	TaskListAdapter taskListAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_list);
		TaskDataSources dataSources=new SqliteDataSource();
		if (dataSources.count()==0) {
			Intent toCreateIntent=new Intent(this, CreateTaskActivity.class);
			startActivity(toCreateIntent);
		}
		dataSources.close();
		
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		contentListView=(ListView) findViewById(R.id.content_listView);
		taskListAdapter=new TaskListAdapter( this);
		contentListView.setAdapter(taskListAdapter);
		//new a thread to add all task to alarmManager
		new TaskProcesser(TaskProcesser.ADD_TASK,null).start();
	
	}
}
