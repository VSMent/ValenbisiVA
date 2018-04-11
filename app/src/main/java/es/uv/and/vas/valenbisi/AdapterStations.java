package es.uv.and.vas.valenbisi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

public class AdapterStations extends BaseAdapter {

    // global variables
    private HTTPConnector httpConnector;
    private DBHelper dbHelper;
    public static ArrayList<Station> stations;
    private Context context;
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

    AdapterStations(Context c){
        httpConnector = new HTTPConnector();
        dbHelper = new DBHelper(c);
        context = c;
        Init();
    }

    private void Init(){
        // global variables for Init scope
        try {
            stations =  httpConnector.execute().get();

            Collections.sort(stations,new StationsComparator());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
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
        holder.vhreports.setText(dbHelper.ReportsAmountForStation(stations.get(position).properties.number) + " reports");

        return row;
    }
}

