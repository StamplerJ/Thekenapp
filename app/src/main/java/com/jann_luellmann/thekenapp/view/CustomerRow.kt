package com.jann_luellmann.thekenapp.view

import android.content.Context
import android.widget.TextView
import com.jann_luellmann.thekenapp.R
import com.jann_luellmann.thekenapp.data.model.Drink
import android.widget.LinearLayout
import com.jann_luellmann.thekenapp.view.ColumnHeader
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.TableRow
import com.jann_luellmann.thekenapp.view.ListTableViewAdapter.CellViewHolder
import com.jann_luellmann.thekenapp.view.ListTableViewAdapter.ColumnHeaderViewHolder
import com.jann_luellmann.thekenapp.view.ListTableViewAdapter.RowHeaderViewHolder
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinksAndCustomers
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import com.jann_luellmann.thekenapp.data.model.relationship.CustomerWithBought

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