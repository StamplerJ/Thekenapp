package com.jann_luellmann.thekenapp.data.model.relationship

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.jann_luellmann.thekenapp.data.model.Customer
import com.jann_luellmann.thekenapp.data.model.Drink
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.data.model.EventCustomerDrinkCrossRef

data class CustomerEventWithBoughtDrinks(
    @Embedded val event: Event,
    @Embedded val customer: Customer,
    @Embedded val drink: Drink,

    var amount: Int
)
