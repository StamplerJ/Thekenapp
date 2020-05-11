package com.jann_luellmann.thekenapp.data.db;

import android.content.Context;

import com.jann_luellmann.thekenapp.data.dao.BoughtDAO;
import com.jann_luellmann.thekenapp.data.dao.CustomerDAO;
import com.jann_luellmann.thekenapp.data.dao.DrinkDAO;
import com.jann_luellmann.thekenapp.data.dao.EventDAO;
import com.jann_luellmann.thekenapp.data.dao.SoldDAO;
import com.jann_luellmann.thekenapp.data.dao.relationship.EventWithCustomersDAO;
import com.jann_luellmann.thekenapp.data.dao.relationship.EventWithDrinksAndCustomersDAO;
import com.jann_luellmann.thekenapp.data.dao.relationship.EventWithDrinksDAO;
import com.jann_luellmann.thekenapp.data.model.Bought;
import com.jann_luellmann.thekenapp.data.model.Customer;
import com.jann_luellmann.thekenapp.data.model.Drink;
import com.jann_luellmann.thekenapp.data.model.Event;
import com.jann_luellmann.thekenapp.data.model.Sold;
import com.jann_luellmann.thekenapp.data.util.Converters;

import java.util.Date;
import java.util.concurrent.Executors;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

public class Database {

    @androidx.room.Database(entities = {Bought.class, Customer.class, Drink.class, Event.class, Sold.class}, version = 1)
    @TypeConverters({Converters.class})
    public static abstract class AppDatabase extends RoomDatabase {
        public abstract BoughtDAO boughtDAO();
        public abstract CustomerDAO customerDAO();
        public abstract DrinkDAO drinkDAO();
        public abstract EventDAO eventDAO();
        public abstract SoldDAO soldDAO();

        // Relationship DAOs
        public abstract EventWithCustomersDAO eventWithCustomersDAO();
        public abstract EventWithDrinksDAO eventWithDrinksDAO();

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
            getInstance().runInTransaction(new Runnable() {
                @Override
                public void run() {
                    getInstance().clearAllTables();

                    getInstance().eventDAO().insertAll(
                            new Event(1L, "Schützenfest", new Date(), 0)
                    );

                    getInstance().customerDAO().insertAll(
                            new Customer(1L, "Jana Körner", 1L),
                            new Customer(2L, "Joachim Sander", 1L),
                            new Customer(3L, "Jann Lüllmann", 1L),
                            new Customer(4L, "Malte Sander", 1L),
                            new Customer(5L, "Marie Lindemann", 1L));

                    getInstance().drinkDAO().insertAll(
                            new Drink(1L, "Wasser", 100, 1l),
                            new Drink(2L, "Bier, Alster", 150, 1l),
                            new Drink(3L, "Cola, Fanta, Sprite", 120, 1l),
                            new Drink(4L, "Charly, Cola-Korn", 250, 1l),
                            new Drink(5L, "Hugo", 200, 1l));
                }
            });
        });
    }
}
