package com.enterprise.c2server;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.appbar.MaterialToolbar;

public class DashboardActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("REX.ENT C2 DASHBOARD");
        }

        viewPager = findViewById(R.id.viewPager);
        bottomNav = findViewById(R.id.bottomNav);

        DashboardPagerAdapter adapter = new DashboardPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_devices) viewPager.setCurrentItem(0, true);
            else if (itemId == R.id.nav_command) viewPager.setCurrentItem(1, true);
            else if (itemId == R.id.nav_files) viewPager.setCurrentItem(2, true);
            else if (itemId == R.id.nav_stream) viewPager.setCurrentItem(3, true);
            return true;
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                bottomNav.getMenu().getItem(position).setChecked(true);
            }
        });
    }
}
