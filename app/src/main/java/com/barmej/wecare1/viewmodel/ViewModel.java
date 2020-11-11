package com.barmej.wecare1.viewmodel;

import android.app.Application;
import android.content.Context;

import android.os.Build;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.barmej.wecare1.data.UserDataRepository;
import com.barmej.wecare1.screen.ScreenUtils;
import com.barmej.wecare1.data.entity.UserData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ViewModel extends AndroidViewModel {
    private UserDataRepository userDataRepository;
    private Context mContext;
    private LiveData<UserData> userDataLiveData;
    private LiveData <List<UserData>> allUserData;
    private LiveData <List<UserData>> userDateInPeriod;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewModel(@NonNull Application application) {
        super(application);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        final String currentDate = simpleDateFormat.format(new Date(calendar.getTimeInMillis()));

        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        final String date = simpleDateFormat.format(new Date(calendar.getTimeInMillis()));

        userDataRepository = UserDataRepository.getInstance(application);
        userDataLiveData = userDataRepository.getUserData(currentDate);
        allUserData = userDataRepository.getAllUserData();
        userDateInPeriod = userDataRepository.getUserDateInPeriod(date);

        mContext = application.getApplicationContext();
        ScreenUtils.startScreen(mContext);
    }

    public LiveData<UserData> getUserDataLiveData(){
        return userDataLiveData;
    }

    public LiveData<List<UserData>> getAllUserDataLiveData(){
        return allUserData;
    }

    public LiveData<List<UserData>> getUserDataInPeriod(){
        return userDateInPeriod;
    }


}
