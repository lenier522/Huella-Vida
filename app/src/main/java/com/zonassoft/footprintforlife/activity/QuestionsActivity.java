package com.zonassoft.footprintforlife.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.zonassoft.footprintforlife.R;
import com.zonassoft.footprintforlife.calculator.Generator;
import com.zonassoft.footprintforlife.connection.API;
import com.zonassoft.footprintforlife.connection.RestAdapter;
import com.zonassoft.footprintforlife.connection.response.ResponseIndicators;
import com.zonassoft.footprintforlife.data.ThisApp;
import com.zonassoft.footprintforlife.fragment.DialogHelpFragment;
import com.zonassoft.footprintforlife.fragment.DialogIntroFragment;
import com.zonassoft.footprintforlife.model.HIndicator;
import com.zonassoft.footprintforlife.model.HIndicatorModel;
import com.zonassoft.footprintforlife.model.HProfileModel;
import com.zonassoft.footprintforlife.model.HResults;
import com.zonassoft.footprintforlife.model.HUserModel;
import com.zonassoft.footprintforlife.room.AppDatabase;
import com.zonassoft.footprintforlife.room.DAO;
import com.zonassoft.footprintforlife.room.table.IndicatorEntity;
import com.zonassoft.footprintforlife.room.table.ProfileEntity;
import com.zonassoft.footprintforlife.utils.NetworkCheck;
import com.zonassoft.footprintforlife.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuestionsActivity extends AppCompatActivity {


    private EditText et_edad, et_kms, et_kms_bus, et_local, et_gas, et_wood, et_kms_train, et_kms_airplane, et_kms_moto, et_car_persons, et_kwatts, et_kwatts_r, et_peoples, et_gastos, et_avg, et_dep;
    private AppCompatSpinner spn_type_diet;
    private SeekBar seekbar_e, seekbar_g;
    private HProfileModel info;
    private AutoCompleteTextView mCountry;
    private ArrayList<String> listCountries;
    private View parent_view, mProgressView, mBtnOk;
    private Call<ResponseIndicators> callbackIndicators;
    private List<HIndicator> indicators;
    private DAO dao;
    private String[] diets, countries;
    private ArrayList<String> listD;
    private HUserModel user;
    private TextView indicator_e, indicator_g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_questions);
        dao = AppDatabase.getDb(this).getDAO();
        user = ThisApp.get().getUser();
        info = new HProfileModel(1, user.name, user.country, user.id_country, user.getYear());
        initToolbar();
        initComponent();
    }

    private void initToolbar() {
        Tools.setSystemBarColor(this, R.color.mdtp_white);
        Tools.setSystemBarLight(this);
    }

    private void initComponent() {

        et_edad = (EditText) findViewById(R.id.p_age);
        parent_view = findViewById(R.id.parent_view);

        et_car_persons = (EditText) findViewById(R.id.number_p_t);
        et_kms = (EditText) findViewById(R.id.kms_car_t);
        et_kms_bus = (EditText) findViewById(R.id.kms_bus_t);
        et_kms_moto = (EditText) findViewById(R.id.kms_moto_t);
        et_kms_train = (EditText) findViewById(R.id.kms_train_t);

        et_kms_airplane = (EditText) findViewById(R.id.kms_air_t);

        et_kwatts = (EditText) findViewById(R.id.whatts_e);
        et_kwatts_r = (EditText) findViewById(R.id.whatts_r_e);

        et_peoples = (EditText) findViewById(R.id.peoples_h);
        et_gas = (EditText) findViewById(R.id.gas_h);
        et_wood = (EditText) findViewById(R.id.wood_h);

        spn_type_diet = (AppCompatSpinner) findViewById(R.id.type_d);
        et_local = (EditText) findViewById(R.id.local_p);

        et_gastos = (EditText) findViewById(R.id.income);
        et_dep = (EditText) findViewById(R.id.g_dep);

        seekbar_e = (SeekBar) findViewById(R.id.seek_bar_e);
        seekbar_g = (SeekBar) findViewById(R.id.seek_bar_g);

        indicator_e = (TextView) findViewById(R.id.e_indicator);
        indicator_g = (TextView) findViewById(R.id.g_indicator);

        mCountry = findViewById(R.id.p_country);

        String[] countries = getResources().
                getStringArray(R.array.countries);

        listCountries = new ArrayList<>();
        for (String a : countries) {
            listCountries.add(a);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_h_autocomplete, countries);
        mCountry.setAdapter(adapter);

        //init user data
        mCountry.setText(listCountries.get(user.id_country));
        et_edad.setText(user.age + "");

        seekbar_e.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                et_kwatts.setVisibility(View.GONE);
                info.e_kwatts_aux = progress;
                switch (progress) {
                    case 0:
                        indicator_e.setText(getString(R.string.indicator_very_low));
                        break;
                    case 1:
                        indicator_e.setText(getString(R.string.indicator_low));
                        break;
                    case 2:
                        indicator_e.setText(getString(R.string.indicator_meddiun));
                        break;
                    case 3:
                        indicator_e.setText(getString(R.string.indicator_high));
                        break;
                    case 4:
                        indicator_e.setText(getString(R.string.indicator_very_high));
                        break;
                    default:
                        indicator_e.setText("");
                        et_kwatts.setVisibility(View.VISIBLE);
                        et_kwatts.requestFocus();
                        et_kwatts.setText("");
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar_g.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                et_gas.setVisibility(View.GONE);
                info.h_fuel_gas_aux = progress;
                switch (progress) {
                    case 0:
                        indicator_g.setText(getString(R.string.indicator_very_low));
                        break;
                    case 1:
                        indicator_g.setText(getString(R.string.indicator_low));
                        break;
                    case 2:
                        indicator_g.setText(getString(R.string.indicator_meddiun));
                        break;
                    case 3:
                        indicator_g.setText(getString(R.string.indicator_high));
                        break;
                    case 4:
                        indicator_g.setText(getString(R.string.indicator_very_high));
                        break;
                    default:
                        indicator_g.setText("");
                        et_gas.setVisibility(View.VISIBLE);
                        et_gas.requestFocus();
                        et_gas.setText("");
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        diets = getResources().getStringArray(R.array.diet);
        ArrayAdapter<String> array = new ArrayAdapter<>(this, R.layout.item_h_spinner, diets);
        array.setDropDownViewResource(R.layout.item_h_spinner_dropdown);
        spn_type_diet.setAdapter(array);
        spn_type_diet.setSelection(0);

        mProgressView = findViewById(R.id.login_progress);
        mBtnOk = findViewById(R.id.btn_ok);


        listD = new ArrayList<>();
        for (String a : diets) {
            listD.add(a);
        }


        ((ImageView) findViewById(R.id.btn_ok)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        ((ImageButton) findViewById(R.id.bt_info)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPaymentSuccess();
            }
        });

        if (user != null) {
            if (user.isIntro()) {
                showDialogIntro();
                user.setIntro(false);
                ThisApp.get().setUser(user);
            }

        } else {
            showDialogIntro();
        }

        if (info.e_kwatts_aux == 5) {
            indicator_g.setText("");
            et_gas.setVisibility(View.VISIBLE);
            et_gas.setText("");
        }
        if (info.h_fuel_gas_aux == 5) {
            indicator_e.setText("");
            et_kwatts.setVisibility(View.VISIBLE);
            et_kwatts.setText("");
        }

        if (dao.getProfile(1) != null) {
            initData();
        }


    }

    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mBtnOk.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private void requestIndicators(HUserModel user) {
        API api = RestAdapter.createAPI();

        callbackIndicators = api.getIndicators(Tools.getCountry(this, user.id_country));
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
                // Log.e("onFailure", t.getMessage());
                if (!call.isCanceled()) onFailRequest();
            }
        });

    }

    private void displayData(ResponseIndicators resp) {

        int id = dao.getLastIndicatorId();
        indicators = resp.result;
        for (HIndicator a : indicators) {
            //update country data
            if (!a.country.equals("World")) {

                int year = 1959;
                for (double val : a.values) {
                    year++;
                    id++;
                    IndicatorEntity entity = IndicatorEntity.entity(new HIndicatorModel(id, a.code, a.country, val, year));

                    dao.insertIndicator(entity);
                }
            }

        }

        sendDataResult();

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

        String country = mCountry.getText().toString();

        if (!listCountries.contains(country)) {
            cancel = true;
            focusView = mCountry;
            Snackbar.make(parent_view, getString(R.string.register_country_error), Snackbar.LENGTH_LONG).show();
        }

        int age = 0;
        try {
            age = Integer.parseInt(et_edad.getText().toString());
        } catch (Exception ex) {
            cancel = true;
            focusView = et_edad;
        }
        if (age <= 0) {
            cancel = true;
            focusView = et_edad;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to perform the user login attempt.
            showProgress(true);
            int id_country = listCountries.indexOf(country);
            user.age = age;
            user.country = Tools.getCountry(this, id_country);
            user.id_country = id_country;

            ThisApp.get().setUser(user);

            if (dao.countCountryIndicators(Tools.getCountry(this, id_country)) == 0) {

                if (NetworkCheck.isConnect(this)) {
                    requestIndicators(user);
                } else {
                    showProgress(false);
                    Snackbar.make(parent_view, getString(R.string.not_internet), Snackbar.LENGTH_LONG).show();
                }
            } else {
                showProgress(false);
                sendDataResult();
            }


        }
    }


    //fill data after initial calculation from main profil information
    private void initData() {
        info = dao.getProfile(1).original();
        et_kms.setText(info.t_kms_by_car + "");
        et_kms_moto.setText(info.t_kms_by_motobike + "");
        et_kms_bus.setText(info.t_kms_by_bus + "");
        et_kms_train.setText(info.t_kms_by_train + "");
        et_kms_airplane.setText(info.v_kms_by_airplane + "");
        et_car_persons.setText(info.t_persons_cars + "");

        et_kwatts.setText(info.e_kwatts_custom + "");
        et_kwatts_r.setText(info.e_kwatts_r + "");
        seekbar_e.setProgress(info.e_kwatts_aux);

        et_peoples.setText(info.h_number_peoples + "");
        et_gas.setText(info.h_fuel_gas_custom + "");
        seekbar_g.setProgress(info.h_fuel_gas_aux);
        et_wood.setText(info.h_kgrs_wood + "");

        spn_type_diet.setSelection(info.d_type);
        et_local.setText(info.d_local + "");

        et_gastos.setText(info.g_individual + "");
        et_dep.setText((int) info.g_dep + "");


    }


    private void sendDataResult() {


        try {
            info.country = user.country;
            info.id_country = user.id_country;
            info.year = user.getYear();

            if (!et_car_persons.getText().toString().equals("")) {
                info.t_persons_cars = Integer.parseInt(et_car_persons.getText().toString());
                if (info.t_persons_cars <= 0) {
                    info.t_persons_cars = 1;
                }
            } else {
                info.t_persons_cars = 1;
            }

            if (!et_kms.getText().toString().equals("")) {
                info.t_kms_by_car = Double.parseDouble(et_kms.getText().toString());

            } else {
                info.t_kms_by_car = 0;
            }

            if (!et_kms_bus.getText().toString().equals("")) {
                info.t_kms_by_bus = Double.parseDouble(et_kms_bus.getText().toString());

            } else {
                info.t_kms_by_bus = 0;
            }

            if (!et_kms_train.getText().toString().equals("")) {
                info.t_kms_by_train = Double.parseDouble(et_kms_train.getText().toString());

            } else {
                info.t_kms_by_train = 0;
            }

            if (!et_kms_moto.getText().toString().equals("")) {
                info.t_kms_by_motobike = Double.parseDouble(et_kms_moto.getText().toString());

            } else {
                info.t_kms_by_motobike = 0;
            }

            if (!et_kms_airplane.getText().toString().equals("")) {
                info.v_kms_by_airplane = Double.parseDouble(et_kms_airplane.getText().toString());

            } else {
                info.v_kms_by_airplane = 0;
            }

            if (!et_kwatts.getText().toString().equals("")) {
                info.e_kwatts_custom = Double.parseDouble(et_kwatts.getText().toString());

            } else {
                info.e_kwatts_custom = 0;
            }

            if (!et_kwatts_r.getText().toString().equals("")) {
                info.e_kwatts_r = Double.parseDouble(et_kwatts_r.getText().toString());

            } else {
                info.e_kwatts_r = 0;
            }

            if (!et_peoples.getText().toString().equals("")) {
                info.h_number_peoples = Integer.parseInt(et_peoples.getText().toString());
                if (info.h_number_peoples <= 0) {
                    info.h_number_peoples = 1;
                }
            } else {
                info.h_number_peoples = 1;
            }

            if (!et_wood.getText().toString().equals("")) {
                info.h_kgrs_wood = Double.parseDouble(et_wood.getText().toString());

            } else {
                info.h_kgrs_wood = 0;
            }

            if (!et_gas.getText().toString().equals("")) {
                info.h_fuel_gas_custom = Double.parseDouble(et_gas.getText().toString());

            } else {
                info.h_fuel_gas_custom = 0;
            }

            if (!et_local.getText().toString().equals("")) {
                info.d_local = Double.parseDouble(et_local.getText().toString());

            } else {
                info.d_local = 0;
            }


            info.d_type = spn_type_diet.getSelectedItemPosition();


            if (!et_gastos.getText().toString().equals("")) {
                info.g_individual = Double.parseDouble(et_gastos.getText().toString());

            } else {
                info.g_individual = 0;
            }

            info.g_avg = info.g_individual / info.h_number_peoples;

            if (!et_dep.getText().toString().equals("")) {
                info.g_dep = Integer.parseInt(et_dep.getText().toString());
                if (info.g_dep <= 0) {
                    info.g_dep = 1;
                }
            } else {
                info.g_dep = 1;
            }


        } catch (Exception ex) {
        }


        Generator g = new Generator(this);
        HResults event = g.calculateFootprint(info);
        if (event.em_pcy > 0) {
            ProfileEntity profileEntity = ProfileEntity.entity(info);

            dao.insertProfile(profileEntity);

            //2021
            profileEntity.setId(2);
            profileEntity.setYear(2021);
            dao.insertProfile(profileEntity);


            //2022-2025
            profileEntity.setId(3);
            profileEntity.setYear(2025);
            dao.insertProfile(profileEntity);


            //2030-end
            profileEntity.setId(4);
            profileEntity.setYear(2030);
            dao.insertProfile(profileEntity);

            Intent i = new Intent(QuestionsActivity.this, MainActivity.class);
            startActivity(i);
            finish(); // kill current activity
        }


    }

    private void showDialogPaymentSuccess() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogHelpFragment newFragment = new DialogHelpFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();

    }

    private void showDialogIntro() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogIntroFragment newFragment = new DialogIntroFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();

    }

}
