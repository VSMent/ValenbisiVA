package es.uv.and.vas.valenbisi;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class SingleRow{
    String sname;
    String sid;
    String samount;
    SingleRow(String name, String id, String amount){
        this.sname = name;
        this.sid = id;
        this.samount = amount;
    }
}

public class AdapterStations extends BaseAdapter {

    ArrayList<SingleRow> rows;
    Context context;

    AdapterStations(Context c){
        context = c;
        rows = new ArrayList<SingleRow>();
        Resources res = c.getResources();
        String[] snames = res.getStringArray(R.array.sname);
        String[] sids = res.getStringArray(R.array.sid);
        String[] samounts = res.getStringArray(R.array.samount);

        for (int i = 0; i < 15; i++) {
            rows.add(new SingleRow(snames[i],sids[i],samounts[i]));
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.station_row, parent, false);

        TextView stationName = row.findViewById(R.id.Station_name);
        TextView stationId = row.findViewById(R.id.Station_id);
        TextView stationAmount = row.findViewById(R.id.Station_bicycles);

        stationName.setText(rows.get(position).sname);
        stationId.setText("#" + rows.get(position).sid);
        stationAmount.setText(rows.get(position).samount + " bike(s) free");

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
////
//    @Override
//    public int getCount() {
//        // number of elements in the array
//        return stations.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        // object from array at index (position)
//        return stations.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        // row id which has data from array at position
//        return stations.get(position).number;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // view to present each row
//        // We use the ViewHolder pattern to store the views of every list element to
//        // display them faster when the list is moved.
//        View v = convertView;
//        ViewHolder holder = null;
//        if (v == null) {
//            // If is null, we create it from “layout”
//            LayoutInflater li = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
//            v = li.inflate(R.layout.listparadaview, null);
//            holder = new ViewHolder();
//            holder.number = (TextView) v.findViewById(R.id.paradaviewnumber);
//            holder.address = (TextView) v.findViewById(R.id.paradaviewaddress);
//            holder.partes = (TextView) v.findViewById(R.id.paradaviewpartes);
//            v.setTag(holder);
//        } else {
//            // If is not null we re-use it to update it.
//            holder = (ViewHolder) v.getTag();
//        }
//        //Fill “holder” with the bike station information which is in the
//        //position “position” of the ArrayList.
//    }
//}
