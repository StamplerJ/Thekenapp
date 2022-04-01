package com.jann_luellmann.thekenapp.data.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jann_luellmann.thekenapp.data.db.Database
import com.jann_luellmann.thekenapp.data.db.Database.AppDatabase
import java.util.concurrent.Executor
import java.util.concurrent.Executors

abstract class BaseViewModel<T> : ViewModel() {
    protected var db: AppDatabase = Database.getInstance()
    protected var executor: Executor = Executors.newSingleThreadExecutor()
    abstract fun findAll(): LiveData<List<T>>?
    abstract fun insert(eventId: Long, t: T)
    abstract fun delete(t: T)
}