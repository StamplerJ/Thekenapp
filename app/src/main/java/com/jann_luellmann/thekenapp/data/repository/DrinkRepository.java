package com.jann_luellmann.thekenapp.data.repository;

import com.jann_luellmann.thekenapp.data.model.Customer;
import com.jann_luellmann.thekenapp.data.model.Drink;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DrinkRepository extends BaseRepository {

    public void insert(Drink drink) {
        executor.execute(() -> db.drinkDAO().insertAll(drink));
    }

    public void insert(List<Drink> drinks) {
        executor.execute(() -> db.drinkDAO().insertAll(drinks.toArray(new Drink[0])));
    }

    public LiveData<List<Drink>> findAll() {
        return db.drinkDAO().getAll();
    }
}
