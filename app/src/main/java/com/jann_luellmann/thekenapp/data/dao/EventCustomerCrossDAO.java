package com.jann_luellmann.thekenapp.data.dao;

import com.jann_luellmann.thekenapp.data.model.EventCustomerCrossRef;
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithCustomers;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface EventCustomerCrossDAO {

    @Transaction
    @Query("SELECT * FROM eventCustomerCrossRef")
    LiveData<List<EventCustomerCrossRef>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(EventCustomerCrossRef... eventCustomerCrossRef);

    @Delete
    void delete(EventCustomerCrossRef eventWithCustomers);
}
