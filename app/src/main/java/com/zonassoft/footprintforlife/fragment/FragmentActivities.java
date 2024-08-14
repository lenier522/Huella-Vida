package com.zonassoft.footprintforlife.fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.zonassoft.footprintforlife.R;
import com.zonassoft.footprintforlife.adapter.AdapterListActivities;
import com.zonassoft.footprintforlife.data.DataGenerator;
import com.zonassoft.footprintforlife.model.HActivity;

import java.util.List;


public class FragmentActivities extends Fragment {

    private RecyclerView recyclerView;
    private AdapterListActivities mAdapter;

    public FragmentActivities() {
    }

    public static FragmentActivities newInstance() {
        FragmentActivities fragment = new FragmentActivities();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_h_activities, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);

        List<HActivity> items = DataGenerator.getActivitiesData(getContext(), 0);
        items.add(0, new HActivity(getString(R.string.activities_title_a), getString(R.string.activities_intro_a), true));
        items.add(new HActivity(getString(R.string.activities_title_b), "", true));
        items.addAll(DataGenerator.getActivitiesData(getContext(), 1));


        //set data and list adapter
        mAdapter = new AdapterListActivities(getContext(), items);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListActivities.OnItemClickListener() {
            @Override
            public void onItemClick(View view, HActivity obj, int position) {
                showDialog(obj);
            }
        });


        return root;
    }


    private void showDialog(HActivity obj) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_h_task);
        ((ImageView) dialog.findViewById(R.id.dialog_img)).setImageDrawable(getResources().getDrawable(obj.image));
        ((TextView) dialog.findViewById(R.id.description)).setText(obj.subtitle);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        ((Button) dialog.findViewById(R.id.bt_join)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}