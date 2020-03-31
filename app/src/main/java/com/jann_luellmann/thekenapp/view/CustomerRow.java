package com.jann_luellmann.thekenapp.view;

import android.content.Context;
import android.widget.TableRow;
import android.widget.TextView;

import com.jann_luellmann.thekenapp.R;

public class CustomerRow extends TableRow {

    private TextView textview;

    public CustomerRow(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.tablerow, this);
        textview = findViewById(R.id.customer);
    }

    public void setText(String text) {
        textview.setText(text);
    }
}
