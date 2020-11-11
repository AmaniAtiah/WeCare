package com.barmej.wecare1.screen;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class ScreenUtils {

    private static final String TAG = ScreenUtils.class.getSimpleName();

    public static void startScreen(Context context) {
        if (!isMyServiceRunning(context, ScreenStatusWorker.class)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, ScreenStatusWorker.class));
            } else {
                context.startService(new Intent(context, ScreenStatusWorker.class));
            }
        }
    }

    private static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void stop(Context context) {
        WorkManager workManager = WorkManager.getInstance(context);
        workManager.cancelUniqueWork("Worker");
    }

    public static void schedule(Context context) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        PeriodicWorkRequest periodicWorkRequest
                = new PeriodicWorkRequest.Builder(ScreenWorkers.class, 25, TimeUnit.MINUTES)
                .setInitialDelay(25, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();
        WorkManager workManager = WorkManager.getInstance(context);
        workManager.enqueueUniquePeriodicWork("Worker", ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest);

    }
}

