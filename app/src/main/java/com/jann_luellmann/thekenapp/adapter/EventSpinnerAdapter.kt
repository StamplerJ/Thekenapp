package com.jann_luellmann.thekenapp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.jann_luellmann.thekenapp.data.model.Event

class EventSpinnerAdapter(
    context: Context,
    private val events: List<Event>
) : ArrayAdapter<Event>(context, android.R.layout.simple_spinner_item, events) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_spinner_item, parent, false)

        val event = events[position]
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.setTextColor(Color.BLACK)
        textView.text = event.name ?: "NO EVENT NAME"
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
                .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)

        val event = events[position]
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.setTextColor(Color.BLACK)
        textView.text = event.name ?: ""
        return view
    }
}