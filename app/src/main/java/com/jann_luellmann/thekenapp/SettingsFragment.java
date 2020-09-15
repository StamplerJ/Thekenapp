package com.jann_luellmann.thekenapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jann_luellmann.thekenapp.adapter.TextAdapter;
import com.jann_luellmann.thekenapp.data.model.Customer;
import com.jann_luellmann.thekenapp.data.model.Drink;
import com.jann_luellmann.thekenapp.data.model.Event;
import com.jann_luellmann.thekenapp.data.model.relationship.CustomerWithBought;
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinksAndCustomers;
import com.jann_luellmann.thekenapp.data.view_model.EventViewModel;
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventWithDrinksAndCustomersViewModel;
import com.jann_luellmann.thekenapp.databinding.FragmentSettingsBinding;
import com.jann_luellmann.thekenapp.dialog.CreateEntryDialogFragment;
import com.jann_luellmann.thekenapp.util.Prefs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SettingsFragment extends Fragment implements EventChangedListener {

    private FragmentManager fragmentManager;
    private FragmentSettingsBinding binding;

    private EventViewModel eventViewModel;
    private EventWithDrinksAndCustomersViewModel eventWithDrinksAndCustomersViewModel;

    private List<Drink> drinks = new ArrayList<>();
    private List<CustomerWithBought> customers = new ArrayList<>();

    private LiveData<EventWithDrinksAndCustomers> observableData;

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

        setupRecyclerView(binding.drinksList, this.drinks, true);
        setupRecyclerView(binding.customerList, this.customers, true);

        binding.addDrink.setOnClickListener(v -> new CreateEntryDialogFragment<>(new Drink()).show(fragmentManager, getString(R.string.drink_tag)));
        binding.addCustomer.setOnClickListener(v -> new CreateEntryDialogFragment<>(new Customer()).show(fragmentManager, getString(R.string.customer_tag)));
        binding.addEvent.setOnClickListener(v -> new CreateEntryDialogFragment<>(new Event()).show(fragmentManager, getString(R.string.event_tag)));

        long eventId = Prefs.getLong(getContext(), Prefs.CURRENT_EVENT, 1L);
        onEventUpdated(eventId);

        // Event setup
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        eventViewModel.findAll().observe(this, events -> {
            setupRecyclerView(binding.eventList, events, true);
        });
    }

    private <type> void setupRecyclerView(RecyclerView recyclerView, List<type> items, boolean editable) {
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        TextAdapter<type> adapter = new TextAdapter<>(editable);
        recyclerView.setAdapter(adapter);

        adapter.setData(items, fragmentManager);
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

        if(observableData != null && observableData.hasObservers()) {
            this.observableData.removeObservers(this);
        }

        this.observableData = eventWithDrinksAndCustomersViewModel.findById(eventId);

        this.observableData.observe(this, event -> {
            if(event == null)
                return;

            Collections.sort(event.getCustomerWithBoughts());

            this.drinks.clear();
            this.drinks.addAll(event.getDrinks());
            binding.drinksList.getAdapter().notifyDataSetChanged();

            this.customers.clear();
            this.customers.addAll(event.getCustomerWithBoughts());
            binding.customerList.getAdapter().notifyDataSetChanged();
        });
    }
}
