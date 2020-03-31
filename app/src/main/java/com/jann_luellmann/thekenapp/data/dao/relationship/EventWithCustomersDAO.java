package com.jann_luellmann.thekenapp.data.dao.relationship;

import com.jann_luellmann.thekenapp.data.model.relationship.EventWithCustomers;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface EventWithCustomersDAO {

    @Transaction
    @Query("SELECT * FROM event")
    LiveData<List<EventWithCustomers>> getEventWithCustomers();
}
