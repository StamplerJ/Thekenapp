package com.jann_luellmann.thekenapp.view

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.evrencoskun.tableview.listener.ITableViewListener
import com.jann_luellmann.thekenapp.OnCustomerClickedListener
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinksAndCustomers
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventAndCustomerWithDrinksViewModel
import com.jann_luellmann.thekenapp.dialog.AddDrinkDialogFragment

class ListTableViewClickListener(
    val context: Context,
    val onCustomerClickedListener: OnCustomerClickedListener
) : ITableViewListener {
    override fun onCellClicked(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {
        Toast.makeText(context, "Cell", Toast.LENGTH_SHORT).show()
    }

    override fun onCellDoubleClicked(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {
        Toast.makeText(context, "Cell", Toast.LENGTH_SHORT).show()
    }

    override fun onCellLongPressed(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {
        Toast.makeText(context, "Cell", Toast.LENGTH_SHORT).show()
    }

    override fun onColumnHeaderClicked(columnHeaderView: RecyclerView.ViewHolder, column: Int) {
        Toast.makeText(context, "Cell", Toast.LENGTH_SHORT).show()
    }

    override fun onColumnHeaderDoubleClicked(
        columnHeaderView: RecyclerView.ViewHolder,
        column: Int
    ) {
        Toast.makeText(context, "Cell", Toast.LENGTH_SHORT).show()
    }

    override fun onColumnHeaderLongPressed(columnHeaderView: RecyclerView.ViewHolder, column: Int) {
        Toast.makeText(context, "Cell", Toast.LENGTH_SHORT).show()
    }

    override fun onRowHeaderClicked(rowHeaderView: RecyclerView.ViewHolder, row: Int) {
        Toast.makeText(context, "Cell $row", Toast.LENGTH_SHORT).show()
    }

    override fun onRowHeaderDoubleClicked(rowHeaderView: RecyclerView.ViewHolder, row: Int) {
        Toast.makeText(context, "Cell", Toast.LENGTH_SHORT).show()
    }

    override fun onRowHeaderLongPressed(rowHeaderView: RecyclerView.ViewHolder, row: Int) {
        Toast.makeText(context, "Cell", Toast.LENGTH_SHORT).show()
    }
}