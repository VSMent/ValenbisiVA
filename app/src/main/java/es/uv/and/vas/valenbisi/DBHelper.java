package es.uv.and.vas.valenbisi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "stationsDB";
    public static final String TABLE_NAME = "reports";

    public static final String KEY_ID = "_id";     // record id
    public static final String KEY_STATION = "station";    // stationId number
    public static final String KEY_REPORT_NAME = "name";   // report name
    public static final String KEY_REPORT_DESCRIPTION = "description";   // report descr
    public static final String KEY_REPORT_STATUS = "status";   // report status
    public static final String KEY_REPORT_TYPE = "type";   // report type

    SQLiteDatabase database;
    ContentValues contentValues;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TABLE_NAME + "("
                        + KEY_ID + " integer primary key,"
                        + KEY_STATION + " integer,"
                        + KEY_REPORT_NAME + " text,"
                        + KEY_REPORT_DESCRIPTION + " text,"
                        + KEY_REPORT_STATUS + " text,"
                        + KEY_REPORT_TYPE + " text"
                        + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
    public void InsertReport(Integer stationId, String name, String description,String status, String type){
        contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_STATION, stationId);
        contentValues.put(DBHelper.KEY_REPORT_NAME, name);
        contentValues.put(DBHelper.KEY_REPORT_DESCRIPTION, description);
        contentValues.put(DBHelper.KEY_REPORT_STATUS, status);
        contentValues.put(DBHelper.KEY_REPORT_TYPE, type);

        database.insert(DBHelper.TABLE_NAME, null, contentValues);
    }
    public void UpdateReport(Integer stationId, String name, String description,String status, String type, Integer reportId){
        contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_STATION, stationId);
        contentValues.put(DBHelper.KEY_REPORT_NAME, name);
        contentValues.put(DBHelper.KEY_REPORT_DESCRIPTION, description);
        contentValues.put(DBHelper.KEY_REPORT_STATUS, status);
        contentValues.put(DBHelper.KEY_REPORT_TYPE, type);

        database.update(DBHelper.TABLE_NAME, contentValues, DBHelper.KEY_ID + "=" + reportId, null);
    }
    public void DeleteReport(Integer reportId){
        database.delete(DBHelper.TABLE_NAME,DBHelper.KEY_ID + "=" + reportId,null);
    }

    public Cursor FindReportByBikeStation(Integer stationId){
        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                        new String[]{DBHelper.KEY_ID,DBHelper.KEY_REPORT_STATUS, DBHelper.KEY_REPORT_NAME},
                        DBHelper.KEY_STATION + "=" + stationId,
                        null, null, null, null);
        return cursor;
    }
    public Cursor FindReportById(Integer reportId){
        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                new String[]{DBHelper.KEY_REPORT_NAME, DBHelper.KEY_REPORT_DESCRIPTION, DBHelper.KEY_REPORT_STATUS, DBHelper.KEY_REPORT_TYPE},
                DBHelper.KEY_ID + "=" + reportId,
                null, null, null, null);
        return cursor;
    }
    public int ReportsAmountForStation(Integer stationId){
        Cursor cursor = database.query(DBHelper.TABLE_NAME, null, DBHelper.KEY_STATION + "=" + stationId, null, null, null, null);
        return cursor.getCount();
    }
}
