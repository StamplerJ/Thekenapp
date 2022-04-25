package com.jann_luellmann.thekenapp.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jann_luellmann.thekenapp.data.dao.*
import com.jann_luellmann.thekenapp.data.dao.relationship.EventAndCustomerWithDrinksDAO
import com.jann_luellmann.thekenapp.data.dao.relationship.EventWithCustomersDAO
import com.jann_luellmann.thekenapp.data.dao.relationship.EventWithDrinksAndCustomersDAO
import com.jann_luellmann.thekenapp.data.dao.relationship.EventWithDrinksDAO
import com.jann_luellmann.thekenapp.data.model.*
import com.jann_luellmann.thekenapp.data.util.Converters
import java.util.*
import java.util.concurrent.Executors

class Database private constructor(context: Context) {
    @androidx.room.Database(
        entities = [Customer::class, Drink::class, Event::class, EventCustomerCrossRef::class, EventDrinkCrossRef::class, EventCustomerDrinkCrossRef::class],
        version = 1
    )
    @TypeConverters(
        Converters::class
    )
    abstract class AppDatabase : RoomDatabase() {
        abstract fun customerDAO(): CustomerDAO
        abstract fun drinkDAO(): DrinkDAO
        abstract fun eventDAO(): EventDAO

        // Relationship DAOs
        abstract fun eventWithCustomersDAO(): EventWithCustomersDAO
        abstract fun eventWithDrinksDAO(): EventWithDrinksDAO
        abstract fun eventDrinkCrossDAO(): EventDrinkCrossDAO
        abstract fun eventCustomerCrossDAO(): EventCustomerCrossDAO
        abstract fun eventWithDrinksAndCustomersDAO(): EventWithDrinksAndCustomersDAO
        abstract fun eventAndCustomerWithDrinksDAO(): EventAndCustomerWithDrinksDAO
    }

    private fun populateInitialData() {
        Executors.newSingleThreadExecutor().execute {
            with(getInstance()) {
                runInTransaction {
                    clearAllTables()
                    eventDAO().insertAll(
                        Event(1L, "Schützenfest 1. Tag", Date(), 0),
                        Event(2L, "Schützenfest 2. Tag", Date(), 0)
                    )
                    customerDAO().insertAll(
                        Customer(1L, "Jana Körner"),
                        Customer(2L, "Joachim Sander"),
                        Customer(3L, "Jann Lüllmann"),
                        Customer(4L, "Malte Sander und seine Freundin Nadine"),
                        Customer(5L, "Marc Müller"),
                        Customer(6L, "Pascal Wittenberg"),
                        Customer(7L, "Tim Tom"),
                        Customer(8L, "Marie Lindemann"),
                        Customer(9L, "Heinz-Hermann Hansemann Sen.")
                    )
                    drinkDAO().insertAllBlocking(
                        Drink(1L, "Wasser", 100),
                        Drink(2L, "Bier, Alster", 150),
                        Drink(3L, "Cola, Fanta, Sprite", 120),
                        Drink(4L, "Barcadi Cola", 300),
                        Drink(5L, "Cocktail", 250),
                        Drink(6L, "Hugo", 200),
                        Drink(7L, "Cocktail", 250),
                        Drink(8L, "Charly, Cola-Korn", 250),
                        Drink(9L, "Charly, Cola-Korn", 300)
                    )
                    eventDrinkCrossDAO().insertAll(
                        EventDrinkCrossRef(1L, 1L),
                        EventDrinkCrossRef(1L, 2L),
                        EventDrinkCrossRef(1L, 3L),
                        EventDrinkCrossRef(1L, 4L),
                        EventDrinkCrossRef(1L, 5L),
                        EventDrinkCrossRef(2L, 1L),
                        EventDrinkCrossRef(2L, 2L),
                        EventDrinkCrossRef(2L, 3L),
                        EventDrinkCrossRef(2L, 5L)
                    )
                    eventCustomerCrossDAO().insertAll(
                        EventCustomerCrossRef(1L, 1L),
                        EventCustomerCrossRef(1L, 2L),
                        EventCustomerCrossRef(1L, 3L),
                        EventCustomerCrossRef(1L, 4L),
                        EventCustomerCrossRef(1L, 5L),
                        EventCustomerCrossRef(2L, 6L),
                        EventCustomerCrossRef(2L, 7L),
                        EventCustomerCrossRef(2L, 1L),
                        EventCustomerCrossRef(2L, 2L)
                    )
                    eventAndCustomerWithDrinksDAO().insertAll(
                        EventCustomerDrinkCrossRef(1L, 1L, 1L, 1),
                        EventCustomerDrinkCrossRef(1L, 1L, 2L, 11),
                        EventCustomerDrinkCrossRef(1L, 1L, 3L, 12),
                        EventCustomerDrinkCrossRef(1L, 1L, 4L, 13),
                        EventCustomerDrinkCrossRef(1L, 1L, 5L, 14),
                        EventCustomerDrinkCrossRef(1L, 2L, 1L, 1),
                        EventCustomerDrinkCrossRef(1L, 2L, 2L, 11),
                        EventCustomerDrinkCrossRef(1L, 2L, 3L, 12),
                        EventCustomerDrinkCrossRef(1L, 2L, 4L, 13),
                        EventCustomerDrinkCrossRef(1L, 2L, 5L, 14),
                        EventCustomerDrinkCrossRef(1L, 3L, 1L, 1),
                        EventCustomerDrinkCrossRef(1L, 3L, 2L, 11),
                        EventCustomerDrinkCrossRef(1L, 3L, 3L, 12),
                        EventCustomerDrinkCrossRef(1L, 3L, 4L, 13),
                        EventCustomerDrinkCrossRef(1L, 3L, 5L, 14),
                        EventCustomerDrinkCrossRef(1L, 4L, 1L, 1),
                        EventCustomerDrinkCrossRef(1L, 4L, 2L, 11),
                        EventCustomerDrinkCrossRef(1L, 4L, 3L, 12),
                        EventCustomerDrinkCrossRef(1L, 4L, 4L, 13),
                        EventCustomerDrinkCrossRef(1L, 4L, 5L, 14),
                        EventCustomerDrinkCrossRef(1L, 5L, 1L, 1),
                        EventCustomerDrinkCrossRef(1L, 5L, 2L, 11),
                        EventCustomerDrinkCrossRef(1L, 5L, 3L, 12),
                        EventCustomerDrinkCrossRef(1L, 5L, 4L, 13),
                        EventCustomerDrinkCrossRef(1L, 5L, 5L, 14)
                    )
                }
            }
        }
    }

    companion object {
        private var instance: AppDatabase? = null
        fun initialize(context: Context) {
            Database(context)
        }

        fun getInstance(): AppDatabase {
            checkNotNull(instance) { "Database has not been initialized. Use initialize(Context) first" }
            return instance!!
        }
    }

    init {
//        TODO: For development:
      context.applicationContext.deleteDatabase("thekenapp-db")
        instance = Room.databaseBuilder(context, AppDatabase::class.java, "thekenapp-db").build()
//        TODO: For development:
      populateInitialData()
    }
}