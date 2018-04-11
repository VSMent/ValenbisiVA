package es.uv.and.vas.valenbisi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterReports extends BaseAdapter {

    // global variables
    class ViewHolder {
        TextView vhname;
        ImageView vhimage;

        ViewHolder(View v) {
            vhname = v.findViewById(R.id.textView_ReportRow_Name);
            vhimage = v.findViewById(R.id.imageView_ReportRow_Image);
        }
    }

    private ArrayList<String[]> reports;
    Context context;
    int stationId;

    AdapterReports(Context c, int s) {
        context = c;
        stationId = s;
        Init();
    }

    public void Init() {
        reports = new ArrayList<>();

        // get data from sql
        DBHelper dbHelper;
        SQLiteDatabase database;

        dbHelper = StationDetails.dbHelper;
        database = dbHelper.getReadableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_NAME,
                new String[]{DBHelper.KEY_REPORT_STATUS, DBHelper.KEY_REPORT_NAME},
                DBHelper.KEY_STATION + "=" + stationId,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            int statusIndex = cursor.getColumnIndex(DBHelper.KEY_REPORT_STATUS);
            int nameIndex = cursor.getColumnIndex(DBHelper.KEY_REPORT_NAME);

            do {
                reports.add(new String[]{cursor.getString(statusIndex), cursor.getString(nameIndex)});
            } while (cursor.moveToNext());
        }
        cursor.close();
        dbHelper.close();
    }

    @Override
    public int getCount() {
        return reports.size();
    }

    @Override
    public Object getItem(int position) {
        return reports.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.report_list_row, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        switch (reports.get(position)[0]) {
            case "Open":
                holder.vhimage.setImageResource(R.drawable.circle_red);
                break;
            case "Processing":
                holder.vhimage.setImageResource(R.drawable.circle_yellow);
                break;
            case "Closed":
                holder.vhimage.setImageResource(R.drawable.circle_green);
                break;
        }
        holder.vhname.setText(reports.get(position)[1]);

        return row;
    }
}

