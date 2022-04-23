package com.jann_luellmann.thekenapp.data.dao.relationship

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
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
}