package es.uv.and.vas.valenbisi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class StationReport extends AppCompatActivity {

    Intent intent;
    Spinner s_rsv, s_rtv;
    EditText et_rnv, et_rdv;
    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;
    int stationId;
    String reportName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_report);

        intent = getIntent();
        stationId = intent.getIntExtra("stationId", -1);
        reportName = intent.getStringExtra("reportName");

        et_rnv = findViewById(R.id.editText_ReportNameValue);
        et_rdv = findViewById(R.id.editText_ReportDescriptionValue);
        s_rsv = findViewById(R.id.spinner_ReportStatusValue);
        s_rtv = findViewById(R.id.spinner_ReportTypeValue);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        String[] statuses = getResources().getStringArray(R.array.report_statuses);
        String[] types = getResources().getStringArray(R.array.report_types);

        ArrayAdapter<String> statusSpinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);
        statusSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_rsv.setAdapter(statusSpinnerArrayAdapter);

        ArrayAdapter<String> typeSpinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        typeSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_rtv.setAdapter(typeSpinnerArrayAdapter);

        switch (reportName) {
            case "":
                setTitle("New report");
                break;
            default: {
                Cursor cursor = database.query(DBHelper.TABLE_NAME,
                        new String[]{DBHelper.KEY_REPORT_DESCRIPTION, DBHelper.KEY_REPORT_STATUS, DBHelper.KEY_REPORT_TYPE},
                        DBHelper.KEY_REPORT_NAME + "=" + reportName,
                        null, null, null, null);

                setTitle("Edit " + reportName);
                et_rnv.setText(reportName);

                if (cursor.moveToFirst()) {
                    int descriptionIndex = cursor.getColumnIndex(DBHelper.KEY_REPORT_DESCRIPTION);
                    int statusIndex = cursor.getColumnIndex(DBHelper.KEY_REPORT_STATUS);
                    int typeIndex = cursor.getColumnIndex(DBHelper.KEY_REPORT_TYPE);

                    et_rdv.setText(cursor.getString(descriptionIndex));
                    s_rsv.setSelection(getIndex(s_rsv, cursor.getString(statusIndex)));
                    s_rtv.setSelection(getIndex(s_rtv, cursor.getString(typeIndex)));
                }
                cursor.close();
            }
        }
    }

    public void deleteReport(View view) {
        switch (reportName) {
            case "":
                setResult(RESULT_CANCELED);
                break;
            default:
                database.delete(DBHelper.TABLE_NAME,DBHelper.KEY_REPORT_NAME + "=" + reportName,null);
                setResult(RESULT_OK);
                break;
        }
        dbHelper.close();
        finish();
    }

    public void saveReport(View view) {
        contentValues = new ContentValues();

        contentValues.put(DBHelper.KEY_STATION, stationId);
        contentValues.put(DBHelper.KEY_REPORT_NAME, et_rnv.getText().toString());
        contentValues.put(DBHelper.KEY_REPORT_DESCRIPTION, et_rdv.getText().toString());
        contentValues.put(DBHelper.KEY_REPORT_STATUS, s_rsv.getSelectedItem().toString());
        contentValues.put(DBHelper.KEY_REPORT_TYPE, s_rtv.getSelectedItem().toString());

        switch (reportName) {
            case "":
                database.insert(DBHelper.TABLE_NAME, null, contentValues);
                break;
            default:
                database.update(DBHelper.TABLE_NAME, contentValues, DBHelper.KEY_REPORT_NAME + "=" + reportName, null);
                break;
        }
        setResult(RESULT_OK);
        dbHelper.close();
        finish();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        setResult(RESULT_OK);
        dbHelper.close();
        finish();
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }
}
