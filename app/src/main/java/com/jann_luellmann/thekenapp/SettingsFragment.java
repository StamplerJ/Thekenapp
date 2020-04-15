package com.jann_luellmann.thekenapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lombok.NoArgsConstructor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jann_luellmann.thekenapp.adapter.TextAdapter;
import com.jann_luellmann.thekenapp.data.model.Customer;
import com.jann_luellmann.thekenapp.data.model.Drink;
import com.jann_luellmann.thekenapp.data.model.Event;
import com.jann_luellmann.thekenapp.data.view_model.BaseViewModel;
import com.jann_luellmann.thekenapp.data.view_model.CustomerViewModel;
import com.jann_luellmann.thekenapp.data.view_model.DrinkViewModel;
import com.jann_luellmann.thekenapp.data.view_model.EventViewModel;
import com.jann_luellmann.thekenapp.databinding.DialogFragmentEditEntryBinding;
import com.jann_luellmann.thekenapp.databinding.FragmentSettingsBinding;
import com.jann_luellmann.thekenapp.dialog.CreateEntryDialogFragment;

import java.util.List;

@NoArgsConstructor
public class SettingsFragment extends Fragment {

    private OnFragmentInteractionListener listener;
    private FragmentManager fragmentManager;
    private FragmentSettingsBinding binding;

    private DrinkViewModel drinkViewModel;
    private CustomerViewModel customerViewModel;
    private EventViewModel eventViewModel;

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

        // Drink setup
        drinkViewModel = ViewModelProviders.of(this).get(DrinkViewModel.class);
        generateRecyclerView(view, binding.drinksList, Drink.class, drinkViewModel);
        binding.addDrink.setOnClickListener(v -> new CreateEntryDialogFragment<>(new Drink()).show(fragmentManager, getString(R.string.drink_tag)));

        // Customer setup
        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        generateRecyclerView(view, binding.customerList, Customer.class, customerViewModel);
        binding.addCustomer.setOnClickListener(v -> new CreateEntryDialogFragment<>(new Customer()).show(fragmentManager, getString(R.string.customer_tag)));

        // Event setup
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        generateRecyclerView(view, binding.eventList, Event.class, eventViewModel);
        binding.addEvent.setOnClickListener(v -> new CreateEntryDialogFragment<>(new Event()).show(fragmentManager, getString(R.string.event_tag)));
    }

    private <clazz> void generateRecyclerView(View view, RecyclerView recyclerView, Class clazz, BaseViewModel viewModel) {
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        TextAdapter<clazz> adapter = new TextAdapter<>();
        recyclerView.setAdapter(adapter);

        viewModel.findAll().observe(this, items -> adapter.setData((List<clazz>) items, fragmentManager));
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
