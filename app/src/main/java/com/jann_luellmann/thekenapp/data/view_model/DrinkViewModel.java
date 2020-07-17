package com.jann_luellmann.thekenapp.data.view_model;

import com.jann_luellmann.thekenapp.data.model.Drink;
import com.jann_luellmann.thekenapp.data.model.EventDrinkCrossRef;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DrinkViewModel extends BaseViewModel<Drink> {

    public DrinkViewModel() {
        super();
    }

    public void insert(Drink drink) {
        executor.execute(() -> db.drinkDAO().insertAll(drink));
    }

    public void insert(List<Drink> drinks) {
        executor.execute(() -> db.drinkDAO().insertAll(drinks.toArray(new Drink[0])));
    }

    public void update(Drink drink) {
        executor.execute(() -> db.drinkDAO().update(drink));
    }

    public LiveData<List<Drink>> findAll() {
        return db.drinkDAO().getAll();
    }

    @Override
    public void insert(long eventId, Drink drink) {
        executor.execute(() -> {
            long id = db.drinkDAO().insert(drink);
            db.eventDrinkCrossDAO().insertAll(new EventDrinkCrossRef(eventId, id));
        });
    }

    @Override
    public void delete(Drink drink) {
        executor.execute(() -> db.drinkDAO().delete(drink));
    }
}
