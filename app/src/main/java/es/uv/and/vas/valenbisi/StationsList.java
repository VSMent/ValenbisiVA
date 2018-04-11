package es.uv.and.vas.valenbisi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class StationsList extends AppCompatActivity {

    ListView stationsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stations_list);

        stationsList = findViewById(R.id.ListView_Stations);
        stationsList.setAdapter(new AdapterStations(this));

        stationsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent("android.intent.action.StationDetails");
                intent.putExtra("stationId", position + 1);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        stationsList.setAdapter(new AdapterStations(this));
    }
}
