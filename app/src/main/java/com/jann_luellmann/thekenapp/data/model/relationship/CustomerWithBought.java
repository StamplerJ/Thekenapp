package com.jann_luellmann.thekenapp.data.model.relationship;

import com.jann_luellmann.thekenapp.data.model.Bought;
import com.jann_luellmann.thekenapp.data.model.Customer;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerWithBought implements Comparable<CustomerWithBought> {
    @Embedded
    private Customer customer;

    @Relation(
            parentColumn = "customerId",
            entityColumn = "customerId"
    )
    private List<Bought> boughts;

    public List<Bought> getBoughtsByEvent(long eventId) {
        List<Bought> boughtsByEvent = new ArrayList<>();

        for (Bought bought : boughts) {
            if(bought.getEventId() == eventId)
                boughtsByEvent.add(bought);
        }

        return boughtsByEvent;
    }

    @NonNull
    @Override
    public String toString() {
        return customer.getName();
    }

    @Override
    public int compareTo(CustomerWithBought c) {
        return this.customer.getName().compareTo(c.getCustomer().getName());
    }
}
