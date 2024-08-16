package com.example.mystepcounter.DataBase.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.mystepcounter.DataBase.helper.Converter;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Entity(tableName = "date_counter")
@TypeConverters({Converter.class})

public class StepCount {
    @NotNull
    @PrimaryKey
    private String date;
    private int counter;

    public StepCount(String date, int counter) {
        this.date = date;
        this.counter = counter;
    }

    public String getDate() {
        return date;
    }

    public StepCount setDate(String date) {
        this.date = date;
        return this;
    }

    public int getCounter() {
        return counter;
    }

    public StepCount setCounter(int counter) {
        this.counter = counter;
        return this;
    }
}
