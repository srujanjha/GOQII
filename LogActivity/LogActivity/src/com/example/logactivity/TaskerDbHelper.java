package com.example.logactivity;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
public class TaskerDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "GoQii_Activity";
    // tasks table name
    private static final String TABLE_TASKS = "Activity";
    // tasks Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_TASKNAME = "taskName";
    private static final String startTime = "Start";
    private static final String endTime = "End";
    private static final String Intensity = "Intensity";
    private static final String Calories = "Calories";
    private static final String Days = "Days";
    private static final String Frequency = "Frequency";
    private static final String Notify = "Notify";
    
    public TaskerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TASKNAME+ " TEXT, "
                + startTime+ " INTEGER, "
                + endTime+ " INTEGER, "
                + Intensity+ " TEXT, "
                + Calories + " TEXT, "
                + Days+ " INTEGER, "
                + Frequency + " INTEGER, "
                + Notify + " INTEGER)";
        System.out.println(sql);
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
         // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        // Create tables again
        onCreate(db);
    }
    public void addTask(Task task) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_TASKNAME, task.getTaskName()); // task name
    	values.put(startTime, task.getStartTime());
    	values.put(endTime, task.getEndTime());
    	values.put(Intensity, task.getIntensity());
    	values.put(Calories, task.getCalories());
    	values.put(Days, task.getDays());
    	values.put(Frequency, task.getFrequency());
    	values.put(Notify, task.getNotify());

    	// Inserting Row
    	db.insert(TABLE_TASKS, null, values);
    	db.close(); // Closing database connection
    	}
    public List<Task> getAllTasks() {
    	List<Task> taskList = new ArrayList<Task>();
    	// Select All Query
    	String selectQuery = "SELECT  * FROM " + TABLE_TASKS;
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	// looping through all rows and adding to list
    	if (cursor.moveToFirst()) {
    	do {
    	Task task = new Task();
    	task.setId(cursor.getInt(0));
    	task.setTaskName(cursor.getString(1));
    	task.setStartTime(cursor.getLong(2));
    	task.setEndTime(cursor.getLong(3));
    	task.setIntensity(cursor.getString(4));
    	task.setCalories(cursor.getString(5));
    	task.setDays(cursor.getInt(6));
    	task.setFrequency(cursor.getInt(7));
    	task.setNotify(cursor.getInt(8));
    	taskList.add(task);
    	} while (cursor.moveToNext());
    	}
    	// return task list
    	return taskList;
    	}
    public void updateTask(Task task) {
    	// updating row
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_TASKNAME, task.getTaskName());
    	values.put(startTime, task.getStartTime());
    	values.put(endTime, task.getEndTime());
    	values.put(Intensity, task.getIntensity());
    	values.put(Calories, task.getCalories());
    	values.put(Days, task.getDays());
    	values.put(Frequency, task.getFrequency());
    	values.put(Notify, task.getNotify());
    	db.update(TABLE_TASKS, values, KEY_ID + " = ?",new String[]{String.valueOf(task.getId())});
    	db.close();
    	}
    public void deleteTask(Task task) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_TASKS, KEY_ID + " = ?",new String[]{String.valueOf(task.getId())});
    	db.close();
    	}

}
