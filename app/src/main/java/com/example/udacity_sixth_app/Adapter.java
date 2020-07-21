package com.example.udacity_sixth_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Adapter extends ArrayAdapter<Class> {


    public Adapter( Context context, List<Class> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView=convertView;

        if(listItemView==null) { listItemView = LayoutInflater.from(getContext()).
                inflate(R.layout.list_item, parent, false); }

       Class xclass = (Class) getItem(position);

        TextView titleView=listItemView.findViewById(R.id.title);
        titleView.setText(xclass.getMtitle());

        TextView sectionView=listItemView.findViewById(R.id.section);
        sectionView.setText(xclass.getMsection());

        TextView authorView=listItemView.findViewById(R.id.author);
        authorView.setText(xclass.getMauthor());

        Date dateObject = new Date(xclass.getmTimeInMilliseconds());
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        String formattedDate = formatDate(dateObject);
        dateView.setText(formattedDate);
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        timeView.setText(formattedTime);

        return listItemView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
