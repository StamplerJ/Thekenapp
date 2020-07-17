package com.jann_luellmann.thekenapp.data.dao;

import com.jann_luellmann.thekenapp.data.model.Customer;
import com.jann_luellmann.thekenapp.data.model.Drink;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public abstract class CustomerDAO {

    @Query("SELECT * FROM customer")
    public abstract LiveData<List<Customer>> getAll();

    @Query("SELECT * FROM customer WHERE customerId IN (:customerIds)")
    public abstract LiveData<List<Customer>> loadAllByIds(long[] customerIds);

    @Query("SELECT * FROM customer WHERE name LIKE :name LIMIT 1")
    public abstract LiveData<Customer> findByName(String name);

    @Insert
    public abstract long insert(Customer customer);

    @Insert
    public abstract void insertAll(Customer... customers);

    @Update
    public abstract void update(Customer customer);

    @Delete
    public abstract void delete(Customer customer);
}
