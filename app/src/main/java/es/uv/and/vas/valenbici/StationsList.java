package es.uv.and.vas.valenbici;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class StationsList extends AppCompatActivity {

    ListView stationsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations_list);

        stationsList = (ListView)findViewById(R.id.ListView_Stations);
        stationsList.setAdapter(new AdapterStations(this));
    }

}
