package k.daniel.timedtask.datasource;

import k.daniel.timedtask.app.MyApplication;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskSQLiteOpenHelper extends SQLiteOpenHelper {

	static String fileName = "TaskData.db";
	private static final int version = 1; // 数据库版本
	static String CREATE_TABLE="create table task(tid integer primary key,thour integer,tminute integer,taskType integer,isavaliable integer,weekState integer)";

	public TaskSQLiteOpenHelper() {
		this(MyApplication.appContext, fileName, null, version);
	}

	public TaskSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE);
	}

	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
