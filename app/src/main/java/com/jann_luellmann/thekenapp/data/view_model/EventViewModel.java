package com.jann_luellmann.thekenapp.data.view_model;

import com.jann_luellmann.thekenapp.data.model.Event;

import java.util.List;

import androidx.lifecycle.LiveData;

public class EventViewModel extends BaseViewModel<Event> {

    public EventViewModel() {
        super();
    }

    public void insert(Event event) {
        executor.execute(() -> db.eventDAO().insertAll(event));
    }

    public void insert(List<Event> events) {
        executor.execute(() -> db.eventDAO().insertAll(events.toArray(new Event[0])));
    }

    public void update(Event event) {
        executor.execute(() -> db.eventDAO().update(event));
    }

    @Override
    public LiveData<List<Event>> findAll() {
        return db.eventDAO().getAll();
    }

    @Override
    public void insert(long eventId, Event event) {
        insert(event);
    }

    @Override
    public void delete(Event event) {
        executor.execute(() -> db.eventDAO().delete(event));
    }
}
