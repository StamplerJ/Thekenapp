package com.jann_luellmann.thekenapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jann_luellmann.thekenapp.data.model.Event;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EventSpinnerAdapter extends ArrayAdapter<Event> {

    private Context context;
    private List<Event> events;

    public EventSpinnerAdapter(@NonNull Context context, @NonNull List<Event> events) {
        super(context, android.R.layout.simple_spinner_item, events);

        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(android.R.layout.simple_spinner_item,parent,false);
        }

        Event event = this.events.get(position);

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setTextColor(Color.BLACK);
        textView.setText(event.getName());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(android.R.layout.simple_spinner_dropdown_item,parent,false);
        }

        Event event = this.events.get(position);

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setTextColor(Color.BLACK);
        textView.setText(event.getName());

        return convertView;
    }
}
