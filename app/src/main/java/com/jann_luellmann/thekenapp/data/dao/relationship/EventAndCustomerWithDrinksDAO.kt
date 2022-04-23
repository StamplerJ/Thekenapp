package com.jann_luellmann.thekenapp.data.dao.relationship

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import com.jann_luellmann.thekenapp.data.model.EventCustomerDrinkCrossRef
import com.jann_luellmann.thekenapp.data.model.relationship.CustomerEventWithBoughtDrinks

@Dao
interface EventAndCustomerWithDrinksDAO {
    @get:Query("SELECT * FROM eventCustomerDrinkCrossRef")
    @get:Transaction
    val all: LiveData<List<EventCustomerDrinkCrossRef>>

    @Transaction
    @Query("SELECT * FROM eventCustomerDrinkCrossRef WHERE eventId = :eventId AND customerId = :customerId")
    fun findByEventAndCustomer(
        eventId: Long,
        customerId: Long
    ): LiveData<List<EventCustomerDrinkCrossRef>>

    @Insert(onConflict = IGNORE)
    fun insert(eventCustomerDrinkCrossRef: EventCustomerDrinkCrossRef)

    @Insert(onConflict = IGNORE)
    fun insertAll(vararg eventCustomerDrinkCrossRef: EventCustomerDrinkCrossRef)

    @Transaction
    @Query("SELECT e.eventId, ename, date, total, c.customerId, cname, d.drinkId, dname, price, amount FROM eventcustomerdrinkcrossref ecd " +
            "JOIN customer c ON ecd.customerId = c.customerId " +
            "JOIN drink d ON ecd.drinkId = d.drinkId " +
            "JOIN event e ON ecd.eventId = e.eventId " +
            "WHERE ecd.eventId = :eventId")
    fun getAllByEvent(eventId: Long): LiveData<List<CustomerEventWithBoughtDrinks>>

    @Transaction
    @Query("SELECT e.eventId, ename, date, total, c.customerId, cname, d.drinkId, dname, price, amount FROM eventcustomerdrinkcrossref ecd " +
            "JOIN customer c ON ecd.customerId = c.customerId " +
            "JOIN drink d ON ecd.drinkId = d.drinkId " +
            "JOIN event e ON ecd.eventId = e.eventId " +
            "WHERE ecd.eventId = :eventId AND ecd.customerId = :customerId")
    fun getAllByEventAndCustomer(
        eventId: Long,
        customerId: Long
    ): LiveData<List<CustomerEventWithBoughtDrinks>>

    @Query("UPDATE eventCustomerDrinkCrossRef SET amount = :amount WHERE eventId = :eventId AND customerId = :customerId AND drinkId = :drinkId")
    fun updateAmount(eventId: Long, customerId: Long, drinkId: Long, amount: Int)
}