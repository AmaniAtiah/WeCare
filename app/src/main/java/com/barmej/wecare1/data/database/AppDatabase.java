package com.barmej.wecare1.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.barmej.wecare1.data.entity.UserData;
import com.barmej.wecare1.data.database.dao.UserDataDao;

@Database(entities = {UserData.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;
    private static final String DATABASE_NAME = "userData_db";

    public static AppDatabase getsInstance(Context context) {
        if(sInstance == null) {
            synchronized (LOCK) {
                if(sInstance == null){
                    sInstance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME
                    ).allowMainThreadQueries().build();

                }
            }
        }
        return sInstance;
    }

    public abstract UserDataDao userDataDao();

}
