package com.example.mystepcounter.DataBase.ViewModel;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

public class ViewModelSingleton {
    private static ViewModelSingleton instance;
    private MyViewModel myViewModel;
    private ViewModelStore viewModelStore;

    private ViewModelSingleton(Application application) {
        this.viewModelStore = new ViewModelStore();
        myViewModel = new ViewModelProvider((ViewModelStoreOwner) () -> viewModelStore,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application))
                .get(MyViewModel.class);
    }

    public static void initialize(Application application) {
        if (instance == null) {
            synchronized (ViewModelSingleton.class) {
                if (instance == null) {
                    instance = new ViewModelSingleton(application);
                }
            }
        }
    }

    public static MyViewModel getMyViewModel() {
        if (instance == null) {
            throw new IllegalStateException("ViewModelSingleton is not initialized, call initialize() method first.");
        }
        return instance.myViewModel;
    }

}
