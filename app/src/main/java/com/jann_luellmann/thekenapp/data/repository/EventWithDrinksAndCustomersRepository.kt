package com.jann_luellmann.thekenapp.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.jann_luellmann.thekenapp.data.dao.EventDAO
import com.jann_luellmann.thekenapp.data.dao.relationship.EventWithDrinksAndCustomersDAO
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinksAndCustomers

class EventWithDrinksAndCustomersRepository(private val eventDAO: EventWithDrinksAndCustomersDAO) : BaseRepository() {

    val allEvents: LiveData<List<EventWithDrinksAndCustomers>> = eventDAO.all

    fun getById(id: Long): LiveData<EventWithDrinksAndCustomers> {
        return eventDAO.findById(id)
    }

//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun insert(event: EventWithDrinksAndCustomers) {
//        eventDAO.insert(event)
//    }
}
