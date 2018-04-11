package es.uv.and.vas.valenbisi;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class AdapterReports extends CursorAdapter {

    AdapterReports(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.report_list_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
        int statusIndex = cursor.getColumnIndex(DBHelper.KEY_REPORT_STATUS);
        int nameIndex = cursor.getColumnIndex(DBHelper.KEY_REPORT_NAME);

        TextView id = view.findViewById(R.id.textView_ReportRow_Id);
        ImageView image = view.findViewById(R.id.imageView_ReportRow_Image);
        TextView name = view.findViewById(R.id.textView_ReportRow_Name);

        id.setText(cursor.getString(idIndex));
        switch (cursor.getString(statusIndex)) {
            case "Open":
                image.setImageResource(R.drawable.circle_red);
                break;
            case "Processing":
                image.setImageResource(R.drawable.circle_yellow);
                break;
            case "Closed":
                image.setImageResource(R.drawable.circle_green);
                break;
        }
        name.setText(cursor.getString(nameIndex));
    }
}

