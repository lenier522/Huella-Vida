package com.zonassoft.footprintforlife.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;

import com.zonassoft.footprintforlife.R;
import com.zonassoft.footprintforlife.utils.Tools;
import com.zonassoft.footprintforlife.utils.ViewAnimation;


public class DialogIntroFragment extends DialogFragment {

    private View root_view;
    private View parent_view;
    private NestedScrollView nested_scroll_view;
    private TextView tv_booking_code;
    private ImageView bt_toggle_why, bt_toggle_what,bt_toggle_how,bt_toggle_who;
    private View lyt_expand_why, lyt_expand_what, lyt_expand_how, lyt_expand_who;
    private View lyt_why, lyt_what, lyt_how, lyt_who;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.dialog_h_intro, container, false);



        // nested scrollview
        nested_scroll_view = (NestedScrollView) root_view.findViewById(R.id.nested_scroll_view);

        ((ImageButton) root_view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
            }
        });

        ((View) root_view.findViewById(R.id.what_link_a)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.statista.com/statistics/1267683/cumulative-co2-emissions-fossil-fuel-land-use-forestry-worldwide-by-country/"));
                startActivity(i);
            }
        });
        ((View) root_view.findViewById(R.id.what_link_b)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.statista.com/statistics/276629/global-co2-emissions/#:~:text=Global%20carbon%20dioxide%20emissions%20from,by%20more%20than%2060%20percent"));
                startActivity(i);
            }
        });

        ((View) root_view.findViewById(R.id.what_link_c)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://img.climateinteractive.org/2014/02/A-Trillion-Tons.pdf"));
                startActivity(i);
            }
        });
        ((View) root_view.findViewById(R.id.what_link_d)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://population.un.org/wpp/"));
                startActivity(i);
            }
        });

        ((View) root_view.findViewById(R.id.what_link_e)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.climate.gov/news-features/understanding-climate/climate-change-atmospheric-carbon-dioxide#:~:text=If%20global%20energy%20demand%20continues,close%20to%2050%20million%20years"));
                startActivity(i);
            }
        });
        ((View) root_view.findViewById(R.id.what_link_f)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.peah.it/2018/07/5498/"));
                startActivity(i);
            }
        });



        // why
        bt_toggle_why = (ImageView) root_view.findViewById(R.id.bt_toggle_why);
        lyt_expand_why = (View) root_view.findViewById(R.id.lyt_expand_why);
        lyt_why = (View) root_view.findViewById(R.id.lyt_why);

        lyt_why.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(bt_toggle_why,lyt_expand_why);
            }
        });

        // what
        bt_toggle_what = (ImageView) root_view.findViewById(R.id.bt_toggle_what);
        lyt_expand_what = (View) root_view.findViewById(R.id.lyt_expand_what);
        lyt_what = (View) root_view.findViewById(R.id.lyt_what);

        lyt_what.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(bt_toggle_what,lyt_expand_what);
            }
        });
        // how
        bt_toggle_how = (ImageView) root_view.findViewById(R.id.bt_toggle_how);
        lyt_expand_how = (View) root_view.findViewById(R.id.lyt_expand_how);
        lyt_how = (View) root_view.findViewById(R.id.lyt_how);

        lyt_how.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(bt_toggle_how,lyt_expand_how);
            }
        });
        // who
        bt_toggle_who = (ImageView) root_view.findViewById(R.id.bt_toggle_who);
        lyt_expand_who = (View) root_view.findViewById(R.id.lyt_expand_who);
        lyt_who = (View) root_view.findViewById(R.id.lyt_who);

        lyt_who.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSection(bt_toggle_who,lyt_expand_who);
            }
        });

        return root_view;
    }

    private void toggleSection(View view,View lyt) {
        boolean show = toggleArrow(view);
        if (show) {
            ViewAnimation.expand(lyt, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, lyt);
                }
            });
        } else {
            ViewAnimation.collapse(lyt);
        }
    }

    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(90);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}