package com.jann_luellmann.thekenapp.data.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jann_luellmann.thekenapp.data.repository.BaseRepository
import com.jann_luellmann.thekenapp.data.repository.EventAndCustomerWithDrinksRepository
import com.jann_luellmann.thekenapp.data.repository.EventWithDrinksAndCustomersRepository
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventAndCustomerWithDrinksViewModel
import com.jann_luellmann.thekenapp.data.view_model.relationship.EventWithDrinksAndCustomersViewModel

class ViewModelFactory(private val repository: BaseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventWithDrinksAndCustomersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventWithDrinksAndCustomersViewModel(repository as EventWithDrinksAndCustomersRepository) as T
        }
        if (modelClass.isAssignableFrom(EventAndCustomerWithDrinksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventAndCustomerWithDrinksViewModel(repository as EventAndCustomerWithDrinksRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}