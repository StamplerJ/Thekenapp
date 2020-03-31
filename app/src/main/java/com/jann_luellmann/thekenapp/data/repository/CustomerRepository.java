package com.jann_luellmann.thekenapp.data.repository;

import com.jann_luellmann.thekenapp.data.model.Customer;

import java.util.List;

import androidx.lifecycle.LiveData;

public class CustomerRepository extends BaseRepository {
    
    public void insert(Customer customer) {
        executor.execute(() -> db.customerDAO().insertAll(customer));
    }

    public void insert(List<Customer> customers) {
        executor.execute(() -> db.customerDAO().insertAll(customers.toArray(new Customer[0])));
    }

    public LiveData<List<Customer>> findAll() {
        return db.customerDAO().getAll();
    }
}
