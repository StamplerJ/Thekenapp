package com.jann_luellmann.thekenapp.data.model.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.jann_luellmann.thekenapp.data.model.Bought
import com.jann_luellmann.thekenapp.data.model.Customer

class CustomerWithBought : Comparable<CustomerWithBought> {
    @Embedded
    var customer: Customer? = null

    @Relation(parentColumn = "customerId", entityColumn = "customerId")
    var boughts: MutableList<Bought> = mutableListOf()

    fun getBoughtsByEvent(eventId: Long): List<Bought> {
        val boughtsByEvent: MutableList<Bought> = ArrayList<Bought>()

        for (bought in boughts) {
            if (bought.eventId === eventId)
                boughtsByEvent.add(bought)
        }
        return boughtsByEvent
    }

    override fun toString(): String {
        return customer?.name ?: "No customer"
    }

    override fun compareTo(other: CustomerWithBought): Int {
        return other.customer?.let { customer?.name?.compareTo(it.name) } ?: -1
    }
}