package k.daniel.timedtask.activity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import k.daniel.timedtask.R;
import k.daniel.timedtask.bean.TimedTask;
import k.daniel.timedtask.datasource.SqliteDataSource;
import k.daniel.timedtask.datasource.TaskDataSources;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

public class CreateTaskActivity extends TimedBaseActivity implements OnClickListener, OnCheckedChangeListener {
	public static final String PARAMETER_TASK = "task";
	public final static int TIME_DIALOG_ID = 1;
	boolean isUpdate=false;
	TimedTask task=null;
	TextView taskTimeTextView;
	TimePicker picker;
	ToggleButton toggleButton;
	CheckBox weekBoxs[];
	Spinner taskTypeSpinner;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_task);
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Intent fromIntent =getIntent();
		Serializable serializableTask=fromIntent.getSerializableExtra(PARAMETER_TASK);
		task =(TimedTask) serializableTask;
	
		List<String> taskTypeList = Arrays.asList(getResources()
				.getStringArray(R.array.task_type_value));
		taskTypeSpinner = (Spinner) findViewById(R.id.spinner_task_type);
		ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, taskTypeList);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		taskTypeSpinner.setAdapter(mAdapter);

		Button createButton = (Button) findViewById(R.id.button_create);
		Button cancleButton = (Button) findViewById(R.id.button_cancle);
		createButton.setOnClickListener(this);
		cancleButton.setOnClickListener(this);
		
		taskTimeTextView = (TextView) findViewById(R.id.textview_task_time);
		
		picker = (TimePicker) findViewById(R.id.timePicker);
		picker.setIs24HourView(true);
		
		// Make avaliable default as true.
		toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
		toggleButton.setChecked(true);
		CheckBox everydayBox=(CheckBox) findViewById(R.id.checkbox_everyday);
		everydayBox.setOnCheckedChangeListener(this);
		weekBoxs=new CheckBox[7];
		weekBoxs[0]=(CheckBox) findViewById(R.id.checkbox_mo);
		weekBoxs[1]=(CheckBox) findViewById(R.id.checkbox_tu);
		weekBoxs[2]=(CheckBox) findViewById(R.id.checkbox_we);
		weekBoxs[3]=(CheckBox) findViewById(R.id.checkbox_th);
		weekBoxs[4]=(CheckBox) findViewById(R.id.checkbox_fr);
		weekBoxs[5]=(CheckBox) findViewById(R.id.checkbox_sa);
		weekBoxs[6]=(CheckBox) findViewById(R.id.checkbox_su);
		
		if (task!=null) {
			taskTypeSpinner.setSelection(task.getTaskType());
			picker.setCurrentHour(task.getHour());
			picker.setCurrentMinute(task.getMinute());
			toggleButton.setChecked(task.isAvaliable());
			boolean weekState[]=task.getWeekState();
			for (int i = 0; i < weekState.length; i++) {
				weekBoxs[i].setChecked(weekState[i]);
			}
			isUpdate=true;
			createButton.setText(R.string.string_save);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		boolean result=super.onCreateOptionsMenu(menu);
		menu.removeItem(R.id.menu_create_task);
		return result;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_create:
			int hour = picker.getCurrentHour();
			int minute = picker.getCurrentMinute();
			int taskType = taskTypeSpinner.getSelectedItemPosition();
			boolean avaliable=toggleButton.isChecked();
			boolean weekState[]=new boolean[weekBoxs.length];
			for (int i = 0; i < weekBoxs.length; i++) {
				weekState[i]=weekBoxs[i].isChecked();
			}
			TaskDataSources taskData= new SqliteDataSource();
			if (isUpdate) {
				task = new TimedTask(task.getId(),hour,minute,taskType,avaliable,weekState);
				taskData.updateTask(task);
			}else {
				task = new TimedTask(hour,minute,taskType,avaliable,weekState);
				taskData.addTask(task);
			}
			taskData.close();
			toListActivity();
			break;
		case R.id.button_cancle:
			toListActivity();
			break;
		default:
			break;
		}
	}

	private void toListActivity() {
		Intent toListIntent=new Intent(this, TaskListActivity.class);
		startActivity(toListIntent);
		finish();
	}
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		// TODO Auto-generated method stub
		if (arg1) {
			for (int i = 0; i < weekBoxs.length; i++) {
				weekBoxs[i].setChecked(true);
			}
		}else {
			for (int i = 0; i < weekBoxs.length; i++) {
				weekBoxs[i].setChecked(false);
			}
		}
	}

}
