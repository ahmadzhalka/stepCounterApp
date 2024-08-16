package com.example.mystepcounter.DataBase.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.mystepcounter.DataBase.Entity.StepCount;
import com.example.mystepcounter.DataBase.repositry.Repositry;
import com.example.mystepcounter.Steps.PatDetector;

import java.util.List;

public class MyViewModel extends AndroidViewModel {
    Repositry repositry;
    private static boolean isInitialized = false;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repositry=new Repositry(application);
    }
    public  void insertuser(StepCount...StepCount){
        repositry.insertuser(StepCount);

    }
    public void delete(StepCount...StepCount){
        repositry.deletetuser(StepCount);
    }
    public LiveData<StepCount> getUserByDate(String date) {
        return repositry.getUserByDate(date);
    }
    public LiveData<List<StepCount>> getAllUsers() {
        return repositry.getAllUsers();
    }
    public void updateUser(StepCount... StepCount){
            repositry.updateUser(StepCount);
    }
}
