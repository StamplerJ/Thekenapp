package com.jann_luellmann.thekenapp.data.repository.relationship;

import com.jann_luellmann.thekenapp.data.model.Event;
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithCustomers;
import com.jann_luellmann.thekenapp.data.repository.BaseRepository;

import java.util.List;

import androidx.lifecycle.LiveData;

public class EventWithCustomersRepository extends BaseRepository {

    public LiveData<List<EventWithCustomers>> findAll() {
        return db.eventWithCustomersDAO().getEventWithCustomers();
    }

    public LiveData<EventWithCustomers> findByName(String name) {
        return db.eventWithCustomersDAO().findByName(name);
    }

    public LiveData<EventWithCustomers> findByEvent(Event event) {
        return db.eventWithCustomersDAO().findByName(event.getName());
    }
}
