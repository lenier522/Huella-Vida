package com.zonassoft.footprintforlife.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.zonassoft.footprintforlife.R;
import com.zonassoft.footprintforlife.utils.Tools;


public class StepperActivity extends AppCompatActivity {

    private static final int MAX_STEP = 3;

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private Button btn_got_it;
    private String title_array[] = {
    };
    private String description_array[] = {
    };
    private int about_images_array[] = {
            R.drawable.img_wizard_1,
            R.drawable.img_wizard_2,
            R.drawable.img_wizard_4
    };
    private int color_array[] = {
            R.color.mdtp_white,
            R.color.mdtp_white,
            R.color.mdtp_white
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_stepper);
        Tools.setSystemBarColor(this,R.color.mdtp_white);
        Tools.setSystemBarLight(this);
        initComponent();


    }

    private void initComponent() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        btn_got_it = (Button) findViewById(R.id.btn_got_it);


        title_array = new String[]{
                getString(R.string.steps_know_title),
                getString(R.string.steps_do_title),
                getString(R.string.steps_share_title)
        };
        description_array = new String[]{
                getString(R.string.steps_know_desc),
                getString(R.string.steps_do_desc),
                getString(R.string.steps_share_desc)
        };

        // adding bottom dots
        bottomProgressDots(0);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btn_got_it.setVisibility(View.GONE);
        btn_got_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StepperActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        ((Button) findViewById(R.id.btn_skip)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StepperActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void bottomProgressDots(int current_index) {
        LinearLayout dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        ImageView[] dots = new ImageView[MAX_STEP];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(getResources().getColor(R.color.overlay_dark_30), PorterDuff.Mode.SRC_IN);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current_index].setImageResource(R.drawable.shape_circle);
            dots[current_index].setColorFilter(getResources().getColor(R.color.grey_10), PorterDuff.Mode.SRC_IN);
        }
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(position);
            if (position == title_array.length - 1) {
                btn_got_it.setVisibility(View.VISIBLE);
            } else {
                btn_got_it.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.item_stepper_wizard_color, container, false);
            ((TextView) view.findViewById(R.id.title)).setText(title_array[position]);
            ((TextView) view.findViewById(R.id.description)).setText(description_array[position]);
            ((ImageView) view.findViewById(R.id.image)).setImageResource(about_images_array[position]);
            ((RelativeLayout) view.findViewById(R.id.lyt_parent)).setBackgroundColor(getResources().getColor(color_array[position]));
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return title_array.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}