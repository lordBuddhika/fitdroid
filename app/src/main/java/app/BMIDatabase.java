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

public class BMIDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "fitdroid.db";
    public static final String TABLE_NAME = "bmi";
    public static final String COLUMN_NAME_BMIID = "bmi_id";
    public static final String COLUMN_NAME_DATE = "date";
    public static final String COLUMN_NAME_WEIGHT = "weight";
    public static final String COLUMN_NAME_HEIGHT = "height";

    public BMIDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public int getBMIRows() {
        SQLiteDatabase db = this.getWritableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public ArrayList<BMI> getBMIs() {
        ArrayList<BMI> bmi = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM bmi ORDER BY bmi_id DESC", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            bmi.add(new BMI(
                    res.getInt(res.getColumnIndex(COLUMN_NAME_BMIID)),
                    res.getString(res.getColumnIndex(COLUMN_NAME_DATE)),
                    res.getDouble(res.getColumnIndex(COLUMN_NAME_WEIGHT)),
                    res.getDouble(res.getColumnIndex(COLUMN_NAME_HEIGHT)))
            );
            res.moveToNext();
        }

        return bmi;
    }

    public boolean newBMI(String date, double weight, double height) {
        Date presentTime_Date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time_end = dateFormat.format(presentTime_Date);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvals = new ContentValues();
        cvals.put("date", date);
        cvals.put("weight", weight);
        cvals.put("height", height);
        db.insert(TABLE_NAME, null, cvals);
        return true;
    }

    public boolean updateBMI(int bmi_id, String date, double weight, double height) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cvals = new ContentValues();
        cvals.put("date", date);
        cvals.put("weight", weight);
        cvals.put("height", height);
        db.update(TABLE_NAME, cvals, "bmi_id = ? ", new String[] { Integer.toString(bmi_id)} );
        return true;
    }

    public Integer deleteBMI(int bmi_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "bmi_id = ?", new String[] { Integer.toString(bmi_id)});
    }
}
