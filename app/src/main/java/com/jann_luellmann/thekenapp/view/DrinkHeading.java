package com.jann_luellmann.thekenapp.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jann_luellmann.thekenapp.R;
import com.jann_luellmann.thekenapp.data.model.Drink;

import java.util.List;

public class DrinkHeading extends TableRow {

    private LinearLayout linearLayout;

    public DrinkHeading(Context context, List<Drink> drinks) {
        super(context);
        init();

        for (Drink drink : drinks) {
            TextView tv = new TextView(getContext());
            tv.setText(drink.getName() + "\n" + drink.getPrice());
            linearLayout.addView(tv);
        }
    }

    private void init() {
        inflate(getContext(), R.layout.tablerow, this);
        linearLayout = findViewById(R.id.drinks_list);
    }
}
