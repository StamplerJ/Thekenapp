package com.jann_luellmann.thekenapp.data.view_model

import androidx.lifecycle.LiveData
import com.jann_luellmann.thekenapp.data.model.Event

class EventViewModel : BaseViewModel<Event>() {
    fun insert(event: Event) {
        executor.execute { db.eventDAO().insertAll(event) }
    }

    fun insert(events: List<Event>) {
        executor.execute { db.eventDAO().insertAll(*events.toTypedArray()) }
    }

    fun update(event: Event) {
        executor.execute { db.eventDAO().update(event) }
    }

    override fun findAll(): LiveData<List<Event>>? {
        return db.eventDAO().all
    }

    override fun insert(eventId: Long, event: Event) {
        insert(event)
    }

    override fun delete(event: Event) {
        executor.execute { db.eventDAO().delete(event) }
    }
}