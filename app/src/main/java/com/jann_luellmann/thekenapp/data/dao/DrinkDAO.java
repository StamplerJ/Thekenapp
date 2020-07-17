package com.jann_luellmann.thekenapp.data.dao;

import com.jann_luellmann.thekenapp.data.model.Drink;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public abstract class DrinkDAO {

    @Query("SELECT * FROM drink")
    public abstract LiveData<List<Drink>> getAll();

    @Query("SELECT * FROM drink WHERE drinkId IN (:drinkIds)")
    public abstract LiveData<List<Drink>> loadAllByIds(long[] drinkIds);

    @Query("SELECT * FROM drink WHERE name LIKE :name LIMIT 1")
    public abstract LiveData<Drink> findByName(String name);

    @Insert
    public abstract long insert(Drink drink);

    @Insert
    public abstract void insertAll(Drink... drinks);

    @Update
    public abstract void update(Drink drink);

    @Delete
    public abstract void delete(Drink drink);
}
