package com.example.damian.earthquakemonitor;

/**
 * Created by Damian on 09/12/2014.
 */
import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;

    public ListViewAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView magnitude_row_text;
        TextView place_of_earthquake_row_text;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_row, parent, false);
        HashMap<String, String> resultp = new HashMap();
        resultp = data.get(position);

        float magnitude = Float.parseFloat(resultp.get(MainActivity.TAG_MAGNITUDE));
        String location = resultp.get(MainActivity.TAG_PLACE);
        magnitude_row_text = (TextView) itemView.findViewById(R.id.magnitude_row_text);
        place_of_earthquake_row_text = (TextView) itemView.findViewById(R.id.place_of_earthquake_row_text);
        magnitude_row_text.setText("Magnitude: " + magnitude);
        place_of_earthquake_row_text.setText("Location: " + location);

        if (magnitude < 1.0){
            itemView.setBackgroundColor(itemView.getResources().getColor(R.color.green));
        }else if(magnitude < 3.0){
            itemView.setBackgroundColor(itemView.getResources().getColor(R.color.orange));
        }else if(magnitude < 6.0){
            itemView.setBackgroundColor(itemView.getResources().getColor(R.color.darkorange));
        }else if(magnitude < 9.0){
            itemView.setBackgroundColor(itemView.getResources().getColor(R.color.red));
        }else{
            itemView.setBackgroundColor(itemView.getResources().getColor(R.color.darkred));
        }

        return itemView;
    }
}