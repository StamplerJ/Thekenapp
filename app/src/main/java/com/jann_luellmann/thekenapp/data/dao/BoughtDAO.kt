package com.jann_luellmann.thekenapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jann_luellmann.thekenapp.data.model.Bought

@Dao
abstract class BoughtDAO {
    @get:Query("SELECT * FROM bought")
    abstract val all: LiveData<List<Bought?>>?

    @Query("SELECT * FROM bought WHERE customerId IN (:customerIds)")
    abstract fun findAllByCustomerIds(customerIds: LongArray?): LiveData<List<Bought?>?>?

    @Query("SELECT * FROM bought WHERE eventId = :eventId and customerId = :customerId")
    abstract fun findAllByEventAndCustomer(
        eventId: Long,
        customerId: Long
    ): LiveData<List<Bought?>?>?

    @Transaction
    open fun insert(eventId: Long, customerId: Long, drinkId: Long, amount: Int) {
        insert(Bought(eventId, customerId, drinkId, amount))
    }

    @Insert
    abstract fun insert(bought: Bought?)

    @Insert
    abstract fun insertAll(vararg boughts: Bought?)

    @Delete
    abstract fun delete(bought: Bought?)
}