package com.jann_luellmann.thekenapp.data.view_model.relationship

import androidx.lifecycle.LiveData
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinksAndCustomers
import com.jann_luellmann.thekenapp.data.view_model.BaseViewModel

class EventWithDrinksAndCustomersViewModel : BaseViewModel<EventWithDrinksAndCustomers?>() {
    override fun findAll(): LiveData<List<EventWithDrinksAndCustomers?>>? {
        return db.eventWithDrinksAndCustomersDAO().all
    }

    fun findById(id: Long): LiveData<EventWithDrinksAndCustomers?>? {
        return db.eventWithDrinksAndCustomersDAO().findById(id)
    }

    fun findByName(name: String?): LiveData<EventWithDrinksAndCustomers?>? {
        return db.eventWithDrinksAndCustomersDAO().findByName(name)
    }

    fun findByEvent(event: Event): LiveData<EventWithDrinksAndCustomers?>? {
        return db.eventWithDrinksAndCustomersDAO().findByName(event.name)
    }

    override fun insert(eventId: Long, event: EventWithDrinksAndCustomers?) {
        //nop
    }

    override fun delete(eventWithDrinksAndCustomers: EventWithDrinksAndCustomers?) {
        //nop
    }
}