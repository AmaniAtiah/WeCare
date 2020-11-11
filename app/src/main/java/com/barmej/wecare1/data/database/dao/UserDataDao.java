package com.barmej.wecare1.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.barmej.wecare1.data.entity.UserData;

import java.util.List;

@Dao
public interface UserDataDao {
    @Query("SELECT * FROM userdata_table WHERE date = :date")
    LiveData<UserData> getUserData(String date);

    @Query("SELECT * FROM userdata_table")
    LiveData <List<UserData>> getAllUserData();

    @Query("SELECT * FROM userdata_table WHERE date >= :date")
    LiveData<List<UserData>> getUserDateInPeriod(String date);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addData(UserData userData);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateData(UserData userData);


}
