package com.jann_luellmann.thekenapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jann_luellmann.thekenapp.data.model.Drink
import com.jann_luellmann.thekenapp.data.model.Event

@Dao
interface EventDAO {
    @get:Query("SELECT * FROM event ORDER BY date")
    val all: LiveData<List<Event>>

    @Query("SELECT * FROM event WHERE eventId IN (:eventIds)")
    fun loadAllByIds(eventIds: LongArray): LiveData<List<Event>>

    @Query("SELECT * FROM event WHERE ename LIKE :name LIMIT 1")
    fun findByName(name: String): LiveData<Event>

    @Insert
    suspend fun insert(event: Event): Long

    @Insert
    fun insertAll(vararg events: Event)

    @Update
    fun update(event: Event)

    @Delete
    fun delete(event: Event)
}