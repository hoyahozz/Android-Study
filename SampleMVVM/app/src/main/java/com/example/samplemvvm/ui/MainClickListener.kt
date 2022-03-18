package com.example.samplemvvm.ui

import com.example.samplemvvm.data.remote.entity.Row

interface MainClickListener {
    fun onListItemClick(item: Row)
}