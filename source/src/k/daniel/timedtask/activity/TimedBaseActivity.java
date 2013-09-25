package k.daniel.timedtask.activity;

import k.daniel.timedtask.AboutDialogActivity;
import k.daniel.timedtask.R;
import k.daniel.timedtask.app.MyApplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class TimedBaseActivity extends Activity{
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_create_task:
			Intent toCreateIntent=new Intent(this, CreateTaskActivity.class);
			startActivity(toCreateIntent);
			finish();
			break;
		case R.id.menu_about:
			Intent toAboutIntent=new Intent(this,AboutDialogActivity.class);
			startActivity(toAboutIntent);
			break;
		case R.id.menu_setting:
			Intent toSettingIntent=new Intent(this,SettingActivity.class);
			startActivity(toSettingIntent);
			break;
		case R.id.menu_exit:
			MyApplication.exit(false);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//finish();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MyApplication.pop(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		MyApplication.push(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	protected void refresh() {
		Intent refreshIntent=new Intent(this,this.getClass());
		startActivity(refreshIntent);
		finish();
	}
	
}
