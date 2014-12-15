package com.example.damian.earthquakemonitor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailText extends Fragment {

    TextView magnitude_text;
    TextView date_text;
    TextView time_text;
    TextView location_text;
    TextView depth_text;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_text, container, false);

        magnitude_text = (TextView) view.findViewById(R.id.magnitude_text);
        date_text = (TextView) view.findViewById(R.id.date_text);
        time_text = (TextView) view.findViewById(R.id.time_text);
        location_text = (TextView) view.findViewById(R.id.location_text);
        depth_text = (TextView) view.findViewById(R.id.depth_text);

        return view;
    }

    public void setValues(String magnitude, String date, String location, String depth){
        String dateString = new SimpleDateFormat("MM/dd/yyyy").format(new Date(Long.parseLong(date)));
        String timeString = new SimpleDateFormat("HH:mm:ss").format(new Date(Long.parseLong(date)));
        magnitude_text.setText(magnitude);
        date_text.setText(dateString);
        time_text.setText(timeString);
        location_text.setText(location);
        depth_text.setText(depth);
    }

}
