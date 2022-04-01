package com.jann_luellmann.thekenapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.jann_luellmann.thekenapp.R
import com.jann_luellmann.thekenapp.dialog.EditEntryDialogFragment

class TextAdapter<T>(
    private var fragmentManager: FragmentManager
) : RecyclerView.Adapter<TextAdapter.ViewHolder<T>>() {

    private var editButton: ImageButton? = null

    private val data: MutableList<T> = mutableListOf()
    var showEditButton: Boolean = true
        set(value) {
            editButton?.visibility = if(value) View.VISIBLE else View.GONE
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.text_entry, parent, false)
        editButton = v.findViewById<View>(R.id.editButton) as ImageButton
        return ViewHolder(v, fragmentManager)
    }

    fun setData(newData: List<T>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.updateData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder<T>(
        v: View,
        private val fragmentManager: FragmentManager
    ) : RecyclerView.ViewHolder(v) {

        private var item: T? = null
        private val textView: TextView = v.findViewById(R.id.textView)
        private val imageButton: ImageButton = v.findViewById(R.id.editButton)

        fun updateData(item: T) {
            this.item = item
            textView.text = item.toString()

            imageButton.setOnClickListener {
                EditEntryDialogFragment(item as Any).show(fragmentManager, "")
            }
        }
    }
}