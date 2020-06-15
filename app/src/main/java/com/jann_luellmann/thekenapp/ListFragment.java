package com.jann_luellmann.thekenapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evrencoskun.tableview.TableView;
import com.jann_luellmann.thekenapp.data.model.Bought;
import com.jann_luellmann.thekenapp.data.model.Customer;
import com.jann_luellmann.thekenapp.data.model.Drink;
import com.jann_luellmann.thekenapp.data.model.Event;
import com.jann_luellmann.thekenapp.data.model.relationship.CustomerWithBought;
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinksAndCustomers;
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventWithDrinksAndCustomersViewModel;
import com.jann_luellmann.thekenapp.util.Prefs;
import com.jann_luellmann.thekenapp.view.Cell;
import com.jann_luellmann.thekenapp.view.ColumnHeader;
import com.jann_luellmann.thekenapp.view.ListTableViewAdapter;
import com.jann_luellmann.thekenapp.view.ListTableViewClickListener;
import com.jann_luellmann.thekenapp.view.RowHeader;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ListFragment extends Fragment implements EventChangedListener {

    private TableView tableView;
    private ListTableViewAdapter adapter;

    private EventWithDrinksAndCustomersViewModel eventWithDrinksAndCustomersViewModel;

    private List<RowHeader> rowHeaderList = new ArrayList<>();
    private List<ColumnHeader> columnHeaderList = new ArrayList<>();
    private List<List<Cell>> cellList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        tableView = view.findViewById(R.id.tableView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        long eventId = Prefs.getLong(getContext(), Prefs.CURRENT_EVENT, 1L);
        eventWithDrinksAndCustomersViewModel = ViewModelProviders.of(this).get(EventWithDrinksAndCustomersViewModel.class);
        eventWithDrinksAndCustomersViewModel.findById(eventId).observe(this, event -> {
            if(event == null)
                return;

            setData(event);

            this.adapter = new ListTableViewAdapter();

            this.tableView.setAdapter(adapter);
            this.adapter.setAllItems(columnHeaderList, rowHeaderList, cellList);

            this.tableView.setTableViewListener(new ListTableViewClickListener(getContext(), event));
        });
    }

    @Override
    public void onEventUpdated(long eventId) {
        eventWithDrinksAndCustomersViewModel.findById(eventId).observe(this, event -> {
            this.columnHeaderList.clear();
            this.rowHeaderList.clear();
            this.cellList.clear();

            setData(event);

            this.adapter.setAllItems(columnHeaderList, rowHeaderList, cellList);
            this.adapter.notifyDataSetChanged();
        });
    }

    private void setData(EventWithDrinksAndCustomers event) {
        for (Drink drink : event.getDrinks()) {
            columnHeaderList.add(new ColumnHeader(drink.toString()));
        }

        for (CustomerWithBought customer : event.getCustomerWithBoughts()) {
            rowHeaderList.add(new RowHeader(customer.toString()));

            List<Cell> r = new ArrayList<>();
            for (Bought bought : customer.getBoughts()) {
                r.add(new Cell(bought.getAmount()));
            }
            cellList.add(r);
        }
    }
}
