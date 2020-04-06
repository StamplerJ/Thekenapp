package com.jann_luellmann.thekenapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jann_luellmann.thekenapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TextAdapter<T> extends RecyclerView.Adapter<TextAdapter.ViewHolder> {

    private List<T> data;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageButton imageButton;

        public ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textView);
            imageButton = v.findViewById(R.id.imageButton);
        }
    }

    public TextAdapter() {
        this.data = new ArrayList<T>();
    }

    public TextAdapter(List<T> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TextAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_entry, parent, false);
        return new TextAdapter.ViewHolder(v);
    }

    public void setData(List<T> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull TextAdapter.ViewHolder holder, int position) {
        holder.textView.setText(data.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
