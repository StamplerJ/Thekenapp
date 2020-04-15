package com.jann_luellmann.thekenapp.data.dao;

import com.jann_luellmann.thekenapp.data.model.Customer;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CustomerDAO {

    @Query("SELECT * FROM customer")
    LiveData<List<Customer>> getAll();

    @Query("SELECT * FROM customer WHERE id IN (:customerIds)")
    LiveData<List<Customer>> loadAllByIds(long[] customerIds);

    @Query("SELECT * FROM customer WHERE name LIKE :name LIMIT 1")
    LiveData<Customer> findByName(String name);

    @Insert
    void insertAll(Customer... customers);

    @Update
    void update(Customer customer);

    @Delete
    void delete(Customer customer);
}
