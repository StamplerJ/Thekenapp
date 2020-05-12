package com.jann_luellmann.thekenapp.view;

import androidx.annotation.Nullable;

public class Cell {

    @Nullable
    private Object data;

    public Cell(@Nullable Object data) {
        this.data = data;
    }

    @Nullable
    public String getData() {
        return data.toString();
    }
}