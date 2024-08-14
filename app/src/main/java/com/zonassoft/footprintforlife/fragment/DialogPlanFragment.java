package com.zonassoft.footprintforlife.fragment;

import android.app.Dialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.zonassoft.footprintforlife.R;
import com.zonassoft.footprintforlife.calculator.Generator;
import com.zonassoft.footprintforlife.connection.API;
import com.zonassoft.footprintforlife.connection.RestAdapter;
import com.zonassoft.footprintforlife.connection.response.ResponseIndicators;
import com.zonassoft.footprintforlife.data.ThisApp;
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


public class DialogPlanFragment extends DialogFragment {

    public CallbackResult callbackResult;


    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    private DAO dao;
    private ArrayList<String> listD;
    private AutoCompleteTextView mCountry;
    private ArrayList<String> listCountries;
    private int request_code = 0;
    private View root_view, parent_view, mProgressView, mBtnOk;
    ;
    private Button spn_from_date, spn_from_time;
    private Button spn_to_date, spn_to_time;
    private TextView tv_email;
    private EditText et_kms, et_kms_bus, et_local, et_gas, et_wood, et_kms_train, et_kms_airplane, et_kms_moto, et_car_persons, et_kwatts, et_kwatts_r, et_peoples, et_gastos, et_avg, et_dep;
    private AppCompatCheckBox cb_allday;
    private AppCompatSpinner spn_year_birt, spn_year_reference, spn_type_diet;
    private SeekBar seekbar_e, seekbar_g;
    private HProfileModel info, before;
    private HUserModel user;
    private int plan = 1, id_country;
    TextView indicator_e, indicator_g;
    private Call<ResponseIndicators> callbackIndicators;
    private List<HIndicator> indicators;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root_view = inflater.inflate(R.layout.dialog_h_plan, container, false);
        parent_view = root_view.findViewById(R.id.parent_view);

        dao = AppDatabase.getDb(getContext()).getDAO();

        user = ThisApp.get().getUser();
        info = dao.getProfile(this.plan).original();
        id_country = info.id_country;
        before = dao.getProfile(this.plan - 1).original();
        String plant = "PLAN 2018-2020";
        switch (this.plan) {
            case 3:
                plant = "PLAN 2022-2025";
                break;
            case 4:
                plant = "PLAN 2025-2030";
                break;
            default:
                plant = "PLAN 2021";
                break;
        }
        ((TextView) root_view.findViewById(R.id.title_plan)).setText(plant);


        ((TextView) root_view.findViewById(R.id.okms_car_t)).setText(before.t_kms_by_car + " " + getString(R.string.um_km_month));
        ((TextView) root_view.findViewById(R.id.okms_bus_t)).setText(before.t_kms_by_bus + " " + getString(R.string.um_km_month));
        ((TextView) root_view.findViewById(R.id.okms_moto_t)).setText(before.t_kms_by_motobike + " " + getString(R.string.um_km_month));
        ((TextView) root_view.findViewById(R.id.okms_train_t)).setText(before.t_kms_by_train + " " + getString(R.string.um_km_year));
        ((TextView) root_view.findViewById(R.id.okms_air_t)).setText(before.v_kms_by_airplane + " " + getString(R.string.um_km_year));

        ((TextView) root_view.findViewById(R.id.onumber_p_t)).setText(before.t_persons_cars + " " + getString(R.string.um_peoples));
        switch (before.e_kwatts_aux) {
            case 0:
                ((TextView) root_view.findViewById(R.id.owhatts_e)).setText(getString(R.string.indicator_very_low));
                break;
            case 1:
                ((TextView) root_view.findViewById(R.id.owhatts_e)).setText(getString(R.string.indicator_low));
                break;
            case 2:
                ((TextView) root_view.findViewById(R.id.owhatts_e)).setText(getString(R.string.indicator_meddiun));
                break;
            case 3:
                ((TextView) root_view.findViewById(R.id.owhatts_e)).setText(getString(R.string.indicator_high));
                break;
            case 4:
                ((TextView) root_view.findViewById(R.id.owhatts_e)).setText(getString(R.string.indicator_very_high));
                break;
            default:
                ((TextView) root_view.findViewById(R.id.owhatts_e)).setText(before.e_kwatts_custom + " " + getString(R.string.um_kw_month));
                break;
        }

        ((TextView) root_view.findViewById(R.id.owhatts_r_e)).setText(before.e_kwatts_r + " " + getString(R.string.um_kw_month));

        ((TextView) root_view.findViewById(R.id.opeoples_h)).setText(before.h_number_peoples + " " + getString(R.string.um_peoples));
        switch (before.h_fuel_gas_aux) {
            case 0:
                ((TextView) root_view.findViewById(R.id.ogas_h)).setText(getString(R.string.indicator_very_low));
                break;
            case 1:
                ((TextView) root_view.findViewById(R.id.ogas_h)).setText(getString(R.string.indicator_low));
                break;
            case 2:
                ((TextView) root_view.findViewById(R.id.ogas_h)).setText(getString(R.string.indicator_meddiun));
                break;
            case 3:
                ((TextView) root_view.findViewById(R.id.ogas_h)).setText(getString(R.string.indicator_high));
                break;
            case 4:
                ((TextView) root_view.findViewById(R.id.ogas_h)).setText(getString(R.string.indicator_very_high));
                break;
            default:
                ((TextView) root_view.findViewById(R.id.ogas_h)).setText(before.h_fuel_gas_custom + " " + getString(R.string.um_l_month));
                break;
        }
        ((TextView) root_view.findViewById(R.id.owood_h)).setText(before.h_kgrs_wood + " " + getString(R.string.um_kg_month));


        ((TextView) root_view.findViewById(R.id.olocal_p)).setText(before.d_local + " %");

        ((TextView) root_view.findViewById(R.id.oincome)).setText(before.g_individual + " ");
        ((TextView) root_view.findViewById(R.id.odep)).setText(before.g_dep + " ");


        et_car_persons = (EditText) root_view.findViewById(R.id.number_p_t);
        et_kms = (EditText) root_view.findViewById(R.id.kms_car_t);
        et_kms_bus = (EditText) root_view.findViewById(R.id.kms_bus_t);
        et_kms_moto = (EditText) root_view.findViewById(R.id.kms_moto_t);
        et_kms_train = (EditText) root_view.findViewById(R.id.kms_train_t);

        et_kms_airplane = (EditText) root_view.findViewById(R.id.kms_air_t);

        et_kwatts = (EditText) root_view.findViewById(R.id.whatts_e);
        et_kwatts_r = (EditText) root_view.findViewById(R.id.whatts_r_e);

        et_peoples = (EditText) root_view.findViewById(R.id.peoples_h);
        et_gas = (EditText) root_view.findViewById(R.id.gas_h);
        et_wood = (EditText) root_view.findViewById(R.id.wood_h);

        spn_type_diet = (AppCompatSpinner) root_view.findViewById(R.id.type_d);
        et_local = (EditText) root_view.findViewById(R.id.local_p);

        et_gastos = (EditText) root_view.findViewById(R.id.income);
        et_dep = (EditText) root_view.findViewById(R.id.g_dep);

        seekbar_e = (SeekBar) root_view.findViewById(R.id.seek_bar_e);
        seekbar_g = (SeekBar) root_view.findViewById(R.id.seek_bar_g);

        indicator_e = (TextView) root_view.findViewById(R.id.e_indicator);
        indicator_g = (TextView) root_view.findViewById(R.id.g_indicator);

        mCountry = root_view.findViewById(R.id.p_country);

        String[] countries = getResources().
                getStringArray(R.array.countries);

        listCountries = new ArrayList<>();
        for (String a : countries) {
            listCountries.add(a);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_h_autocomplete, countries);
        mCountry.setAdapter(adapter);
        //init country data
        mCountry.setText(listCountries.get(info.id_country));

        mProgressView = root_view.findViewById(R.id.login_progress);
        mBtnOk = root_view.findViewById(R.id.bt_save);

        ((TextView) root_view.findViewById(R.id.op_country)).setText(listCountries.get(before.id_country));

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

        final String[] diets = getResources().getStringArray(R.array.diet);
        ArrayAdapter<String> array = new ArrayAdapter<>(getActivity(), R.layout.item_h_spinner, diets);
        array.setDropDownViewResource(R.layout.item_h_spinner_dropdown);
        spn_type_diet.setAdapter(array);
        spn_type_diet.setSelection(0);
        listD = new ArrayList<>();
        for (String a : diets) {
            listD.add(a);
        }

        ((TextView) root_view.findViewById(R.id.otype_d)).setText(listD.get(before.d_type));

        ((ImageButton) root_view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ((Button) root_view.findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
                //dismiss();
            }
        });


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

        initData();


        return root_view;
    }

    private void initData() {

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

    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mBtnOk.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private void requestIndicators() {
        API api = RestAdapter.createAPI();

        callbackIndicators = api.getIndicators(Tools.getCountry(getContext(), id_country));
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
            //Update country data
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

        if (NetworkCheck.isConnect(getContext())) {
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


        if (cancel) {
            // There was an error; don't attempt login and focus the first form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to perform the user login attempt.
            showProgress(true);

            id_country = listCountries.indexOf(country);

            if (dao.countCountryIndicators(Tools.getCountry(getContext(), id_country)) == 0) {

                if (NetworkCheck.isConnect(getContext())) {
                    requestIndicators();
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

    private void sendDataResult() {

        try {
            //update country code and name
            info.id_country = id_country;
            info.country = Tools.getCountry(getContext(), id_country);

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
        Generator g = new Generator(getContext());
        HResults event = g.calculateFootprint(info);
        if (event.em_pcy > 0) {
            ProfileEntity profileEntity = ProfileEntity.entity(info);
            dao.insertProfile(profileEntity);
           /* if (info.id < 3) {
                //2022-2025
                profileEntity.setId(3);
                profileEntity.setYear(2025);
                dao.insertProfile(profileEntity);
            }

            if (info.id < 4) {

                //2030-end
                profileEntity.setId(4);
                profileEntity.setYear(2030);
                dao.insertProfile(profileEntity);
            }*/
        }

        if (callbackResult != null) {
            callbackResult.sendResult(request_code, event);
        }
        dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void setRequestCode(int request_code) {
        this.request_code = request_code;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }


    public interface CallbackResult {
        void sendResult(int requestCode, Object obj);
    }

}