package com.jann_luellmann.thekenapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lombok.NoArgsConstructor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jann_luellmann.thekenapp.adapter.TextAdapter;
import com.jann_luellmann.thekenapp.data.model.Customer;
import com.jann_luellmann.thekenapp.data.model.Drink;
import com.jann_luellmann.thekenapp.data.model.Event;
import com.jann_luellmann.thekenapp.data.view_model.BaseViewModel;
import com.jann_luellmann.thekenapp.data.view_model.CustomerViewModel;
import com.jann_luellmann.thekenapp.data.view_model.DrinkViewModel;
import com.jann_luellmann.thekenapp.data.view_model.EventViewModel;

import java.util.List;

@NoArgsConstructor
public class SettingsFragment extends Fragment {

    private OnFragmentInteractionListener listener;

    private DrinkViewModel drinkViewModel;
    private CustomerViewModel customerViewModel;
    private EventViewModel eventViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drinkViewModel = ViewModelProviders.of(this).get(DrinkViewModel.class);
        generateRecyclerView(view, R.id.drinks_list, Drink.class, drinkViewModel);

        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        generateRecyclerView(view, R.id.customer_list, Customer.class, customerViewModel);

        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        generateRecyclerView(view, R.id.event_list, Event.class, eventViewModel);

    }

    private <clazz> void generateRecyclerView(View view, int layoutId, Class clazz, BaseViewModel viewModel) {

        RecyclerView recyclerView = view.findViewById(layoutId);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        TextAdapter<clazz> adapter = new TextAdapter<>();
        recyclerView.setAdapter(adapter);

        viewModel.findAll().observe(this, items -> adapter.setData((List<clazz>) items));
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
