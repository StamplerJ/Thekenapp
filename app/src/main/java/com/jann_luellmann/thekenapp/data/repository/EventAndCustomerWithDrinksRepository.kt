package com.jann_luellmann.thekenapp.data.repository

import androidx.lifecycle.LiveData
import com.jann_luellmann.thekenapp.data.dao.relationship.EventAndCustomerWithDrinksDAO
import com.jann_luellmann.thekenapp.data.dao.relationship.EventWithDrinksAndCustomersDAO
import com.jann_luellmann.thekenapp.data.model.Customer
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.data.model.EventCustomerDrinkCrossRef
import com.jann_luellmann.thekenapp.data.model.relationship.CustomerEventWithBoughtDrinks
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinksAndCustomers

class EventAndCustomerWithDrinksRepository(private val eventDAO: EventAndCustomerWithDrinksDAO) :
    BaseRepository() {

    val allThings: LiveData<List<EventCustomerDrinkCrossRef>> = eventDAO.all

    fun getByIds(eventId: Long, customerId: Long): LiveData<List<EventCustomerDrinkCrossRef>> {
        return eventDAO.findByEventAndCustomer(eventId, customerId)
    }

    fun getBy(event: Event, customer: Customer): LiveData<List<EventCustomerDrinkCrossRef>> {
        return eventDAO.findByEventAndCustomer(event.eventId, customer.customerId)
    }

    fun getAllByEvent(eventId: Long): LiveData<List<CustomerEventWithBoughtDrinks>> {
        return eventDAO.getAllByEvent(eventId)
    }

    fun getAllByEventAndCustomer(eventId: Long, customerId: Long): LiveData<List<CustomerEventWithBoughtDrinks>> {
        return eventDAO.getAllByEventAndCustomer(eventId, customerId)
    }

    fun updateAmount(eventId: Long, customerId: Long, drinkId: Long, amount: Int) {
        eventDAO.updateAmount(eventId, customerId, drinkId, amount)
    }
}
