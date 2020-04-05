package com.jann_luellmann.thekenapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jann_luellmann.thekenapp.data.db.Database;
import com.jann_luellmann.thekenapp.data.model.Customer;
import com.jann_luellmann.thekenapp.data.model.Drink;
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithCustomers;
import com.jann_luellmann.thekenapp.data.repository.CustomerRepository;
import com.jann_luellmann.thekenapp.data.repository.DrinkRepository;
import com.jann_luellmann.thekenapp.data.repository.relationship.EventWithCustomersRepository;
import com.jann_luellmann.thekenapp.view.CustomerRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ListFragment extends Fragment {

    private OnFragmentInteractionListener listener;

    private TableLayout tableLayout;
    private TableRow tableRowPrefab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        tableLayout = view.findViewById(R.id.tableLayout);
        tableRowPrefab = (TableRow) getLayoutInflater().inflate(R.layout.tablerow, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new EventWithCustomersRepository().findByName("Schützenfest").observe(this, eventWithCustomers -> {
            for (Customer customer : eventWithCustomers.getCustomers()) {
                CustomerRow row = new CustomerRow(getContext());
                row.setText(customer.getName());
                tableLayout.addView(row);
            }
        });

        new DrinkRepository().findAll().observe(this, drinks -> {
            for (int i = 0; i < tableLayout.getChildCount(); i++) {
                View v = tableLayout.getChildAt(i);
                if(v instanceof CustomerRow) {
                    CustomerRow row = (CustomerRow) v;
                    for (Drink drink : drinks) {
                        TextView textView = new TextView(getContext());
                        textView.setText(drink.getName());
                        row.addView(textView);
                    }
                }
            }
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
