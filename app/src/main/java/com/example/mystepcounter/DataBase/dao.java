package com.example.mystepcounter.DataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mystepcounter.DataBase.Entity.StepCount;

import java.util.Date;
import java.util.List;

@Dao
public interface dao {
    @Insert
     void insert(StepCount... StepCount);
    @Update
    void updateUser(StepCount... StepCount);

    @Delete
    void deleteUser(StepCount...StepCount);

    @Query("select * from date_counter")
    LiveData<List<StepCount>> getAllUsers();
    @Query("SELECT * FROM date_counter WHERE date = :date LIMIT 1")
    LiveData<StepCount> getUserByDate(String date);

}
