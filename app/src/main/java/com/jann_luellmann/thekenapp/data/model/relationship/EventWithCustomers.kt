package com.jann_luellmann.thekenapp.data.model.relationship

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jann_luellmann.thekenapp.data.model.Customer
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.data.model.EventCustomerCrossRef

class EventWithCustomers {
    @Embedded
    var event: Event? = null

    @Relation(
        parentColumn = "eventId", entityColumn = "customerId", associateBy = Junction(
            EventCustomerCrossRef::class
        )
    )
    var customers: MutableList<Customer> = mutableListOf()
}