package com.jann_luellmann.thekenapp.data.dao.relationship;

import com.jann_luellmann.thekenapp.data.model.relationship.EventWithCustomers;
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinks;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface EventWithDrinksDAO {

    @Transaction
    @Query("SELECT * FROM event")
    LiveData<List<EventWithDrinks>> getAll();

    @Transaction
    @Query("SELECT * FROM event WHERE name LIKE :name LIMIT 1")
    LiveData<EventWithDrinks> findByName(String name);
}
