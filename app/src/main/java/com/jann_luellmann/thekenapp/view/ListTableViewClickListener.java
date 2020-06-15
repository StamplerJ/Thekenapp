package com.jann_luellmann.thekenapp.view;

import android.content.Context;
import android.widget.Toast;

import com.evrencoskun.tableview.listener.ITableViewListener;
import com.jann_luellmann.thekenapp.data.model.Customer;
import com.jann_luellmann.thekenapp.data.model.relationship.CustomerWithBought;
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinksAndCustomers;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ListTableViewClickListener implements ITableViewListener {

    private Context context;

    private EventWithDrinksAndCustomers event;

    @Override
    public void onCellClicked(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {
        Toast.makeText(context, "Cell", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCellDoubleClicked(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {
        Toast.makeText(context, "Cell", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCellLongPressed(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {
        Toast.makeText(context, "Cell", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onColumnHeaderClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {
        Toast.makeText(context, "Cell", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onColumnHeaderDoubleClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {
        Toast.makeText(context, "Cell", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onColumnHeaderLongPressed(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {
        Toast.makeText(context, "Cell", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRowHeaderClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {
        CustomerWithBought customer = this.event.getCustomerWithBoughts().get(row);
        Toast.makeText(this.context, customer.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRowHeaderDoubleClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {
        Toast.makeText(context, "Cell", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRowHeaderLongPressed(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {
        Toast.makeText(context, "Cell", Toast.LENGTH_SHORT).show();
    }
}
