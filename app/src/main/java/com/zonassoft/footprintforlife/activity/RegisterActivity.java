package com.zonassoft.footprintforlife.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.zonassoft.footprintforlife.R;
import com.zonassoft.footprintforlife.connection.API;
import com.zonassoft.footprintforlife.connection.RestAdapter;
import com.zonassoft.footprintforlife.connection.response.ResponseIndicators;
import com.zonassoft.footprintforlife.data.ThisApp;
import com.zonassoft.footprintforlife.model.HIndicator;
import com.zonassoft.footprintforlife.model.HIndicatorModel;
import com.zonassoft.footprintforlife.model.HUserModel;
import com.zonassoft.footprintforlife.room.AppDatabase;
import com.zonassoft.footprintforlife.room.DAO;
import com.zonassoft.footprintforlife.room.table.IndicatorEntity;
import com.zonassoft.footprintforlife.utils.NetworkCheck;
import com.zonassoft.footprintforlife.utils.Tools;
import com.zonassoft.footprintforlife.utils.ViewAnimation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    private List<HIndicator> indicators;
    private Call<ResponseIndicators> callbackIndicators;
    String[] countries;
    private DAO dao;
    private HUserModel user;
    private ArrayList<String> listCountries;

    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView, mCountry;
    private EditText mName, mAge;
    View mProgressView, mLoginFormView, parent_view;

    private String[] array_countries;

    private View back_drop;
    private boolean rotate = false;
    private View lyt_what;
    private View lyt_who;
    private View lyt_how;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_form_profile);

        array_countries = getResources().getStringArray(R.array.countries);
        dao = AppDatabase.getDb(this).getDAO();
        initComponent();
        Tools.setSystemBarColor(this, R.color.ch_fondo_azul);
        Tools.setSystemBarLight(this);
    }

    private void initComponent() {


        // Set up the login form.
        mName = findViewById(R.id.name);
        mAge = findViewById(R.id.age);
        mCountry = findViewById(R.id.country);
        //
        parent_view = findViewById(R.id.parent_view);
        mProgressView = findViewById(R.id.login_progress);
        mLoginFormView = findViewById(R.id.login_form);
        String[] countries = getResources().
                getStringArray(R.array.countries);

        listCountries = new ArrayList<>();
        for (String a : countries) {
            listCountries.add(a);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_h_autocomplete, countries);

        mCountry.setAdapter(adapter);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }

    private void toggleFabMode(View v) {
        rotate = ViewAnimation.rotateFab(v, !rotate);
        if (rotate) {
            ViewAnimation.showIn(lyt_who);
            ViewAnimation.showIn(lyt_what);
            ViewAnimation.showIn(lyt_how);
            back_drop.setVisibility(View.VISIBLE);
        } else {
            ViewAnimation.showOut(lyt_who);
            ViewAnimation.showOut(lyt_what);
            ViewAnimation.showOut(lyt_how);
            back_drop.setVisibility(View.GONE);
        }
    }


    private void showCountriesChoiceDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setSingleChoiceItems(array_countries, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mCountry.setTextColor(Color.BLACK);
                mCountry.setText(array_countries[which]);
            }
        });
        builder.show();
    }

    private void requestIndicators(HUserModel user) {
        API api = RestAdapter.createAPI();

        callbackIndicators = api.getIndicators(Tools.getCountry(this,user.id_country));
        callbackIndicators.enqueue(new Callback<ResponseIndicators>() {
            @Override
            public void onResponse(Call<ResponseIndicators> call, Response<ResponseIndicators> response) {
                ResponseIndicators resp = response.body();
                if (resp != null && resp.status == 200) {
                    showProgress(false);
                    displayData(resp);
                } else {
                    showProgress(false);
                    onFailRequest();
                }
            }

            @Override
            public void onFailure(Call<ResponseIndicators> call, Throwable t) {
                showProgress(false);
                Snackbar.make(parent_view, getString(R.string.not_server), Snackbar.LENGTH_LONG).show();

                if (!call.isCanceled()) onFailRequest();
            }
        });

    }

    private void displayData(ResponseIndicators resp) {

        int id = 1;
        dao.deleteAllIndicators();
        indicators = resp.result;
        for (HIndicator a : indicators
        ) {

            int year = 1959;
            for (double val : a.values
            ) {
                year++;
                id++;
                IndicatorEntity entity = IndicatorEntity.entity(new HIndicatorModel(id, a.code, a.country, val, year));

                dao.insertIndicator(entity);
            }

        }

        Intent i = new Intent(RegisterActivity.this, QuestionsActivity.class);

        startActivity(i);
        finish(); // kill current activity

    }

    private void onFailRequest() {

        if (NetworkCheck.isConnect(this)) {
            Snackbar.make(parent_view, getString(R.string.not_server), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(parent_view, getString(R.string.not_internet), Snackbar.LENGTH_LONG).show();
        }
    }

    private void attemptLogin() {

        // Reset errors.
        boolean cancel = false;
        View focusView = null;
        // Store values at the time of the login attempt.
        String name = mName.getText().toString();
        String country = mCountry.getText().toString();
        if (name.equals("")) {
            cancel = true;
            focusView = mName;
        }
        if (!listCountries.contains(country)) {
            cancel = true;
            focusView = mCountry;
            Snackbar.make(parent_view, "Type a valid country name", Snackbar.LENGTH_LONG).show();
        }

        int age = 0;
        try {
            age = Integer.parseInt(mAge.getText().toString());
        } catch (Exception ex) {
            cancel = true;
            focusView = mAge;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to perform the user login attempt.
            showProgress(true);
            int id_country=listCountries.indexOf(country);
            ThisApp.get().setUser(new HUserModel(name, age, Tools.getCountry(this,id_country),id_country));

            if (NetworkCheck.isConnect(this)) {
                requestIndicators(ThisApp.get().getUser());
            } else {
                showProgress(false);
                Snackbar.make(parent_view, getString(R.string.not_internet), Snackbar.LENGTH_LONG).show();
            }


        }
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mName;
        private final int mAge;
        private final String mCountry;

        public UserLoginTask(String mName, int mAge, String mCountry) {
            this.mName = mName;
            this.mAge = mAge;
            this.mCountry = mCountry;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }


            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                showProgress(false);
                ThisApp.get().setUser(new HUserModel(mName, mAge, mCountry,listCountries.indexOf(mCountry)));
                requestIndicators(ThisApp.get().getUser());
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

}

