package com.barmej.wecare1.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.barmej.wecare1.R;
import com.barmej.wecare1.data.entity.UserData;
import com.barmej.wecare1.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class DailyDataActivity extends AppCompatActivity {

    Spinner mSpinner;
    private TextView mSpinnerTv;

    ViewModel viewModel;
    private List<UserData> userDataList;
    UserData userData;
    ArrayAdapter<String> mAdapter;
    private List<String> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_data);

        mSpinner = findViewById(R.id.spinner);
        mSpinnerTv = findViewById(R.id.textView4);

        getAllUserData();

        dates = new ArrayList<>();

        mAdapter =  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dates);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id) {
                UserData userData = userDataList.get(position);
                showDailyData(userData);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
    }

    private void showDailyData(UserData userData) {
        this.userData = userData;
        int notification = userData.getTodayNotification();
        mSpinnerTv.setText(String.valueOf(notification));
    }

   private void update(List<UserData> userDataList) {
        this.userDataList = userDataList;
        dates.clear();
        for (UserData userData : userDataList) {
            dates.add(userData.getDate());
        }
        mAdapter.notifyDataSetChanged();
    }

    private void getAllUserData() {
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllUserDataLiveData().observe(this,new Observer<List<UserData>>() {
            @Override
            public void onChanged(List<UserData> userDataList) {
                if(userDataList != null) {
                    update(userDataList);
                }

            }

        });
    }

}


