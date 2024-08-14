package com.zonassoft.footprintforlife.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zonassoft.footprintforlife.R;
import com.zonassoft.footprintforlife.data.ThisApp;
import com.zonassoft.footprintforlife.fragment.DialogHelpFragment;
import com.zonassoft.footprintforlife.fragment.DialogIntroFragment;
import com.zonassoft.footprintforlife.fragment.FragmentActivities;
import com.zonassoft.footprintforlife.fragment.FragmentMain;
import com.zonassoft.footprintforlife.fragment.FragmentPlanner;
import com.zonassoft.footprintforlife.model.HUserModel;
import com.zonassoft.footprintforlife.room.AppDatabase;
import com.zonassoft.footprintforlife.room.DAO;
import com.zonassoft.footprintforlife.utils.Tools;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private BottomNavigationView navigation;
    private View search_bar;
    protected DAO dao;
    private HUserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main);
        user = ThisApp.get().getUser();
        dao = AppDatabase.getDb(this).getDAO();
        initComponent();
    }



    private void initComponent() {
        search_bar = findViewById(R.id.search_bar);
        mTextMessage = findViewById(R.id.search_text);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_form) {
                    Intent i = new Intent(MainActivity.this, QuestionsActivity.class);
                    startActivity(i);
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    mTextMessage.setText(item.getTitle());
                    displayFragment(new FragmentMain());
                    return true;
                } else if (itemId == R.id.navigation_ways) {
                    mTextMessage.setText(item.getTitle());
                    displayFragment(new FragmentActivities());
                    return true;
                } else if (itemId == R.id.navigation_plan) {
                    mTextMessage.setText(item.getTitle());
                    displayFragment(new FragmentPlanner());
                    return true;
                }

                return false;
            }
        });


        NestedScrollView nested_content = (NestedScrollView) findViewById(R.id.nested_scroll_view);
        nested_content.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY < oldScrollY) { // up
                    animateNavigation(false);
                    animateSearchBar(false);
                }
                if (scrollY > oldScrollY) { // down
                    animateNavigation(true);
                    animateSearchBar(true);
                }
            }
        });



        ((ImageButton) findViewById(R.id.help)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(i);
            }
        });

        Tools.setSystemBarColor(this, R.color.mdtp_white);
        Tools.setSystemBarLight(this);

        if(user!=null){
            if(user.isIntro()){
                showDialogIntro();
                user.setIntro(false);
                ThisApp.get().setUser(user);
            }

        }else{
            showDialogIntro();
        }

        navigation.setSelectedItemId(R.id.navigation_profile);


    }

    private void showDialogIntro() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogIntroFragment newFragment = new DialogIntroFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();

    }


    private void displayFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (fragment == null) return;
        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();

    }

    boolean isNavigationHide = false;

    private void animateNavigation(final boolean hide) {
        if (isNavigationHide && hide || !isNavigationHide && !hide) return;
        isNavigationHide = hide;
        int moveY = hide ? (2 * navigation.getHeight()) : 0;
        navigation.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }

    boolean isSearchBarHide = false;

    private void animateSearchBar(final boolean hide) {
        if (isSearchBarHide && hide || !isSearchBarHide && !hide) return;
        isSearchBarHide = hide;
        int moveY = hide ? -(2 * search_bar.getHeight()) : 0;
        search_bar.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }

    private void showDialogPaymentSuccess() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogHelpFragment newFragment = new DialogHelpFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
    }

}