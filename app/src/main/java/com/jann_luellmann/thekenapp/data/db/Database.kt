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
                        Event(1L, "Jahreshauptversammlung", GregorianCalendar(2022, Calendar.MAY, 1).time, 0),
                    )
                    customerDAO().insertAll(
                        Customer(1L, "Jana Sander"),
                        Customer(2L, "Joachim Sander"),
                        Customer(3L, "Thorsten Fischer"),
                        Customer(4L, "Malte Sander"),
                        Customer(5L, "Marc Müller"),
                        Customer(6L, "Pascal Wittenberg"),
                        Customer(7L, "Alf Remke"),
                        Customer(8L, "Bernd Gerke"),
                        Customer(9L, "Heinz-Hermann Hansemann"),
                        Customer(10L, "Siegfried Böhm"),
                        Customer(11L, "Ralf Böhm"),
                        Customer(12L, "Kai Wesemann"),
                    )
                    drinkDAO().insertAllBlocking(
                        Drink(1L, "Wasser", 100),
                        Drink(2L, "Bier, Alster", 150),
                        Drink(3L, "Cola, Fanta, Sprite", 120),
                        Drink(4L, "Bacardi Cola", 300),
                        Drink(5L, "Korn, Rot, Grün", 150),
                        Drink(6L, "Charly, Cola-Korn", 250),
                    )
                    eventDrinkCrossDAO().insertAll(
                        EventDrinkCrossRef(1L, 1L),
                        EventDrinkCrossRef(1L, 2L),
                        EventDrinkCrossRef(1L, 3L),
                        EventDrinkCrossRef(1L, 4L),
                        EventDrinkCrossRef(1L, 5L),
                        EventDrinkCrossRef(1L, 6L),
                    )
                    eventCustomerCrossDAO().insertAll(
                        EventCustomerCrossRef(1L, 1L),
                        EventCustomerCrossRef(1L, 2L),
                        EventCustomerCrossRef(1L, 3L),
                        EventCustomerCrossRef(1L, 4L),
                        EventCustomerCrossRef(1L, 5L),
                        EventCustomerCrossRef(1L, 6L),
                        EventCustomerCrossRef(1L, 7L),
                        EventCustomerCrossRef(1L, 8L),
                        EventCustomerCrossRef(1L, 9L),
                        EventCustomerCrossRef(1L, 10L),
                        EventCustomerCrossRef(1L, 11L),
                        EventCustomerCrossRef(1L, 12L),
                    )
                    val list = mutableListOf<EventCustomerDrinkCrossRef>().apply {
                        for (i in 1L..12L) {
                            add(EventCustomerDrinkCrossRef(1L, i, 1L, 0))
                            add(EventCustomerDrinkCrossRef(1L, i, 2L, 0))
                            add(EventCustomerDrinkCrossRef(1L, i, 3L, 0))
                            add(EventCustomerDrinkCrossRef(1L, i, 4L, 0))
                            add(EventCustomerDrinkCrossRef(1L, i, 5L, 0))
                            add(EventCustomerDrinkCrossRef(1L, i, 6L, 0))
                        }
                    }
                    list.forEach {
                        eventAndCustomerWithDrinksDAO().insert(it)
                    }
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
//        context.applicationContext.deleteDatabase("thekenapp-db")
        instance = Room.databaseBuilder(context, AppDatabase::class.java, "thekenapp-db").build()
//        TODO: For development:
//        populateInitialData()
    }
}