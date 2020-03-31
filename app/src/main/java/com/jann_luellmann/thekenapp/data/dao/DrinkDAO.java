package com.jann_luellmann.thekenapp.data.dao;

import com.jann_luellmann.thekenapp.data.model.Drink;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DrinkDAO {

    @Query("SELECT * FROM drink")
    LiveData<List<Drink>> getAll();

    @Query("SELECT * FROM drink WHERE id IN (:drinkIds)")
    LiveData<List<Drink>> loadAllByIds(long[] drinkIds);

    @Query("SELECT * FROM drink WHERE name LIKE :name LIMIT 1")
    LiveData<Drink> findByName(String name);

    @Insert
    void insertAll(Drink... drinks);

    @Delete
    void delete(Drink drink);

}
