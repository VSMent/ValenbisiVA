package es.uv.and.vas.valenbisi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import uk.me.jstott.jcoord.UTMRef;

public class AdapterStations extends BaseAdapter {

    // global variables
    DBHelper dbHelper;
    SQLiteDatabase database;
    class ViewHolder{
        TextView vhaddress;
        TextView vhid;
        TextView vhreports;
        ViewHolder(View v){
            vhaddress = v.findViewById(R.id.textView_StationRow_Address);
            vhid = v.findViewById(R.id.textView_StationRow_Id);
            vhreports = v.findViewById(R.id.textView_StationRow_Reports);
        }
    }
    class StationsComparator implements Comparator<Station> {
        @Override
        public int compare(Station s1, Station s2) {
            int n1 = s1.properties.number;
            int n2 = s2.properties.number;
            if(n1 > n2){
                return 1;
            }else if(n2 > n1){
                return -1;
            }
            return 0;
        }
    }
    public static ArrayList<Station> stations;
    Context context;

    AdapterStations(Context c){
        dbHelper = new DBHelper(c);
        database = dbHelper.getReadableDatabase();
        context = c;
        Init();
    }

    public void Init(){
        // global variables for Init scope
        String jsonString;
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        stations = new ArrayList<>();

        // read file to writer -> jsonString
        InputStream is = context.getResources().openRawResource(R.raw.valenbisi);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];

        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        jsonString = writer.toString();

        // json array of features from jsonString
        try{
            jsonObject = new JSONObject(jsonString);
            jsonArray = new JSONArray(jsonObject.get("features").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // clone data from jsonArray to stations array
        if(jsonArray != null){
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject obj = jsonArray.getJSONObject(i);

                    // coordinates conversion
                    double[] coordinates = {
                            obj.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0),
                            obj.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1)
                    };

                    UTMRef utm = new UTMRef(coordinates[0], coordinates[1], 'N', 30);
                    coordinates[0] = utm.toLatLng().getLat();
                    coordinates[1] = utm.toLatLng().getLng();

                    stations.add(new Station(
                            new Properties(
                                    obj.getJSONObject("properties").getString("name"),
                                    obj.getJSONObject("properties").getInt("number"),
                                    obj.getJSONObject("properties").getString("address"),
                                    obj.getJSONObject("properties").getString("open"),
                                    obj.getJSONObject("properties").getInt("available"),
                                    obj.getJSONObject("properties").getInt("free"),
                                    obj.getJSONObject("properties").getInt("total"),
                                    obj.getJSONObject("properties").getString("ticket"),
                                    obj.getJSONObject("properties").getString("updated_at")
                            ),
                            new Geometry(
                                    obj.getJSONObject("geometry").getString("type"),
                                    coordinates
                            )
                    ));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Collections.sort(stations,new StationsComparator());
        }
    }

    @Override
    public int getCount() {
        return stations.size();
    }

    @Override
    public Object getItem(int position) {
        return stations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.station_list_row, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        }else {
            holder = (ViewHolder) row.getTag();
        }

        holder.vhaddress.setText(stations.get(position).properties.address);
        holder.vhid.setText("#" + stations.get(position).properties.number);

        Cursor cursor = database.query(DBHelper.TABLE_NAME, null, DBHelper.KEY_STATION + "=" + stations.get(position).properties.number, null, null, null, null);
        holder.vhreports.setText(cursor.getCount() + " reports");
        cursor.close();

        return row;
    }
}

