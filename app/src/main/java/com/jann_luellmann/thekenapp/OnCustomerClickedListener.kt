package com.jann_luellmann.thekenapp

import com.jann_luellmann.thekenapp.data.model.Customer

interface OnCustomerClickedListener {
    fun onCustomerClicked(customer: Customer)
}