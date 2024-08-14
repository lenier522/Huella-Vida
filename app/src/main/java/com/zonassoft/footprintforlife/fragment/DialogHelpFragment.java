package com.zonassoft.footprintforlife.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.DialogFragment;

import com.zonassoft.footprintforlife.R;


public class DialogHelpFragment extends DialogFragment {

    private View root_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.dialog_h_help, container, false);

        ((ImageButton) root_view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
            }
        });

        return root_view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}