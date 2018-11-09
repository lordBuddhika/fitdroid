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


public class SleepTrackerDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "fitdroid.db";
    public static final String TABLE_NAME = "sleeptracker";
    public static final String COLUMN_NAME_SLIIPID = "slp_id";
    public static final String COLUMN_NAME_SLEEPSTART = "startt";
    public static final String COLUMN_NAME_SLEEPEND = "endt";

    public SleepTrackerDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getSleepRows() {
        SQLiteDatabase db = this.getWritableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public ArrayList<Sleep> getSleeps() {
        ArrayList<Sleep> sleepList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM sleeptracker ORDER BY slp_id DESC", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            sleepList.add(new Sleep(
                    res.getInt(res.getColumnIndex(COLUMN_NAME_SLIIPID)),
                    res.getString(res.getColumnIndex(COLUMN_NAME_SLEEPSTART)),
                    res.getString(res.getColumnIndex(COLUMN_NAME_SLEEPEND)))
            );
            res.moveToNext();
        }

        return sleepList;
    }

    public boolean newSleep(String start_time, String end_time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvals = new ContentValues();
        cvals.put(COLUMN_NAME_SLEEPSTART, start_time);
        cvals.put(COLUMN_NAME_SLEEPEND, end_time);
        db.insert(TABLE_NAME, null, cvals);
        return true;
    }

    public boolean updateSleep(int sleep_id, String start_time, String end_time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvals = new ContentValues();
        cvals.put(COLUMN_NAME_SLEEPSTART, start_time);
        cvals.put(COLUMN_NAME_SLEEPEND, end_time);
        db.update(TABLE_NAME, cvals, COLUMN_NAME_SLIIPID+" = ? ", new String[] { Integer.toString(sleep_id)} );
        return true;
    }

    public Integer deleteSleep(int sleep_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_NAME_SLIIPID+" = ? ", new String[] { Integer.toString(sleep_id)});
    }

}
