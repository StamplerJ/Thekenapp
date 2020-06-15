package com.jann_luellmann.thekenapp.data.view_model.relationship;

import com.jann_luellmann.thekenapp.data.model.Event;
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinks;
import com.jann_luellmann.thekenapp.data.view_model.BaseViewModel;

import java.util.List;

import androidx.lifecycle.LiveData;

public class EventWithDrinksViewModel extends BaseViewModel<EventWithDrinks> {

    public EventWithDrinksViewModel() {
        super();
    }

    public LiveData<List<EventWithDrinks>> findAll() {
        return db.eventWithDrinksDAO().getAll();
    }

    public LiveData<EventWithDrinks> findByName(String name) {
        return db.eventWithDrinksDAO().findByName(name);
    }

    public LiveData<EventWithDrinks> findByEvent(Event event) {
        return db.eventWithDrinksDAO().findByName(event.getName());
    }

    @Override
    public void insert(long eventId, EventWithDrinks eventWithDrinks) {
        //nop
    }
}
