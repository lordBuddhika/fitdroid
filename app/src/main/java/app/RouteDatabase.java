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

public class RouteDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "fitdroid.db";
    public static final String TABLE_NAME = "routes";
    public static final String COLUMN_NAME_ROUTEID = "route_id";
    public static final String COLUMN_NAME_DISTANCE = "distance";
    public static final String COLUMN_NAME_TOPSPEED = "top_speed";
    public static final String COLUMN_NAME_DURATION = "duration";
    public static final String COLUMN_NAME_TIMESTART = "time_start";
    public static final String COLUMN_NAME_TIMEEND = "time_end";

    public RouteDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
            "CREATE TABLE " + TABLE_NAME + "(" +
             COLUMN_NAME_ROUTEID + " integer PRIMARY KEY, " +
             COLUMN_NAME_DISTANCE + " text, " +
             COLUMN_NAME_TOPSPEED + " text, " +
             COLUMN_NAME_DURATION + " text, " +
             COLUMN_NAME_TIMESTART + " text, " +
             COLUMN_NAME_TIMEEND + " text);"
        );

        sqLiteDatabase.execSQL(
            "CREATE TABLE bmi (" +
            "bmi_id" + " integer PRIMARY KEY, " +
            "date" + " text, " +
            "weight" + " text, " +
            "height" + " text)"
        );

        sqLiteDatabase.execSQL(
            "CREATE TABLE alarm (" +
                "alarm_id" + " integer PRIMARY KEY, " +
                "time" + " text)"
        );

        sqLiteDatabase.execSQL(
                "CREATE TABLE sleeptracker (" +
                        "slp_id" + " integer PRIMARY KEY, " +
                        "startt" + " text, " +
                        "endt" + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "bmi");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "alarm");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "sleeptracker");
    }

    public int getRouteRows() {
        SQLiteDatabase db = this.getWritableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public ArrayList<Route> getRoutes() {
        ArrayList<Route> route = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM routes ORDER BY route_id DESC", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            route.add(new Route(
                    res.getInt(res.getColumnIndex(COLUMN_NAME_ROUTEID)),
                    res.getDouble(res.getColumnIndex(COLUMN_NAME_DISTANCE)),
                    res.getDouble(res.getColumnIndex(COLUMN_NAME_TOPSPEED)),
                    res.getDouble(res.getColumnIndex(COLUMN_NAME_DURATION)),
                    res.getString(res.getColumnIndex(COLUMN_NAME_TIMESTART)),
                    res.getString(res.getColumnIndex(COLUMN_NAME_TIMEEND)))
            );
            res.moveToNext();
        }

        return route;
    }

    public boolean newRoute(double distance, double top_speed, double duration, String time_start) {
        Date presentTime_Date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time_end = dateFormat.format(presentTime_Date);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvals = new ContentValues();
        cvals.put("distance", distance);
        cvals.put("top_speed", top_speed);
        cvals.put("duration", duration);
        cvals.put("time_start", time_start);
        cvals.put("time_end", time_end);
        db.insert(TABLE_NAME, null, cvals);
        return true;
    }

    public boolean updateRoute(int route_id, double distance, double top_speed, double duration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvals = new ContentValues();
        cvals.put("distance", distance);
        cvals.put("top_speed", top_speed);
        cvals.put("duration", duration);
        db.update(TABLE_NAME, cvals, "route_id = ? ", new String[] { Integer.toString(route_id)} );
        return true;
    }

    public Integer deleteRoute(int route_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "route_id = ?", new String[] { Integer.toString(route_id)});
    }
}
