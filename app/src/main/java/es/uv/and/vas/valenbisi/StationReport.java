package es.uv.and.vas.valenbisi;

import android.content.Intent;
import android.database.Cursor;
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
    int stationId, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_report);

        intent = getIntent();
        stationId = intent.getIntExtra("stationId", -1);
        id = intent.getIntExtra("id",-1);

        et_rnv = findViewById(R.id.editText_ReportNameValue);
        et_rdv = findViewById(R.id.editText_ReportDescriptionValue);
        s_rsv = findViewById(R.id.spinner_ReportStatusValue);
        s_rtv = findViewById(R.id.spinner_ReportTypeValue);

        dbHelper = StationDetails.dbHelper;

        String[] statuses = getResources().getStringArray(R.array.report_statuses);
        String[] types = getResources().getStringArray(R.array.report_types);

        ArrayAdapter<String> statusSpinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);
        statusSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_rsv.setAdapter(statusSpinnerArrayAdapter);

        ArrayAdapter<String> typeSpinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        typeSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_rtv.setAdapter(typeSpinnerArrayAdapter);

        switch (id) {
            // create new report
            case -1:
                setTitle("New report");
                break;
            // edit existing one
            default: {
                Cursor cursor = dbHelper.FindReportById(id);

                if (cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_REPORT_NAME);
                    int descriptionIndex = cursor.getColumnIndex(DBHelper.KEY_REPORT_DESCRIPTION);
                    int statusIndex = cursor.getColumnIndex(DBHelper.KEY_REPORT_STATUS);
                    int typeIndex = cursor.getColumnIndex(DBHelper.KEY_REPORT_TYPE);

                    setTitle("Edit " + cursor.getString(nameIndex));
                    et_rnv.setText(cursor.getString(nameIndex));
                    et_rdv.setText(cursor.getString(descriptionIndex));
                    s_rsv.setSelection(getIndex(s_rsv, cursor.getString(statusIndex)));
                    s_rtv.setSelection(getIndex(s_rtv, cursor.getString(typeIndex)));
                }
                cursor.close();
            }
        }
    }

    public void deleteReport(View view) {
        switch (id) {
            case -1:
                setResult(RESULT_CANCELED);
                break;
            default:
                dbHelper.DeleteReport(id);
                setResult(RESULT_OK);
                break;
        }
        finish();
    }

    public void saveReport(View view) {
        switch (id) {
            case -1:
                dbHelper.InsertReport(stationId,
                        et_rnv.getText().toString(),
                        et_rdv.getText().toString(),
                        s_rsv.getSelectedItem().toString(),
                        s_rtv.getSelectedItem().toString()
                );
                break;
            default:
                dbHelper.UpdateReport(stationId,
                        et_rnv.getText().toString(),
                        et_rdv.getText().toString(),
                        s_rsv.getSelectedItem().toString(),
                        s_rtv.getSelectedItem().toString(),
                        id
                );
                break;
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
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
