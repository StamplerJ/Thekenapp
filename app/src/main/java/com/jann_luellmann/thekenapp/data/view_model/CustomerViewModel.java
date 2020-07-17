package com.jann_luellmann.thekenapp.data.view_model;

import com.jann_luellmann.thekenapp.data.model.Customer;
import com.jann_luellmann.thekenapp.data.model.EventCustomerCrossRef;

import java.util.List;

import androidx.lifecycle.LiveData;

public class CustomerViewModel extends BaseViewModel<Customer> {

    public CustomerViewModel() {
        super();
    }

    public void insert(Customer customer) {
        executor.execute(() -> db.customerDAO().insertAll(customer));
    }

    public void insert(List<Customer> customers) {
        executor.execute(() -> db.customerDAO().insertAll(customers.toArray(new Customer[0])));
    }

    public void update(Customer customer) {
        executor.execute(() -> db.customerDAO().update(customer));
    }

    public LiveData<List<Customer>> findAll() {
        return db.customerDAO().getAll();
    }

    @Override
    public void insert(long eventId, Customer customer) {
        executor.execute(() -> {
            long id = db.customerDAO().insert(customer);
            db.eventCustomerCrossDAO().insertAll(new EventCustomerCrossRef(eventId, id));
        });
    }

    @Override
    public void delete(Customer customer) {
        executor.execute(() -> db.customerDAO().delete(customer));
    }
}
