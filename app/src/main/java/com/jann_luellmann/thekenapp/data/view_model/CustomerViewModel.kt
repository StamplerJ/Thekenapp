package com.jann_luellmann.thekenapp.data.view_model

import androidx.lifecycle.LiveData
import com.jann_luellmann.thekenapp.data.dao.relationship.EventAndCustomerWithDrinksDAO
import com.jann_luellmann.thekenapp.data.model.Customer
import com.jann_luellmann.thekenapp.data.model.EventCustomerCrossRef
import com.jann_luellmann.thekenapp.data.model.EventCustomerDrinkCrossRef

class CustomerViewModel : BaseViewModel<Customer>() {
    fun insert(customer: Customer) {
        executor.execute { db.customerDAO().insertAll(customer) }
    }

    fun insert(customers: List<Customer>) {
        executor.execute { db.customerDAO().insertAll(*customers.toTypedArray()) }
    }

    fun update(customer: Customer) {
        executor.execute { db.customerDAO().update(customer) }
    }

    override fun findAll(): LiveData<List<Customer>> {
        return db.customerDAO().all
    }

    /**
     * Creates a customer for this event and inserts a EventCustomerDrinkCrossRef for each drink
     * in this event.
     */
    override fun insert(eventId: Long, customer: Customer) {
        executor.execute {
            val customerId = db.customerDAO().insert(customer)
            db.eventCustomerCrossDAO().insertAll(EventCustomerCrossRef(eventId, customerId))

            val drinkIds = db.eventDrinkCrossDAO().findAllByIdBlocking(eventId)
            drinkIds.forEach {
                db.eventAndCustomerWithDrinksDAO().insert(EventCustomerDrinkCrossRef(eventId, customerId, it.drinkId))
            }
        }
    }

    override fun delete(customer: Customer) {
        executor.execute { db.customerDAO().delete(customer) }
    }
}