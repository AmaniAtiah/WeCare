package com.barmej.wecare1.screen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context,Intent arg1) {
        ScreenUtils.startScreen(context);
    }
}
