package com.jann_luellmann.thekenapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import com.jann_luellmann.thekenapp.data.model.Customer

@Dao
interface CustomerDAO {
    @get:Query("SELECT * FROM customer")
    val all: LiveData<List<Customer>>

    @Query("SELECT * FROM customer WHERE customerId IN (:customerIds)")
    fun loadAllByIds(customerIds: LongArray): LiveData<List<Customer>>

    @Query("SELECT * FROM customer WHERE cname LIKE :name LIMIT 1")
    fun findByName(name: String): LiveData<Customer>

    @Insert(onConflict = REPLACE)
    fun insert(customer: Customer): Long

    @Insert
    fun insertAll(vararg customers: Customer)

    @Update
    fun update(customer: Customer)

    @Delete
    fun delete(customer: Customer)
}