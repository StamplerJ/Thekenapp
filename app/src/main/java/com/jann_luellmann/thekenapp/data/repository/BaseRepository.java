package com.jann_luellmann.thekenapp.data.repository;

import com.jann_luellmann.thekenapp.data.db.Database;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BaseRepository {

    protected Database.AppDatabase db;
    protected Executor executor;

    public BaseRepository() {
        db = Database.getInstance();
        executor = Executors.newSingleThreadExecutor();
    }
}
