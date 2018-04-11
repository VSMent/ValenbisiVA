package es.uv.and.vas.valenbisi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class StationDetails extends AppCompatActivity {

    Intent intentRoot;
    Station st;
    static DBHelper dbHelper;
    ListView lv_r;
    int stationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_details);

        dbHelper = new DBHelper(this);
        intentRoot = getIntent();
        stationId = intentRoot.getIntExtra("stationId", -1);
        st = AdapterStations.stations.get(stationId - 1);

        TextView tv_n = findViewById(R.id.textView_NumberValue);
        TextView tv_ad = findViewById(R.id.textView_AddressValue);
        TextView tv_t = findViewById(R.id.textView_TotalValue);
        TextView tv_av = findViewById(R.id.textView_AvailableValue);
        TextView tv_f = findViewById(R.id.textView_FreeValue);
        TextView tv_c = findViewById(R.id.textView_CoordinatesValue);
        lv_r = findViewById(R.id.ListView_Reports);

        setTitle(st.properties.name);
        tv_n.setText(String.valueOf(st.properties.number));
        tv_ad.setText(st.properties.address);
        tv_t.setText(String.valueOf(st.properties.total));
        tv_av.setText(String.valueOf(st.properties.available));
        tv_f.setText(String.valueOf(st.properties.free));
        tv_c.setText(String.valueOf(st.geometry.coordinates[0]) + ", " + String.valueOf(st.geometry.coordinates[1]));

        lv_r.setAdapter(new AdapterReports(this, dbHelper.FindReportByBikeStation(stationId), 0));

        lv_r.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("android.intent.action.StationReport");
                intent.putExtra("stationId", st.properties.number);
                intent.putExtra("id", Integer.parseInt(((TextView) view.findViewById(R.id.textView_ReportRow_Id)).getText().toString()));
                startActivityForResult(intent, 1);
            }
        });

    }

    public void showOnMap(View view) {
        double latitud = st.geometry.coordinates[0];
        double longitud = st.geometry.coordinates[1];

        String stationName = st.properties.name;

        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + latitud + "," + longitud + "(" + stationName + ")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        // If is installed in the application, the Google Maps activity is launched
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    public void addReport(View view) {
        Intent intent = new Intent("android.intent.action.StationReport");
        intent.putExtra("stationId", st.properties.number);
        intent.putExtra("id", -1);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            lv_r.setAdapter(new AdapterReports(this, dbHelper.FindReportByBikeStation(stationId), 0));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
