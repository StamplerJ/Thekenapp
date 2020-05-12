package com.jann_luellmann.thekenapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.evrencoskun.tableview.TableView;
import com.jann_luellmann.thekenapp.data.model.Customer;
import com.jann_luellmann.thekenapp.data.model.Drink;
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinksAndCustomers;
import com.jann_luellmann.thekenapp.data.view_model.DrinkViewModel;
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventWithCustomersViewModel;
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventWithDrinksAndCustomersViewModel;
import com.jann_luellmann.thekenapp.view.Cell;
import com.jann_luellmann.thekenapp.view.ColumnHeader;
import com.jann_luellmann.thekenapp.view.CustomerRow;
import com.jann_luellmann.thekenapp.view.DrinkHeading;
import com.jann_luellmann.thekenapp.view.ListTableViewAdapter;
import com.jann_luellmann.thekenapp.view.RowHeader;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ListFragment extends Fragment {

    private OnFragmentInteractionListener listener;

    private TableView tableView;

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

        new EventWithDrinksAndCustomersViewModel().findByName("Schützenfest").observe(this, event -> {
            if(event == null)
                return;

            List<RowHeader> rowHeaderList = new ArrayList<>();
            List<ColumnHeader> columnHeaderList = new ArrayList<>();
            List<List<Cell>> cellList = new ArrayList<>();

            for (Drink drink : event.getDrinks()) {
                columnHeaderList.add(new ColumnHeader(drink));
            }

            for (Customer customer : event.getCustomers()) {
                rowHeaderList.add(new RowHeader(customer));
            }

            for (int row = 0; row < event.getCustomers().size(); row++) {
                List<Cell> r = new ArrayList<>();
                for (int col = 0; col < event.getDrinks().size(); col++) {
                    r.add(new Cell("Row: " + row + ", Col: " + col));
                }
                cellList.add(r);
            }

            ListTableViewAdapter adapter = new ListTableViewAdapter();

            this.tableView.setAdapter(adapter);
            adapter.setAllItems(columnHeaderList, rowHeaderList, cellList);
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (listener != null) {
            listener.onListInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onListInteraction();
    }
}
