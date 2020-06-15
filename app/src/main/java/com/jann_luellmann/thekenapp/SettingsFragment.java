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
public class SettingsFragment extends Fragment implements EventChangedListener {

    private FragmentManager fragmentManager;
    private FragmentSettingsBinding binding;

    private EventViewModel eventViewModel;
    private EventWithDrinksAndCustomersViewModel eventWithDrinksAndCustomersViewModel;

    private List<Drink> drinks = new ArrayList<>();
    private List<CustomerWithBought> customers = new ArrayList<>();

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
            binding.addDrink.setOnClickListener(v -> new CreateEntryDialogFragment<>(new Drink()).show(fragmentManager, getString(R.string.drink_tag)));

            // Customer setup
            customers.addAll(event.getCustomerWithBoughts());
            generateRecyclerView(binding.customerList, Customer.class, customers);
            binding.addCustomer.setOnClickListener(v -> new CreateEntryDialogFragment<>(new Customer()).show(fragmentManager, getString(R.string.customer_tag)));
        });

        // Event setup
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        eventViewModel.findAll().observe(this, events -> {
            generateRecyclerView(binding.eventList, Event.class, events);
            binding.addEvent.setOnClickListener(v -> new CreateEntryDialogFragment<>(new Event()).show(fragmentManager, getString(R.string.event_tag)));
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

    @Override
    public void onEventUpdated(long eventId) {
        eventWithDrinksAndCustomersViewModel.findById(eventId).observe(this, event -> {
            this.drinks.clear();
            this.drinks.addAll(event.getDrinks());
            binding.drinksList.getAdapter().notifyDataSetChanged();

            this.customers.clear();
            this.customers.addAll(event.getCustomerWithBoughts());
            binding.customerList.getAdapter().notifyDataSetChanged();
        });
    }
}
