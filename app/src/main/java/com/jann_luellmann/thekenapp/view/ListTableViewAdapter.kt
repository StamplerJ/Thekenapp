package com.jann_luellmann.thekenapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private val resizableViewHolders: MutableList<BaseViewHolder> = mutableListOf()

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
    }

    override fun onCreateColumnHeaderViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbstractViewHolder {
        val layout: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.table_view_column_header_layout, parent, false)
        val viewHolder = ColumnHeaderViewHolder(layout)
        resizableViewHolders.add(viewHolder)
        return viewHolder
    }

    override fun onBindColumnHeaderViewHolder(
        holder: AbstractViewHolder,
        columnHeaderItemModel: ColumnHeader?,
        columnPosition: Int
    ) {
        val columnHeaderViewHolder = holder as ColumnHeaderViewHolder
        columnHeaderViewHolder.columnHeaderTextView.text = columnHeaderItemModel!!.data.toString()
    }

    override fun onCreateRowHeaderViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        return when (viewType) {
            ROW_HEADER_TEXT -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.table_view_row_header_layout, parent, false)
                RowHeaderViewHolder(view)
            }
            else -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.table_view_customer_row_header_layout, parent, false)
                RowHeaderCustomerViewHolder(view)
            }
        }
    }

    override fun onBindRowHeaderViewHolder(
        holder: AbstractViewHolder,
        rowHeaderItemModel: RowHeader?,
        rowPosition: Int
    ) {
        rowHeaderItemModel?.let {
            if (holder is RowHeaderViewHolder) {
                holder.bind(rowHeaderItemModel)
                rowHeadWidth = holder.cellContainer.layoutParams.width
            } else if (holder is RowHeaderCustomerViewHolder) {
                holder.bind(it)
                rowHeadWidth = holder.cellContainer.layoutParams.width
            }
        }
    }

    override fun onCreateCornerView(parent: ViewGroup): View {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.table_view_corner_layout, parent, false)
    }

    override fun getColumnHeaderItemViewType(position: Int): Int {
        return 0
    }

    override fun getRowHeaderItemViewType(position: Int): Int {
        return when (mRowHeaderItems[position]) {
            is RowHeaderCustomer -> ROW_HEADER_CUSTOMER
            else -> ROW_HEADER_TEXT
        }
    }

    override fun getCellItemViewType(position: Int): Int {
        return 0
    }

    internal open inner class BaseViewHolder(itemView: View) : AbstractViewHolder(itemView) {
        val cellContainer: ConstraintLayout = itemView.findViewById(R.id.cell_container)
    }

    internal inner class CellViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val cellTextview: TextView = itemView.findViewById(R.id.cell_data)
    }

    internal inner class ColumnHeaderViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val columnHeaderTextView: TextView = itemView.findViewById(R.id.column_header_textView)
    }

    internal inner class RowHeaderViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private val rowHeaderTextView: TextView = itemView.findViewById(R.id.rowHeaderTextView)

        fun bind(rowHeaderItemModel: RowHeader) {
            rowHeaderTextView.text = rowHeaderItemModel.data.toString()
        }
    }

    internal inner class RowHeaderCustomerViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private val rowHeaderButton: TextView = itemView.findViewById(R.id.rowHeaderButton)

        fun bind(rowHeaderItemModel: RowHeader) {
            rowHeaderButton.text = rowHeaderItemModel.data.toString()
            rowHeaderButton.setOnClickListener {
                rowHeaderItemModel.onClick()
            }
        }
    }

    companion object {
        val ROW_HEADER_CUSTOMER = 0
        val ROW_HEADER_TEXT = 1
    }
}