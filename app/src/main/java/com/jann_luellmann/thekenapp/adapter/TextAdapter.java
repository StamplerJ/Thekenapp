package com.jann_luellmann.thekenapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jann_luellmann.thekenapp.R;
import com.jann_luellmann.thekenapp.dialog.EditEntryDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class TextAdapter<T> extends RecyclerView.Adapter<TextAdapter.ViewHolder> {

    private boolean showEditButton;
    private List<T> data;
    private FragmentManager fragmentManager;

    static class ViewHolder<T> extends RecyclerView.ViewHolder {

        T item;
        TextView textView;
        ImageButton imageButton;

        ViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.textView);
            imageButton = v.findViewById(R.id.editButton);
        }

        void updateData(T item, FragmentManager fragmentManager) {
            this.item = item;
            textView.setText(item.toString());
            imageButton.setOnClickListener(view -> new EditEntryDialogFragment(item).show(fragmentManager, ""));
        }
    }

    public TextAdapter(boolean showEditButton) {
        this.showEditButton = showEditButton;
        this.data = new ArrayList<T>();
    }

    @NonNull
    @Override
    public TextAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_entry, parent, false);

        if(!this.showEditButton) {
            v.findViewById(R.id.editButton).setVisibility(View.GONE);
        }

        return new TextAdapter.ViewHolder(v);
    }

    public void setData(List<T> newData, FragmentManager fragmentManager) {
        this.data = newData;
        this.fragmentManager = fragmentManager;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull TextAdapter.ViewHolder holder, int position) {
        holder.updateData(data.get(position), fragmentManager);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
