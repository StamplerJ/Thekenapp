package com.jann_luellmann.thekenapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jann_luellmann.thekenapp.data.model.Customer

@Dao
abstract class CustomerDAO {
    @get:Query("SELECT * FROM customer")
    abstract val all: LiveData<List<Customer?>>?

    @Query("SELECT * FROM customer WHERE customerId IN (:customerIds)")
    abstract fun loadAllByIds(customerIds: LongArray?): LiveData<List<Customer?>?>?

    @Query("SELECT * FROM customer WHERE name LIKE :name LIMIT 1")
    abstract fun findByName(name: String?): LiveData<Customer?>?

    @Insert
    abstract fun insert(customer: Customer?): Long

    @Insert
    abstract fun insertAll(vararg customers: Customer?)

    @Update
    abstract fun update(customer: Customer?)

    @Delete
    abstract fun delete(customer: Customer?)
}