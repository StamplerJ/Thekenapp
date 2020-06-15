package com.jann_luellmann.thekenapp.data.view_model;

import com.jann_luellmann.thekenapp.data.db.Database;
import com.jann_luellmann.thekenapp.data.model.Drink;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel<T> extends ViewModel {

    protected Database.AppDatabase db;
    protected Executor executor;

    public BaseViewModel() {
        super();
        db = Database.getInstance();
        executor = Executors.newSingleThreadExecutor();
    }

    public abstract LiveData<List<T>> findAll();

    public abstract void insert(long eventId, T t);
}
