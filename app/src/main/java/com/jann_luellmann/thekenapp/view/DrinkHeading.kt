package com.jann_luellmann.thekenapp.view

import android.content.Context
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import com.jann_luellmann.thekenapp.R
import com.jann_luellmann.thekenapp.data.model.Drink

class DrinkHeading(context: Context?, drinks: List<Drink>) : TableRow(context) {
    private var linearLayout: LinearLayout? = null
    private fun init() {
        inflate(context, R.layout.tablerow, this)
        linearLayout = findViewById(R.id.drinks_list)
    }

    init {
        init()
        for (drink in drinks) {
            val tv = TextView(getContext())
            tv.text = "${drink.name.toString()}\n${drink.price}"
            linearLayout!!.addView(tv)
        }
    }
}