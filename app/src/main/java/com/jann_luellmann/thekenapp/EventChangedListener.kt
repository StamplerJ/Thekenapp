package com.jann_luellmann.thekenapp

import androidx.lifecycle.LiveData

interface EventChangedListener {
    fun onEventUpdated(updatedId: LiveData<Long>)
}