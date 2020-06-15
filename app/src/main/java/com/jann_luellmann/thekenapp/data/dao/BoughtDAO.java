package com.jann_luellmann.thekenapp.data.dao;

import com.jann_luellmann.thekenapp.data.model.Bought;
import com.jann_luellmann.thekenapp.data.model.Customer;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class BoughtDAO {

    @Query("SELECT * FROM bought")
    public abstract LiveData<List<Bought>> getAll();

    @Query("SELECT * FROM bought WHERE customerId IN (:customerIds)")
    public abstract LiveData<List<Bought>> findAllByCustomerIds(long[] customerIds);

    @Transaction
    public void insert(long customerId, long drinkId, int amount) {
        insert(new Bought(customerId, drinkId, amount));
    }

    @Insert
    public abstract void insert(Bought bought);

    @Insert
    public abstract void insertAll(Bought... boughts);

    @Delete
    public abstract void delete(Bought bought);
}
