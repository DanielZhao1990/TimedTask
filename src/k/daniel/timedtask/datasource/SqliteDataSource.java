package k.daniel.timedtask.datasource;

import java.util.ArrayList;
import java.util.List;

import k.daniel.timedtask.bean.TimedTask;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SqliteDataSource implements TaskDataSources {
	static final String SELECT_ALL = "select * from ";
	static final String ORDER_BY=" order by thour,tminute";
	static final String TABLE_NAME = " task";
	public static final int TID = 0;
	public static final int THOUR = 1;
	public static final int TMINUTE = 2;
	public static final int TYPE = 3;
	public static final int AVALIABLE = 4;
	public static final int WEEK_STATE = 5;

	SQLiteDatabase database;

	public SqliteDataSource() {
		// TODO Auto-generated constructor stub
		TaskSQLiteOpenHelper helper = new TaskSQLiteOpenHelper();
		database = helper.getWritableDatabase();
	}

	@Override
	public List<TimedTask> getAllTask() {
		// TODO Auto-generated method stub
		List<TimedTask> tasks = new ArrayList<TimedTask>();
		Cursor cursor = database.rawQuery(SELECT_ALL+TABLE_NAME+ORDER_BY, null);
		while (cursor.moveToNext()) {
			TimedTask task = new TimedTask();
			task.setId(cursor.getInt(TID));
			task.setHour(cursor.getInt(THOUR));
			task.setMinute(cursor.getInt(TMINUTE));
			task.setTaskType(cursor.getInt(TYPE));
			task.setAvaliable(cursor.getInt(AVALIABLE));
			task.setWeekState(TaskWeekUtil.getWeekState(cursor
					.getInt(WEEK_STATE)));
			tasks.add(task);
		}
		return tasks;
	}

	@Override
	public void setAllTask(List<TimedTask> timedTasks) {
		// TODO Auto-generated method stub
		for (int i = 0; i < timedTasks.size(); i++) {
			addTask(timedTasks.get(i));
		}
	}

	@Override
	public void addTask(TimedTask timedTask) {
		// TODO Auto-generated method stub
		Integer ids = timedTask.getId()==-1? null : timedTask.getId();
		Object args[] = new Object[] { ids, timedTask.getHour(),
				timedTask.getMinute(), timedTask.getTaskType(),
				timedTask.isAvaliable() ? 1 : 0,
				TaskWeekUtil.getIntState(timedTask.getWeekState()) };
		String insertSql = "insert into " + TABLE_NAME
				+ " values (?,?,?,?,?,?)";
		database.execSQL(insertSql, args);
	}

	@Override
	public boolean deleteTask(TimedTask timedTask) {
		// TODO Auto-generated method stub
		String deleteSql = "delete from " + TABLE_NAME + " where tid=?";
		Object args[] = new Object[] { timedTask.getId() };
		database.execSQL(deleteSql, args);
		return false;
	}

	@Override
	public boolean deleteAllTask() {
		// TODO Auto-generated method stub
		String deleteAllSql = "delete * from " + TABLE_NAME;
		database.execSQL(deleteAllSql);
		return false;
	}

	@Override
	public boolean updateTask(TimedTask timedTask) {
		// TODO Auto-generated method stub
		deleteTask(timedTask);
		addTask(timedTask);
		return false;
	}

	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		if (database!=null) {
			database.close();
			return true;
		}
		return false;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		Cursor cursor = database.rawQuery(SELECT_ALL+TABLE_NAME+ORDER_BY, null);
		return cursor.getCount();
	}
	
}
