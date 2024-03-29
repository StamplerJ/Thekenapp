package com.jann_luellmann.thekenapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jann_luellmann.thekenapp.data.model.EventDrinkCrossRef

@Dao
interface EventDrinkCrossDAO {
    @get:Query("SELECT * FROM eventDrinkCrossRef")
    @get:Transaction
    val all: LiveData<List<EventDrinkCrossRef>>

    @Query("SELECT * FROM eventDrinkCrossRef WHERE eventId = :eventId")
    fun findAllById(eventId: Long) : LiveData<List<EventDrinkCrossRef>>

    @Query("SELECT * FROM eventDrinkCrossRef WHERE eventId = :eventId")
    fun findAllByIdBlocking(eventId: Long) : List<EventDrinkCrossRef>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg eventDrinkCrossRefs: EventDrinkCrossRef)

    @Delete
    fun delete(eventDrinkCrossRef: EventDrinkCrossRef)
}