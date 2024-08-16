package com.example.mystepcounter

import android.app.Application
import com.example.mystepcounter.DataBase.ViewModel.ViewModelSingleton

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize the DataManager here
        ViewModelSingleton.initialize(this);


    }
}
