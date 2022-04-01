package com.jann_luellmann.thekenapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jann_luellmann.thekenapp.data.model.EventCustomerCrossRef

@Dao
interface EventCustomerCrossDAO {
    @get:Query("SELECT * FROM eventCustomerCrossRef")
    @get:Transaction
    val all: LiveData<List<EventCustomerCrossRef?>>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg eventCustomerCrossRef: EventCustomerCrossRef?)

    @Delete
    fun delete(eventWithCustomers: EventCustomerCrossRef?)
}