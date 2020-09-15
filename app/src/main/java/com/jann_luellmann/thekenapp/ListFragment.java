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
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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
        tableView.setIgnoreSelectionColors(true);
        tableView.setHasFixedWidth(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.adapter = new ListTableViewAdapter();
        this.tableView.setAdapter(adapter);
        this.adapter.setAllItems(columnHeaderList, rowHeaderList, cellList);

        long eventId = Prefs.getLong(getContext(), Prefs.CURRENT_EVENT, 1L);
        eventWithDrinksAndCustomersViewModel = ViewModelProviders.of(this).get(EventWithDrinksAndCustomersViewModel.class);
        onEventUpdated(eventId);
    }

    @Override
    public void onEventUpdated(long eventId) {
        if(eventWithDrinksAndCustomersViewModel == null) {
            if(isAdded()) {
                eventWithDrinksAndCustomersViewModel = ViewModelProviders.of(this).get(EventWithDrinksAndCustomersViewModel.class);
            }
            else {
                return;
            }
        }

        eventWithDrinksAndCustomersViewModel.findById(eventId).observe(this, event -> {
            this.columnHeaderList.clear();
            this.rowHeaderList.clear();
            this.cellList.clear();

            setData(event);

            this.adapter.setAllItems(columnHeaderList, rowHeaderList, cellList);
            this.adapter.notifyDataSetChanged();
            this.tableView.setTableViewListener(new ListTableViewClickListener(getContext(), event));
        });
    }

    private void setData(EventWithDrinksAndCustomers event) {
        if(event == null)
            return;

        for (Drink drink : event.getDrinks()) {
            columnHeaderList.add(new ColumnHeader(drink.toString()));
        }

        columnHeaderList.add(new ColumnHeader(getString(R.string.total)));

        float total = 0f;

        Collections.sort(event.getCustomerWithBoughts());
        for (CustomerWithBought customer : event.getCustomerWithBoughts()) {
            rowHeaderList.add(new RowHeader(customer.getCustomer().toString()));

            float sum = 0f;

            List<Cell> row = new ArrayList<>();
            for (Drink drink : event.getDrinks()) {
                boolean isBoughtPresent = false;
                for (Bought bought : customer.getBoughtsByEvent(event.getEvent().getEventId())) {
                    if(bought.getDrinkId() == drink.getDrinkId()) {
                        sum += bought.getAmount() * drink.getPrice();
                        row.add(new Cell(bought.getAmount()));
                        isBoughtPresent = true;
                        break;
                    }
                }
                if(!isBoughtPresent) {
                    row.add(new Cell(0));
                }
            }

            total += sum;
            row.add(new Cell(String.format(Locale.GERMAN, "%.2f€", (sum / 100f))));
            cellList.add(row);
        }

        rowHeaderList.add(new RowHeader(getString(R.string.total)));

        // Add total sum of all customers
        List<Cell> lastRow = new ArrayList<>();
        for (int i = 0; i < event.getDrinks().size(); i++) {
            lastRow.add(new Cell(""));
        }
        lastRow.add(new Cell(String.format(Locale.GERMAN, "%.2f€", (total / 100f))));

        cellList.add(lastRow);
    }
}
