package com.jann_luellmann.thekenapp.data.model.relationship;

import com.jann_luellmann.thekenapp.data.model.Customer;
import com.jann_luellmann.thekenapp.data.model.Event;
import com.jann_luellmann.thekenapp.data.model.EventCustomerCrossRef;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventWithCustomers {
    @Embedded
    private Event event;

    @Relation(
            parentColumn = "eventId",
            entityColumn = "customerId",
            associateBy = @Junction(EventCustomerCrossRef.class)
    )
    private List<Customer> customers;
}
