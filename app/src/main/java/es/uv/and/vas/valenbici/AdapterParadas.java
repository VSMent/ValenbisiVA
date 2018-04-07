package es.uv.and.vas.valenbici;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

public class AdapterParadas extends BaseAdapter {
    private ArrayList<Parada> paradas;
    Context context;
    static class ViewHolder {
        TextView number;
        TextView address;
        TextView partes;
    }
    public AdapterParadas(Context c)
    {
        context=c;
        Init();
    }
    public void Init() {
        paradas=new ArrayList<Parada>();
        InputStream is = context.getResources().openRawResource(R.raw.paradasvalenbici);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //The String writer.toString() must be parsed in the bike stations
        // ArrayList by using JSONArray and JSONObject
    }
    @Override
    public int getCount() {
        return paradas.size();
    }
    @Override
    public Object getItem(int position) {
        return paradas.get(position);
    }
    @Override
    public long getItemId(int position) {
        return paradas.get(position).number;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // We use the ViewHolder pattern to store the views of every list element to
        // display them faster when the list is moved.
        View v = convertView ;
        ViewHolder holder = null;
        if ( v == null ) {
            // If is null, we create it from “layout”
            LayoutInflater li = (LayoutInflater)context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            v = li.inflate (R.layout.listparadaview , null ) ;
            holder = new ViewHolder ( ) ;
            holder.number = ( TextView ) v.findViewById (R.id.paradaviewnumber ) ;
            holder.address = ( TextView ) v.findViewById(R.id.paradaviewaddress ) ;
            holder.partes = ( TextView ) v.findViewById(R.id.paradaviewpartes ) ;
            v.setTag ( holder ) ;
        } else {
            // If is not null we re-use it to update it.
            holder = ( ViewHolder ) v.getTag ( ) ;
        }
        //Fill “holder” with the bike station information which is in the
        //position “position” of the ArrayList.
    }
