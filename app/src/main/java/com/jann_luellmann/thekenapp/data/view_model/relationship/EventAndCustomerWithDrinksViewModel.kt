package com.jann_luellmann.thekenapp.data.view_model.relationship

import androidx.lifecycle.LiveData
import com.jann_luellmann.thekenapp.data.model.Customer
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.data.model.EventCustomerDrinkCrossRef
import com.jann_luellmann.thekenapp.data.model.relationship.CustomerEventWithBoughtDrinks
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinksAndCustomers
import com.jann_luellmann.thekenapp.data.repository.EventAndCustomerWithDrinksRepository
import com.jann_luellmann.thekenapp.data.repository.EventWithDrinksAndCustomersRepository
import com.jann_luellmann.thekenapp.data.view_model.BaseViewModel

class EventAndCustomerWithDrinksViewModel(
    private val repository: EventAndCustomerWithDrinksRepository
) : BaseViewModel<EventCustomerDrinkCrossRef>() {

    val all: LiveData<List<EventCustomerDrinkCrossRef>> = repository.allThings

    override fun findAll(): LiveData<List<EventCustomerDrinkCrossRef>> {
        return db.eventAndCustomerWithDrinksDAO().all
    }

    fun findByIds(eventId: Long, customerId: Long): LiveData<List<EventCustomerDrinkCrossRef>> {
        return repository.getByIds(eventId, customerId)
    }

    fun findBy(event: Event, customer: Customer): LiveData<List<EventCustomerDrinkCrossRef>> {
        return repository.getBy(event, customer)
    }

    fun findAllByEvent(eventId: Long): LiveData<List<CustomerEventWithBoughtDrinks>> {
        return repository.getAllByEvent(eventId)
    }

    fun findAllByEventAndCustomer(eventId: Long, customerId: Long): LiveData<List<CustomerEventWithBoughtDrinks>> {
        return repository.getAllByEventAndCustomer(eventId, customerId)
    }

    fun updateAmount(eventId: Long, customerId: Long, drinkId: Long, amount: Int) {
        executor.execute {
            repository.updateAmount(eventId, customerId, drinkId, amount)
        }
    }

    override fun insert(eventId: Long, event: EventCustomerDrinkCrossRef) {
        //nop
    }

    override fun delete(eventWithDrinksAndCustomers: EventCustomerDrinkCrossRef) {
        //nop
    }
}