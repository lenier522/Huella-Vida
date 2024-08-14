package com.zonassoft.footprintforlife.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.zonassoft.footprintforlife.model.HUserModel;

public class SharedPref {

    private Context context;
    private SharedPreferences sharedPreferences;

    private static final String FIRST_LAUNCH = "_.FIRST_LAUNCH";
    private static final String CLICK_OFFER = "_.MAX_CLICK_OFFER";
    private static final String CLICK_MAX = "_.MAX_CLICK_LICENSE";


    private static final int MAX_CLICK_OFFER = 50;

    public SharedPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("MAIN_PREF", Context.MODE_PRIVATE);
    }


    // Preference for User Login
    public void saveUser(HUserModel user) {
        String str = new Gson().toJson(user);
        sharedPreferences.edit().putString("USER_PREF_KEY", str).apply();
    }


    public HUserModel getUser() {
        String str = sharedPreferences.getString("USER_PREF_KEY", null);
        if (str != null) {
            return new Gson().fromJson(str, HUserModel.class);
        }
        return null;
    }


    public void setFirstLaunch(boolean flag) {
        sharedPreferences.edit().putBoolean(FIRST_LAUNCH, flag).apply();
    }

    /**
     * Count times open
     *
     * @return total times
     */
    public boolean isFirstLaunch() {
        return sharedPreferences.getBoolean(FIRST_LAUNCH, true);
    }

    /**
     * Count times open
     *
     * @return total times
     */
    public int actionClickOffer() {
        int current = sharedPreferences.getInt(CLICK_OFFER, 1);

        int is_reset = 0;
        current++;

        sharedPreferences.edit().putInt(CLICK_OFFER, current).apply();
        return current;
    }

    // To save dialog permission state
    public void setNeverAskAgain(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getNeverAskAgain(String key) {
        return sharedPreferences.getBoolean(key, false);
    }


}
