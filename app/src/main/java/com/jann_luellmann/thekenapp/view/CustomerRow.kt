package com.jann_luellmann.thekenapp.view

import android.content.Context
import android.widget.TextView
import com.jann_luellmann.thekenapp.R
import android.widget.TableRow

class CustomerRow(context: Context?) : TableRow(context) {
    private var textview: TextView? = null
    private fun init() {
        inflate(context, R.layout.tablerow, this)
        textview = findViewById(R.id.customer)
    }

    fun setText(text: String?) {
        textview!!.text = text
    }

    init {
        init()
    }
}