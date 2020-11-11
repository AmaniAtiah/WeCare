package com.barmej.wecare1.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.barmej.wecare1.R;
import com.barmej.wecare1.data.entity.UserData;
import com.barmej.wecare1.utils.NotificationUtils;
import com.barmej.wecare1.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView todayNoTextView;
    private Button dailyDataButton;
    private TextView averageTextView;

    UserData userData;
    ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dailyDataButton = findViewById(R.id.daily_data);
        todayNoTextView = findViewById(R.id.today_notification);
        averageTextView = findViewById(R.id.average);

        dailyDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DailyDataActivity.class);
                startActivity(intent);

            }
        });

        getUserData();

        getUserDataInPeriod();
        NotificationUtils.createHealthNotificationChannel(this);
    }

    private void update(UserData userData) {
        this.userData = userData;
        showTodayNotification();
    }

    private void showTodayNotification(){
        int notification = userData.getTodayNotification();
        todayNoTextView.setText(String.valueOf(notification));
    }

    private void updatePeriod(List<UserData> userDataList) {
        calculateAverage(userDataList);
    }

    private void calculateAverage(List<UserData> userDataList) {
        double totalNumberOfNotification = 0;
        double avg;
        for (UserData userData : userDataList) {
            totalNumberOfNotification += userData.getTodayNotification();
        }
        avg = totalNumberOfNotification / userDataList.size();
        averageTextView.setText(String.valueOf(avg));

    }

    private void getUserData() {
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getUserDataLiveData().observe(this, new Observer<UserData>() {
            @Override
            public void onChanged(UserData userData) {
                if(userData != null) {
                    update(userData);
                }
            }
        });
    }

    private void getUserDataInPeriod() {
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getUserDataInPeriod().observe(this,new Observer<List<UserData>>() {
            @Override
            public void onChanged(List<UserData> userDataList) {
                if (userDataList!= null) {
                    updatePeriod(userDataList);
                }
            }
        });

    }

}

