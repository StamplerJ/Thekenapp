package com.jann_luellmann.thekenapp.data.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jann_luellmann.thekenapp.data.db.Database
import com.jann_luellmann.thekenapp.data.repository.BaseRepository
import com.jann_luellmann.thekenapp.data.repository.EventAndCustomerWithDrinksRepository
import com.jann_luellmann.thekenapp.data.repository.EventWithDrinksAndCustomersRepository
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventAndCustomerWithDrinksViewModel
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventWithDrinksAndCustomersViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventWithDrinksAndCustomersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventWithDrinksAndCustomersViewModel(EventWithDrinksAndCustomersRepository(
                Database.getInstance().eventWithDrinksAndCustomersDAO()
            )) as T
        }
        if (modelClass.isAssignableFrom(EventAndCustomerWithDrinksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventAndCustomerWithDrinksViewModel(EventAndCustomerWithDrinksRepository(
                Database.getInstance().eventAndCustomerWithDrinksDAO()
            )) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}