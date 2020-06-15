package com.jann_luellmann.thekenapp.data.view_model.relationship;

import com.jann_luellmann.thekenapp.data.model.Event;
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithCustomers;
import com.jann_luellmann.thekenapp.data.view_model.BaseViewModel;

import java.util.List;

import androidx.lifecycle.LiveData;

public class EventWithCustomersViewModel extends BaseViewModel<EventWithCustomers> {

    public EventWithCustomersViewModel() {
        super();
    }

    public LiveData<List<EventWithCustomers>> findAll() {
        return db.eventWithCustomersDAO().getAll();
    }

    public LiveData<EventWithCustomers> findByName(String name) {
        return db.eventWithCustomersDAO().findByName(name);
    }

    public LiveData<EventWithCustomers> findByEvent(Event event) {
        return db.eventWithCustomersDAO().findByName(event.getName());
    }

    @Override
    public void insert(long eventId, EventWithCustomers eventWithCustomers) {
        //noi
    }
}
