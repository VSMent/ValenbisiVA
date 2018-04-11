package es.uv.and.vas.valenbisi;

import android.content.Context;
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



    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
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
}
