package com.jann_luellmann.thekenapp.data.view_model.relationship;

import com.jann_luellmann.thekenapp.data.model.Event;
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinks;
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinksAndCustomers;
import com.jann_luellmann.thekenapp.data.view_model.BaseViewModel;

import java.util.List;

import androidx.lifecycle.LiveData;

public class EventWithDrinksAndCustomersViewModel extends BaseViewModel<EventWithDrinksAndCustomers> {

    public EventWithDrinksAndCustomersViewModel() {
        super();
    }

    public LiveData<List<EventWithDrinksAndCustomers>> findAll() {
        return db.eventWithDrinksAndCustomersDAO().getAll();
    }

    public LiveData<EventWithDrinksAndCustomers> findById(long id) {
        return db.eventWithDrinksAndCustomersDAO().findById(id);
    }

    public LiveData<EventWithDrinksAndCustomers> findByName(String name) {
        return db.eventWithDrinksAndCustomersDAO().findByName(name);
    }

    public LiveData<EventWithDrinksAndCustomers> findByEvent(Event event) {
        return db.eventWithDrinksAndCustomersDAO().findByName(event.getName());
    }

    @Override
    public void insert(long eventId, EventWithDrinksAndCustomers event) {
        //nop
    }

    @Override
    public void delete(EventWithDrinksAndCustomers eventWithDrinksAndCustomers) {
        //nop
    }
}
