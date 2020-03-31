package com.jann_luellmann.thekenapp.data.model.relationship;

import com.jann_luellmann.thekenapp.data.model.Customer;
import com.jann_luellmann.thekenapp.data.model.Event;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class EventWithCustomers {
    @Embedded
    public Event event;

    @Relation(
            parentColumn = "id",
            entityColumn = "eventId"
    )
    public List<Customer> customers;
}
