package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class DriverActivity extends AppCompatActivity
{
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DeployActivity(),"");
        adapter.addFragment(new CarActivityDriver(),"");
        adapter.addFragment(new ChatActivity(),"");
        adapter.addFragment(new EditProfilePage(),"Водитель");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.deploy_vector_full);
        tabLayout.getTabAt(1).setIcon(R.drawable.car_vector);
        tabLayout.getTabAt(2).setIcon(R.drawable.chat_vector);
        tabLayout.getTabAt(3).setIcon(R.drawable.profile_vector);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(R.drawable.deploy_vector_full);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.car_vector_full);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.chat_vector_full);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.profile_vector_full);
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(R.drawable.deploy_vector);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.car_vector);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.chat_vector);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.profile_vector);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
    }
}