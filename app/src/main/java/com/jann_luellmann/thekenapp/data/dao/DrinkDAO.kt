package com.jann_luellmann.thekenapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jann_luellmann.thekenapp.data.model.Drink

@Dao
abstract class DrinkDAO {
    @get:Query("SELECT * FROM drink")
    abstract val all: LiveData<List<Drink?>>?

    @Query("SELECT * FROM drink WHERE drinkId IN (:drinkIds)")
    abstract fun loadAllByIds(drinkIds: LongArray?): LiveData<List<Drink?>?>?

    @Query("SELECT * FROM drink WHERE name LIKE :name LIMIT 1")
    abstract fun findByName(name: String?): LiveData<Drink?>?

    @Insert
    abstract fun insert(drink: Drink?): Long

    @Insert
    abstract fun insertAll(vararg drinks: Drink?)

    @Update
    abstract fun update(drink: Drink?)

    @Delete
    abstract fun delete(drink: Drink?)
}