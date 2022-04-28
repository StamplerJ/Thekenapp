package com.jann_luellmann.thekenapp.data.repository

import androidx.lifecycle.LiveData
import com.jann_luellmann.thekenapp.data.dao.relationship.EventWithDrinksAndCustomersDAO
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinksAndCustomers

class EventWithDrinksAndCustomersRepository(private val eventDAO: EventWithDrinksAndCustomersDAO) :
    BaseRepository() {

    val allEvents: LiveData<List<EventWithDrinksAndCustomers>> = eventDAO.all

    fun getById(id: Long): LiveData<EventWithDrinksAndCustomers> {
        return eventDAO.findById(id)
    }
}
