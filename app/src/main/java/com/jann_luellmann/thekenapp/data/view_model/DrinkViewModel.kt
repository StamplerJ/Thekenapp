package com.jann_luellmann.thekenapp.data.view_model

import androidx.lifecycle.LiveData
import com.jann_luellmann.thekenapp.data.model.Drink
import com.jann_luellmann.thekenapp.data.model.EventCustomerDrinkCrossRef
import com.jann_luellmann.thekenapp.data.model.EventDrinkCrossRef

class DrinkViewModel : BaseViewModel<Drink>() {
    fun insert(drink: Drink) {
        executor.execute { db.drinkDAO().insertAll(drink) }
    }

    fun insert(drinks: List<Drink>) {
        executor.execute { db.drinkDAO().insertAll(*drinks.toTypedArray()) }
    }

    fun update(drink: Drink) {
        executor.execute { db.drinkDAO().update(drink) }
    }

    override fun findAll(): LiveData<List<Drink>> {
        return db.drinkDAO().all
    }

    override fun insert(eventId: Long, drink: Drink) {
        executor.execute {
            val drinkId = db.drinkDAO().insert(drink)
            db.eventDrinkCrossDAO().insertAll(EventDrinkCrossRef(eventId, drinkId))

            val customerIds = db.eventCustomerCrossDAO().findAllByIdBlocking(eventId)
            customerIds.forEach {
                db.eventAndCustomerWithDrinksDAO()
                    .insert(EventCustomerDrinkCrossRef(eventId, it.customerId, drinkId))
            }
        }
    }

    override fun delete(drink: Drink) {
        executor.execute { db.drinkDAO().delete(drink) }
    }
}