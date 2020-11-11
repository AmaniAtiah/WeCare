package com.barmej.wecare1.screen;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.barmej.wecare1.data.UserDataRepository;
import com.barmej.wecare1.data.entity.UserData;
import com.barmej.wecare1.utils.AppExecutor;
import com.barmej.wecare1.utils.NotificationUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScreenWorkers extends Worker {

    public ScreenWorkers(@NonNull Context context,@NonNull WorkerParameters workerParams) {
        super(context,workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        final UserDataRepository repository = UserDataRepository.getInstance(getApplicationContext());

        AppExecutor.getInstance().getMainThread().execute(new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                final String currentDate = simpleDateFormat.format(new Date(calendar.getTimeInMillis()));

                final LiveData<UserData> liveData =  repository.getUserData(currentDate);
                liveData.observeForever(new Observer<UserData>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onChanged(UserData userData) {
                        if(userData == null) {
                            userData = new UserData();
                            userData.setTodayNotification(1);

                            userData.setDate(currentDate);
                            repository.addUserData(userData);

                        } else {
                            int notification = userData.getTodayNotification();
                            userData.setTodayNotification(++notification);
                            repository.updateUserData(userData);
                        }

                        NotificationUtils.showHealthNotification(getApplicationContext(), userData);
                        liveData.removeObserver(this);
                    }


                });
            }
        });

        return Result.success();
    }


}
