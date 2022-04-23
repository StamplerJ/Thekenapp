package com.jann_luellmann.thekenapp.data.model.relationship

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jann_luellmann.thekenapp.data.model.*

class EventWithDrinksAndCustomers {
    @Embedded
    var event: Event? = null

    @Relation(
        parentColumn = "eventId", entityColumn = "drinkId", associateBy = Junction(
            EventDrinkCrossRef::class
        )
    )
    var drinks: List<Drink> = emptyList()

    @Relation(
        entity = Customer::class,
        parentColumn = "eventId",
        entityColumn = "customerId",
        associateBy = Junction(
            EventCustomerCrossRef::class
        )
    )
    var customers: MutableList<Customer> = mutableListOf()
}