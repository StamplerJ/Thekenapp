package com.jann_luellmann.thekenapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.evrencoskun.tableview.TableView
import com.evrencoskun.tableview.adapter.AbstractTableAdapter
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.jann_luellmann.thekenapp.R
import com.jann_luellmann.thekenapp.util.Util

class ListTableViewAdapter : AbstractTableAdapter<ColumnHeader?, RowHeader?, Cell?>() {

    var tableView: TableView? = null
    private var rowHeadWidth = 0

    override fun onCreateCellViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        val layout: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.table_view_cell_layout, parent, false)
        return CellViewHolder(layout)
    }

    override fun onBindCellViewHolder(
        holder: AbstractViewHolder,
        cellItemModel: Cell?,
        columnPosition: Int,
        rowPosition: Int
    ) {
        val viewHolder = holder as CellViewHolder
        viewHolder.cellTextview.text = cellItemModel!!.data.toString()

        // Resize
        viewHolder.cellContainer.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        viewHolder.cellTextview.requestLayout()
    }

    override fun onCreateColumnHeaderViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractViewHolder {
        val layout: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.table_view_column_header_layout, parent, false)
        return ColumnHeaderViewHolder(layout)
    }

    override fun onBindColumnHeaderViewHolder(
        holder: AbstractViewHolder,
        columnHeaderItemModel: ColumnHeader?,
        columnPosition: Int
    ) {
        val columnHeaderViewHolder = holder as ColumnHeaderViewHolder
        columnHeaderViewHolder.columnHeaderTextView.text = columnHeaderItemModel!!.data.toString()

        // Resize
        tableView?.let {
            val layoutParams = columnHeaderViewHolder.columnHeaderContainer.layoutParams
            val tableWidth = it.measuredWidth - Util.convertDpToPixel(
                columnHeaderViewHolder.columnHeaderContainer.context.resources.getDimension(R.dimen.row_header_width),
                columnHeaderViewHolder.columnHeaderContainer.context
            )
            layoutParams.width = tableWidth / mColumnHeaderItems.size
            columnHeaderViewHolder.columnHeaderContainer.layoutParams = layoutParams
        }
    }

    override fun onCreateRowHeaderViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        val layout: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.table_view_row_header_layout, parent, false)
        return RowHeaderViewHolder(layout)
    }

    override fun onBindRowHeaderViewHolder(
        holder: AbstractViewHolder,
        rowHeaderItemModel: RowHeader?,
        rowPosition: Int
    ) {
        val rowHeaderViewHolder = holder as RowHeaderViewHolder
        rowHeaderViewHolder.rowHeaderTextView.text = rowHeaderItemModel!!.data.toString()

        rowHeadWidth = rowHeaderViewHolder.rowHeaderContainer.layoutParams.width
    }

    override fun onCreateCornerView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.table_view_corner_layout, parent, false)
    }

    override fun getColumnHeaderItemViewType(position: Int): Int {
        return 0
    }

    override fun getRowHeaderItemViewType(position: Int): Int {
        return 0
    }

    override fun getCellItemViewType(position: Int): Int {
        return 0
    }

    internal inner class CellViewHolder(itemView: View) : AbstractViewHolder(itemView) {
        val cellContainer: LinearLayout = itemView.findViewById(R.id.cell_container)
        val cellTextview: TextView = itemView.findViewById(R.id.cell_data)
    }

    internal inner class ColumnHeaderViewHolder(itemView: View) : AbstractViewHolder(itemView) {
        val columnHeaderContainer: ConstraintLayout = itemView.findViewById(R.id.column_header_container)
        val columnHeaderTextView: TextView = itemView.findViewById(R.id.column_header_textView)
    }

    internal inner class RowHeaderViewHolder(itemView: View) : AbstractViewHolder(itemView) {
        val rowHeaderContainer: ConstraintLayout = itemView.findViewById(R.id.row_header_container)
        val rowHeaderTextView: TextView = itemView.findViewById(R.id.row_header_textView)
    }
}