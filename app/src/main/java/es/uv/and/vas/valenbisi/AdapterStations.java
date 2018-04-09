package es.uv.and.vas.valenbisi;

import android.content.Context;
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

public class AdapterStations extends BaseAdapter {

    // global variables
    public static ArrayList<Station> stations;
    Context context;

    AdapterStations(Context c){
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
                                    new float[]{
                                            (float) obj.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0),
                                            (float) obj.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1)
                                    }
                            )
                    ));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
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

    class ViewHolder{
        TextView vhaddress;
        TextView vhid;
        TextView vhamount;
        ViewHolder(View v){
            vhaddress = v.findViewById(R.id.Station_address);
            vhid = v.findViewById(R.id.Station_id);
            vhamount = v.findViewById(R.id.Station_bicycles);
        }
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
        holder.vhamount.setText(stations.get(position).properties.free + " bike(s) free");

        return row;
    }
}

