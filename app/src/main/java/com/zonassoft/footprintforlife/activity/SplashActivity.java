package com.zonassoft.footprintforlife.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import com.zonassoft.footprintforlife.R;
import com.zonassoft.footprintforlife.connection.response.ResponseIndicators;
import com.zonassoft.footprintforlife.data.ThisApp;
import com.zonassoft.footprintforlife.model.HIndicator;
import com.zonassoft.footprintforlife.model.HUserModel;
import com.zonassoft.footprintforlife.room.AppDatabase;
import com.zonassoft.footprintforlife.room.DAO;
import com.zonassoft.footprintforlife.utils.Tools;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;


public class SplashActivity extends AppCompatActivity {

    private List<HIndicator> indicators;
    private Call<ResponseIndicators> callbackIndicators;
    private DAO dao;
    private HUserModel user;
    private int dealy = 1500;
    private int times = 0;
    private int LIMIT_TIMES = 50;

    View parent_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_splash);
        Tools.setSystemBarColor(this, R.color.mdtp_white);
        Tools.setSystemBarLight(this);
        dao = AppDatabase.getDb(this).getDAO();
        user = ThisApp.get().getUser();
        if (user != null) {
            user.setIntro(true);
            ThisApp.get().setUser(user);
        }


       /* if(ThisApp.get().isFirst()){
            parent_view=findViewById(R.id.parent_view);
            Snackbar.make(parent_view, "You can use this app for "+(50 -ThisApp.get().limit())+ " times", Snackbar.LENGTH_LONG).show();
            dealy=3500;
            ThisApp.get().setFirst();
        }*/


        startActivityMainDelay();
    }


    private void startActivityMainDelay() {


        // Show splash screen for 2 seconds
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, StepperActivity.class);
                if (dao.countIndicators() > 0) {
                    i = new Intent(SplashActivity.this, QuestionsActivity.class);
                }

                if (dao.getProfile(1) != null) {
                    i = new Intent(SplashActivity.this, MainActivity.class);
                }


                startActivity(i);
                finish(); // kill current activity
            }
        };
        new Timer().schedule(task, dealy);
    }


}

