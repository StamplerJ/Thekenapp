package com.jann_luellmann.thekenapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.jann_luellmann.thekenapp.adapter.TextAdapter;
import com.jann_luellmann.thekenapp.data.model.Customer;
import com.jann_luellmann.thekenapp.data.model.Drink;
import com.jann_luellmann.thekenapp.data.model.Event;
import com.jann_luellmann.thekenapp.data.view_model.EventViewModel;
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventWithDrinksAndCustomersViewModel;
import com.jann_luellmann.thekenapp.databinding.FragmentSettingsBinding;
import com.jann_luellmann.thekenapp.dialog.CreateEntryDialogFragment;
import com.jann_luellmann.thekenapp.util.Prefs;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SettingsFragment extends Fragment {

    private OnFragmentInteractionListener listener;
    private FragmentManager fragmentManager;
    private FragmentSettingsBinding binding;

    private EventViewModel eventViewModel;
    private EventWithDrinksAndCustomersViewModel eventWithDrinksAndCustomersViewModel;

    private List<Drink> drinks = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();

    private boolean isSpinnerInitialized = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentManager = getFragmentManager();

        long eventId = Prefs.getLong(getContext(), Prefs.CURRENT_EVENT, 1L);
        eventWithDrinksAndCustomersViewModel = ViewModelProviders.of(this).get(EventWithDrinksAndCustomersViewModel.class);
        eventWithDrinksAndCustomersViewModel.findById(eventId).observe(this, event -> {
            if(event == null)
                return;

            // Drink setup
            drinks.addAll(event.getDrinks());
            generateRecyclerView(binding.drinksList, Drink.class, drinks);
            binding.addDrink.setOnClickListener(v -> new CreateEntryDialogFragment<>(new Drink(eventId)).show(fragmentManager, getString(R.string.drink_tag)));

            // Customer setup
            customers.addAll(event.getCustomers());
            generateRecyclerView(binding.customerList, Customer.class, customers);
            binding.addCustomer.setOnClickListener(v -> new CreateEntryDialogFragment<>(new Customer(eventId)).show(fragmentManager, getString(R.string.customer_tag)));
        });

        // Event setup
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        eventViewModel.findAll().observe(this, events -> {
            generateRecyclerView(binding.eventList, Event.class, events);
            binding.addEvent.setOnClickListener(v -> new CreateEntryDialogFragment<>(new Event()).show(fragmentManager, getString(R.string.event_tag)));

            // Setup spinner
            this.binding.eventSpinner.setAdapter(new ArrayAdapter<Event>(getContext(), R.layout.support_simple_spinner_dropdown_item, events));
            long currentEventId = Prefs.getLong(getContext(), Prefs.CURRENT_EVENT, 1L);
            for (int i = 0; i < this.binding.eventSpinner.getCount(); i++) {
                Event event = (Event) this.binding.eventSpinner.getItemAtPosition(i);
                if(event.getId() == currentEventId) {
                    this.binding.eventSpinner.setSelection(i);
                    break;
                }
            }
        });

        // Spinner OnItemSelectedListener setup
        binding.eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!isSpinnerInitialized) {
                    isSpinnerInitialized = true;
                    return;
                }

                Object selectedItem = adapterView.getItemAtPosition(i);
                if(selectedItem instanceof Event) {
                    Event selectedEvent = (Event) selectedItem;
                    Prefs.putLong(getContext(), Prefs.CURRENT_EVENT, selectedEvent.getId());

                    updateData(selectedEvent.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private <clazz> void generateRecyclerView(RecyclerView recyclerView, Class clazz, List<clazz> items) {
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        TextAdapter<clazz> adapter = new TextAdapter<>();
        recyclerView.setAdapter(adapter);

        adapter.setData(items, fragmentManager);
    }

    private void updateData(long eventId) {
        eventWithDrinksAndCustomersViewModel.findById(eventId).observe(this, event -> {
            this.drinks.clear();
            this.drinks.addAll(event.getDrinks());
            binding.drinksList.getAdapter().notifyDataSetChanged();

            this.customers.clear();
            this.customers.addAll(event.getCustomers());
            binding.customerList.getAdapter().notifyDataSetChanged();
        });
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (listener != null) {
            listener.onSettingsInteraction();
        }
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
        void onSettingsInteraction();
    }
}
