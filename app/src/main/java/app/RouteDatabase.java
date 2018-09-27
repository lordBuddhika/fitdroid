package app;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
             COLUMN_NAME_TIMEEND + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public int getRouteRows() {
        SQLiteDatabase db = this.getWritableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public ArrayList<Route> getRoutes() {
        ArrayList<Route> route = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM routes", null);
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
}
