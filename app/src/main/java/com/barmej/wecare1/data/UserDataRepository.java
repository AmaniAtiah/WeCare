package com.barmej.wecare1.data;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.barmej.wecare1.data.database.AppDatabase;
import com.barmej.wecare1.data.entity.UserData;
import com.barmej.wecare1.utils.AppExecutor;

import java.util.List;

public class UserDataRepository {

    private static final String TAG = UserDataRepository.class.getSimpleName();

    private static UserDataRepository sInstance;
    private static final Object LOCK = new Object();

    private AppDatabase mAppDataBase;
    private AppExecutor mAppExecutor;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static synchronized UserDataRepository getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                if (sInstance == null)
                    sInstance = new UserDataRepository(context.getApplicationContext());
            }
        }
        return sInstance;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private UserDataRepository(Context context) {

        mAppDataBase = AppDatabase.getsInstance(context);
        mAppExecutor = AppExecutor.getInstance();
    }

    public void addUserData(final UserData userData) {

        if(userData != null) {
            mAppExecutor.getDiskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mAppDataBase.userDataDao().addData(userData);
                }
            });
        }
    }

    public void updateUserData(final UserData userData) {
        if(userData != null) {
            mAppExecutor.getDiskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mAppDataBase.userDataDao().updateData(userData);
                }
            });
        }
    }

    public LiveData<UserData> getUserData(String date) {
        final LiveData<UserData> userDataLiveData = mAppDataBase.userDataDao().getUserData(date);
        return userDataLiveData;
    }

    public LiveData<List<UserData>> getAllUserData() {
        final LiveData<List<UserData>> allUserData = mAppDataBase.userDataDao().getAllUserData();
        return allUserData;
    }

    public LiveData<List<UserData>> getUserDateInPeriod(String date) {
        final LiveData<List<UserData>> userDateInPeriod = mAppDataBase.userDataDao().getUserDateInPeriod(date);
        return userDateInPeriod;
    }

}
