package com.jann_luellmann.thekenapp.data.dao.relationship

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinks

@Dao
interface EventWithDrinksDAO {
    @get:Query("SELECT * FROM event")
    @get:Transaction
    val all: LiveData<List<EventWithDrinks?>>?

    @Transaction
    @Query("SELECT * FROM event WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String?): LiveData<EventWithDrinks?>?
}