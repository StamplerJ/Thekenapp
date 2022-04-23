package com.jann_luellmann.thekenapp.view

import com.jann_luellmann.thekenapp.OnCustomerClickedListener
import com.jann_luellmann.thekenapp.data.model.Customer

class RowHeaderCustomer(
    val customer: Customer,
    private val onCustomerClickedListener: OnCustomerClickedListener
) : RowHeader(customer) {
    override fun onClick() {
        onCustomerClickedListener.onCustomerClicked(customer)
    }
}