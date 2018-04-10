package es.uv.and.vas.valenbisi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class StationDetails extends AppCompatActivity {

    Intent intent;
    Station st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_details);

        intent = getIntent();
        int pos = intent.getIntExtra("pos",-1);
        if(pos == -1){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }else{
            st = AdapterStations.stations.get(pos);

            TextView tv_n = findViewById(R.id.textView_NumberValue);
            TextView tv_ad = findViewById(R.id.textView_AddressValue);
            TextView tv_t = findViewById(R.id.textView_TotalValue);
            TextView tv_av = findViewById(R.id.textView_AvailableValue);
            TextView tv_f = findViewById(R.id.textView_FreeValue);
            TextView tv_c = findViewById(R.id.textView_CoordinatesValue);

            setTitle(st.properties.name);
            tv_n.setText(String.valueOf(st.properties.number));
            tv_ad.setText(st.properties.address);
            tv_t.setText(String.valueOf(st.properties.total));
            tv_av.setText(String.valueOf(st.properties.available));
            tv_f.setText(String.valueOf(st.properties.free));
            tv_c.setText(String.valueOf(st.geometry.coordinates[0]) + ", " + String.valueOf(st.geometry.coordinates[1]));
        }
    }

    public void showOnMap(View view){
        float latitud = st.geometry.coordinates[0];
        float longitud = st.geometry.coordinates[1];
//        String stationName = st.properties.name;
//
//        Uri gmmIntentUri;
//        gmmIntentUri = Uri.parse("geo:0,0?q="+latitud+","+longitud+"("+stationName+")");
//        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//        mapIntent.setPackage("com.google.android.apps.maps");
//        // If is installed in the application, the Google Maps activity is launched
//        if (mapIntent.resolveActivity(getPackageManager()) != null) {
//            startActivity(mapIntent);
//        }
        Toast.makeText(this, "Wrong coordinates:\n" + latitud + ", " + longitud, Toast.LENGTH_SHORT).show();
    }
    public void editDetails(View view){
        Toast.makeText(this, "Edit button click", Toast.LENGTH_SHORT).show();
    }
}
