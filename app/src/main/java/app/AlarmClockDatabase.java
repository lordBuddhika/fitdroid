package app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AlarmClockDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "fitdroid.db";
    public static final String TABLE_NAME = "alarm";
    public static final String COLUMN_NAME_ALARMID = "alarm_id";
    public static final String COLUMN_NAME_TIME = "time";

    public AlarmClockDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public boolean updateAlarm(int alarm_id, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvals = new ContentValues();
        cvals.put("alarm_id", alarm_id);
        cvals.put("time", time);
        db.update(TABLE_NAME, cvals, "alarm_id = ? ", new String[] { Integer.toString(alarm_id)} );
        return true;
    }

    public Integer deleteAlarm(int alarm_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "alarm_id = ?", new String[] { Integer.toString(alarm_id)});
    }
}
