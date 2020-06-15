package com.jann_luellmann.thekenapp.data.dao.relationship;

import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinksAndCustomers;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface EventWithDrinksAndCustomersDAO {

    @Transaction
    @Query("SELECT * FROM event")
    LiveData<List<EventWithDrinksAndCustomers>> getAll();

    @Transaction
    @Query("SELECT * FROM event WHERE event.name LIKE :name LIMIT 1")
    LiveData<EventWithDrinksAndCustomers> findByName(String name);

    @Transaction
    @Query("SELECT * FROM event WHERE eventId = :id LIMIT 1")
    LiveData<EventWithDrinksAndCustomers> findById(long id);
}
