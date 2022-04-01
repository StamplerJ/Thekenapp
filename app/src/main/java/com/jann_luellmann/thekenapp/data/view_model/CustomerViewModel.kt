package com.jann_luellmann.thekenapp.data.view_model

import androidx.lifecycle.LiveData
import com.jann_luellmann.thekenapp.data.model.Customer
import com.jann_luellmann.thekenapp.data.model.EventCustomerCrossRef

class CustomerViewModel : BaseViewModel<Customer?>() {
    fun insert(customer: Customer?) {
        executor.execute { db.customerDAO().insertAll(customer) }
    }

    fun insert(customers: List<Customer?>) {
        executor.execute { db.customerDAO().insertAll(*customers.toTypedArray()) }
    }

    fun update(customer: Customer) {
        executor.execute { db.customerDAO().update(customer) }
    }

    override fun findAll(): LiveData<List<Customer?>>? {
        return db.customerDAO().all
    }

    override fun insert(eventId: Long, customer: Customer?) {
        executor.execute {
            val id = db.customerDAO().insert(customer)
            db.eventCustomerCrossDAO().insertAll(EventCustomerCrossRef(eventId, id))
        }
    }

    override fun delete(customer: Customer?) {
        executor.execute { db.customerDAO().delete(customer) }
    }
}