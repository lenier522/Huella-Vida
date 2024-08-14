package com.zonassoft.footprintforlife.data;

import android.app.Application;
import android.location.Location;

import com.zonassoft.footprintforlife.model.HUserModel;


public class ThisApp extends Application {

    private static ThisApp mInstance;

    public static synchronized ThisApp get() {
        return mInstance;
    }

    private int fcm_count = 0;
    private final int FCM_MAX_COUNT = 10;
    private SharedPref shared_pref;
    private HUserModel user = null;
    private Location location = null;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        shared_pref = new SharedPref(this);
        user = shared_pref.getUser();
    }



    public HUserModel getUser() {
        return user;
    }


    public void setUser(HUserModel user) {
        shared_pref.saveUser(user);
        this.user = user;
    }

    public int limit() {
        return shared_pref.actionClickOffer();
    }

    public void setFirst () {
        shared_pref.setFirstLaunch(false);
    }

    public boolean isFirst() {
        return shared_pref.isFirstLaunch();
    }

    public boolean isLogin() {
        return user != null;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


}
