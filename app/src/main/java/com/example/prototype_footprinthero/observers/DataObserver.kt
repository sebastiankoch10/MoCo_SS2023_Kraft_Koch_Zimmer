package com.example.prototype_footprinthero.observers

import androidx.compose.runtime.Composable

interface DataObserver {
    @Composable
    fun onDataChangedFromObserver()
}