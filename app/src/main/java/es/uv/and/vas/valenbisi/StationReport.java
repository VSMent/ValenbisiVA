package es.uv.and.vas.valenbisi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class StationReport extends AppCompatActivity {

    Intent intent;
    Station st;
    Spinner s_rsv;
    Spinner s_rtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_report);

        intent = getIntent();
        int pos = intent.getIntExtra("pos",-1);
        String type = intent.getStringExtra("type");
        if(pos == -1){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }else {
            st = AdapterStations.stations.get(pos);

            EditText et_riv = findViewById(R.id.editText_ReportIssueValue);
            EditText et_rdv = findViewById(R.id.editText_ReportDescriptionValue);
            s_rsv = findViewById(R.id.spinner_ReportStatusValue);
            s_rtv = findViewById(R.id.spinner_ReportTypeValue);


            String[] statuses = {"Open","Processing","Closed"};
            String[] types = {"Painting","Electricity","Masonry","Carpentry"};

            ArrayAdapter<String> statusSpinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,statuses);
            statusSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s_rsv.setAdapter(statusSpinnerArrayAdapter);

            ArrayAdapter<String> typeSpinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,types);
            typeSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s_rtv.setAdapter(typeSpinnerArrayAdapter);

            switch (type){
                case "new":
                    setTitle("New issue");
                    break;
                case "edit":
                    setTitle("Edit issue");
                    break;
            }
        }

    }
    public void deleteReport(View view){
        Toast.makeText(this, "deleteReport", Toast.LENGTH_SHORT).show();
    }
    public void saveReport(View view){
        String status = s_rsv.getSelectedItem().toString();
        String type = s_rtv.getSelectedItem().toString();
        Toast.makeText(this, "saveReport(" + status + ", " + type + ")", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        setResult(RESULT_OK);
        finish();
    }
}
