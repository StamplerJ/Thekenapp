package com.jann_luellmann.thekenapp.data.dao.relationship

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinksAndCustomers

@Dao
interface EventWithDrinksAndCustomersDAO {
    @get:Query("SELECT * FROM event")
    @get:Transaction
    val all: LiveData<List<EventWithDrinksAndCustomers>>

    @Transaction
    @Query("SELECT * FROM event WHERE event.ename LIKE :name LIMIT 1")
    fun findByName(name: String): LiveData<EventWithDrinksAndCustomers>

    @Transaction
    @Query("SELECT * FROM event WHERE eventId = :id LIMIT 1")
    fun findById(id: Long): LiveData<EventWithDrinksAndCustomers>

    @Query("DELETE FROM eventCustomerCrossRef WHERE eventId = :eventId")
    fun deleteEventCustomers(eventId: Long)

    @Query("DELETE FROM eventDrinkCrossRef WHERE eventId = :eventId")
    fun deleteEventDrinks(eventId: Long)
}