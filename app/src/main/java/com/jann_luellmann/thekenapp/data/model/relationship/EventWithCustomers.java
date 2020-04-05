package com.jann_luellmann.thekenapp.data.model.relationship;

import com.jann_luellmann.thekenapp.data.model.Customer;
import com.jann_luellmann.thekenapp.data.model.Event;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventWithCustomers {
    @Embedded
    private Event event;

    @Relation(
            parentColumn = "id",
            entityColumn = "eventId"
    )
    private List<Customer> customers;
}
