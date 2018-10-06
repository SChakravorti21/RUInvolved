package com.example.shoumyo.ruinvolved;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.shoumyo.ruinvolved.ui.LandingPageAdapter;
import com.example.shoumyo.ruinvolved.utils.SharedPrefsUtils;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        ViewPager pager = findViewById(R.id.pager);
        LandingPageAdapter adapter = new LandingPageAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);
    }
}
