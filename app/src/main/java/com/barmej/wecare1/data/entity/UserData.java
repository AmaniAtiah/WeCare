package com.barmej.wecare1.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userdata_table")
public class UserData  {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int todayNotification;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTodayNotification() {
        return todayNotification;
    }

    public void setTodayNotification(int todayNotification) {
        this.todayNotification = todayNotification;
    }




}
