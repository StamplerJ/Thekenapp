package com.jann_luellmann.thekenapp.data.view_model.relationship

import androidx.lifecycle.LiveData
import com.jann_luellmann.thekenapp.data.model.Customer
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.data.model.EventCustomerCrossRef
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithCustomers
import com.jann_luellmann.thekenapp.data.view_model.BaseViewModel

class EventWithCustomersViewModel : BaseViewModel<EventWithCustomers>() {
    override fun findAll(): LiveData<List<EventWithCustomers>> {
        return db.eventWithCustomersDAO().all
    }

    fun findByName(name: String): LiveData<EventWithCustomers> {
        return db.eventWithCustomersDAO().findByName(name)
    }

    fun findByEvent(event: Event): LiveData<EventWithCustomers> {
        return db.eventWithCustomersDAO().findByName(event.name)
    }

    override fun insert(eventId: Long, eventWithCustomers: EventWithCustomers) {
        //noi
    }

    override fun delete(eventWithCustomers: EventWithCustomers) {
        //noi
    }

    fun delete(eventId: Long, customer: Customer) {
        executor.execute {
            db.eventCustomerCrossDAO()
                .delete(EventCustomerCrossRef(eventId, customer.customerId))
        }
    }
}