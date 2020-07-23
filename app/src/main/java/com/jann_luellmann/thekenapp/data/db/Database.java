package com.jann_luellmann.thekenapp.data.db;

import android.content.Context;

import com.jann_luellmann.thekenapp.data.dao.BoughtDAO;
import com.jann_luellmann.thekenapp.data.dao.CustomerDAO;
import com.jann_luellmann.thekenapp.data.dao.DrinkDAO;
import com.jann_luellmann.thekenapp.data.dao.EventCustomerCrossDAO;
import com.jann_luellmann.thekenapp.data.dao.EventDAO;
import com.jann_luellmann.thekenapp.data.dao.EventDrinkCrossDAO;
import com.jann_luellmann.thekenapp.data.dao.relationship.EventWithCustomersDAO;
import com.jann_luellmann.thekenapp.data.dao.relationship.EventWithDrinksAndCustomersDAO;
import com.jann_luellmann.thekenapp.data.dao.relationship.EventWithDrinksDAO;
import com.jann_luellmann.thekenapp.data.model.Bought;
import com.jann_luellmann.thekenapp.data.model.Customer;
import com.jann_luellmann.thekenapp.data.model.Drink;
import com.jann_luellmann.thekenapp.data.model.Event;
import com.jann_luellmann.thekenapp.data.model.EventCustomerCrossRef;
import com.jann_luellmann.thekenapp.data.model.EventDrinkCrossRef;
import com.jann_luellmann.thekenapp.data.util.Converters;

import java.util.Date;
import java.util.concurrent.Executors;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

public class Database {

    @androidx.room.Database(entities = {Bought.class, Customer.class, Drink.class, Event.class, EventCustomerCrossRef.class, EventDrinkCrossRef.class}, version = 1)
    @TypeConverters({Converters.class})
    public static abstract class AppDatabase extends RoomDatabase {
        public abstract BoughtDAO boughtDAO();
        public abstract CustomerDAO customerDAO();
        public abstract DrinkDAO drinkDAO();
        public abstract EventDAO eventDAO();

        // Relationship DAOs
        public abstract EventWithCustomersDAO eventWithCustomersDAO();
        public abstract EventWithDrinksDAO eventWithDrinksDAO();

        public abstract EventDrinkCrossDAO eventDrinkCrossDAO();
        public abstract EventCustomerCrossDAO eventCustomerCrossDAO();

        public abstract EventWithDrinksAndCustomersDAO eventWithDrinksAndCustomersDAO();
    }

    private static AppDatabase instance;

    private Database(Context context){
        context.getApplicationContext().deleteDatabase("thekenapp-db");
        instance = Room.databaseBuilder(context, AppDatabase.class, "thekenapp-db").build();
        populateInitialData();
    }

    public static void initialize(Context context) {
        new Database(context);
    }

    public static AppDatabase getInstance(){
        if (instance == null)
            throw new IllegalStateException("Database has not been initialized. Use initialize(Context context) first");

        return instance;
    }

    private void populateInitialData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            getInstance().runInTransaction(() -> {
                getInstance().clearAllTables();

                getInstance().eventDAO().insertAll(
                        new Event(1L, "Schützenfest 1. Tag", new Date(), 0),
                        new Event(2L, "Schützenfest 2. Tag", new Date(), 0)
                );

                getInstance().customerDAO().insertAll(
                        new Customer(1L, "Jana Körner"),
                        new Customer(2L, "Joachim Sander"),
                        new Customer(3L, "Jann Lüllmann"),
                        new Customer(4L, "Malte Sander"),
                        new Customer(5L, "Marc Müller"),
                        new Customer(6L, "Pascal Wittenberg"),
                        new Customer(7L, "Tim Tom"),
                        new Customer(8L, "Marie Lindemann"),
                        new Customer(9L, "Heinz-Hermann Hansemann Sen."));

                getInstance().drinkDAO().insertAll(
                        new Drink(1L, "Wasser", 100),
                        new Drink(2L, "Bier, Alster", 150),
                        new Drink(3L, "Cola, Fanta, Sprite", 120),
                        new Drink(5L, "Barcadi Cola", 300),
                        new Drink(6L, "Cocktail", 250),
                        new Drink(9L, "Hugo", 200),
                        new Drink(16L, "Cocktail", 250),
                        new Drink(17L, "Charly, Cola-Korn", 250),
                        new Drink(18L, "Charly, Cola-Korn", 300));

                getInstance().eventDrinkCrossDAO().insertAll(
                        new EventDrinkCrossRef(1L, 1L),
                        new EventDrinkCrossRef(1L, 2L),
                        new EventDrinkCrossRef(1L, 3L),
                        new EventDrinkCrossRef(1L, 5L),
                        new EventDrinkCrossRef(2L, 1L),
                        new EventDrinkCrossRef(2L, 2L),
                        new EventDrinkCrossRef(2L, 5L),
                        new EventDrinkCrossRef(2L, 3L)
                );

                getInstance().eventCustomerCrossDAO().insertAll(
                        new EventCustomerCrossRef(1L, 1L),
                        new EventCustomerCrossRef(1L, 2L),
                        new EventCustomerCrossRef(1L, 3L),
                        new EventCustomerCrossRef(1L, 4L),
                        new EventCustomerCrossRef(1L, 5L),
                        new EventCustomerCrossRef(2L, 6L),
                        new EventCustomerCrossRef(2L, 7L),
                        new EventCustomerCrossRef(2L, 1L),
                        new EventCustomerCrossRef(2L, 2L)
                );

                getInstance().boughtDAO().insertAll(
                        new Bought(1L, 1L, 3),
                        new Bought(1L, 2L, 4),
                        new Bought(1L, 3L, 6),
                        new Bought(5L, 5L, 5),
                        new Bought(2L, 1L, 2),
                        new Bought(2L, 3L, 0),
                        new Bought(2L, 2L, 18)
                );
            });
        });
    }
}
