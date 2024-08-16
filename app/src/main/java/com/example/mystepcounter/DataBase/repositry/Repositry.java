package com.example.mystepcounter.DataBase.repositry;

import android.app.Application;

import androidx.lifecycle.LiveData;


import com.example.mystepcounter.DataBase.Entity.StepCount;
import com.example.mystepcounter.DataBase.MyRoomDatabase;
import com.example.mystepcounter.DataBase.dao;

import java.util.Date;
import java.util.List;

public class Repositry {
    dao dao;
    public Repositry(Application application) {
        MyRoomDatabase myRoomDatabase=MyRoomDatabase.getDatabase(application);
        dao=myRoomDatabase.dao();
    }
    public  void insertuser(StepCount...StepCount){
        MyRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(StepCount);
            }
        });
    }
    public  void deletetuser(StepCount...StepCount){
        MyRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteUser(StepCount);
            }
        });
    }
    public LiveData<StepCount> getUserByDate(String date) {
        return dao.getUserByDate(date);
    }
    public LiveData<List<StepCount>> getAllUsers() {
        return dao.getAllUsers();
    }
    public void updateUser(StepCount... StepCount){
        MyRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateUser(StepCount);
            }
        });
    }


}
