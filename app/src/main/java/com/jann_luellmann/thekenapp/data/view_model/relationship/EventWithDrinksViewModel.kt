package com.jann_luellmann.thekenapp.data.view_model.relationship

import androidx.lifecycle.LiveData
import com.jann_luellmann.thekenapp.data.model.Drink
import com.jann_luellmann.thekenapp.data.model.Event
import com.jann_luellmann.thekenapp.data.model.EventDrinkCrossRef
import com.jann_luellmann.thekenapp.data.model.relationship.EventWithDrinks
import com.jann_luellmann.thekenapp.data.view_model.BaseViewModel

class EventWithDrinksViewModel : BaseViewModel<EventWithDrinks?>() {
    override fun findAll(): LiveData<List<EventWithDrinks?>>? {
        return db.eventWithDrinksDAO().all
    }

    fun findByName(name: String?): LiveData<EventWithDrinks?>? {
        return db.eventWithDrinksDAO().findByName(name)
    }

    fun findByEvent(event: Event): LiveData<EventWithDrinks?>? {
        return db.eventWithDrinksDAO().findByName(event.name)
    }

    override fun insert(eventId: Long, eventWithDrinks: EventWithDrinks?) {
        //nop
    }

    override fun delete(eventWithDrinks: EventWithDrinks?) {
        //nop
    }

    fun delete(eventId: Long, drink: Drink) {
        executor.execute {
            db.eventDrinkCrossDAO().delete(EventDrinkCrossRef(eventId, drink.drinkId))
        }
    }
}