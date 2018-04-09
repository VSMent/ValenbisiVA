package es.uv.and.vas.valenbisi;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

class SingleRow{
    String saddress;
    String sid;
    String samount;
    SingleRow(String address, String id, String amount){
        this.saddress = address;
        this.sid = id;
        this.samount = amount;
    }
}

public class AdapterStations extends BaseAdapter {

    ArrayList<SingleRow> rows;

    ArrayList<Station> stations;
    Context context;

    AdapterStations(Context c){
        context = c;
        Init();
    }

    public void Init(){
        String jsonString = "";

        stations = new ArrayList<Station>();
        InputStream is = context.getResources().openRawResource(R.raw.valenbisi);
//        InputStream is = context.getResources().openRawResource(R.raw.small);
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


        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try{
            jsonObject = new JSONObject(jsonString);
            jsonArray = new JSONArray(jsonObject.get("features").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        rows = new ArrayList<SingleRow>();
        Resources res = context.getResources();
        String[] saddresses = res.getStringArray(R.array.saddress);
        String[] sids = res.getStringArray(R.array.sid);
        String[] samounts = res.getStringArray(R.array.samount);

        for (int i = 0; i < 15; i++) {
            rows.add(new SingleRow(saddresses[i],sids[i],samounts[i]));
        }
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int position) {
        return rows.get(position);
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
            row = inflater.inflate(R.layout.station_row, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        }else {
            holder = (ViewHolder) row.getTag();
        }

        holder.vhaddress.setText(rows.get(position).saddress);
        holder.vhid.setText("#" + rows.get(position).sid);
        holder.vhamount.setText(rows.get(position).samount + " bike(s) free");

        return row;
    }
}


//public class AdapterStations extends BaseAdapter {
//    private ArrayList<Station> stations;
//    Context context;
//
//    static class ViewHolder {
//        TextView number;
//        TextView address;
//        TextView partes;
//    }
//
//    public AdapterStations(Context c) {
//        context = c;
////        Init();
//    }
//
////    public void Init() {
////        paradas = new ArrayList<Station>();
////        InputStream is = context.getResources().openRawResource(R.raw.paradasvalenbici);
////        Writer writer = new StringWriter();
////        char[] buffer = new char[1024];
////        try {
////            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
////            int n;
////            while ((n = reader.read(buffer)) != -1) {
////                writer.write(buffer, 0, n);
////            }
////        } catch (UnsupportedEncodingException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        } finally {
////            try {
////                is.close();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        }
////        //The String writer.toString() must be parsed in the bike stations
////        // ArrayList by using JSONArray and JSONObject
////    }

