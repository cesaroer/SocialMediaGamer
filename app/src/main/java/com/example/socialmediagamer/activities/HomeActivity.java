package com.example.socialmediagamer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.socialmediagamer.R;
import com.example.socialmediagamer.fragments.ChatsFragment;
import com.example.socialmediagamer.fragments.FiltersFragment;
import com.example.socialmediagamer.fragments.HomeFragment;
import com.example.socialmediagamer.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(new HomeFragment());
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    if (item.getItemId() == R.id.itemHome) {
                        //FRAGMENT HOME
                        openFragment(new HomeFragment());
                    }else if (item.getItemId() == R.id.itemChat){
                        //FRAGMENT CHATS
                        openFragment(new ChatsFragment());

                    }else if (item.getItemId() == R.id.itemFilters){
                        //FRAGMENT FILTER
                        openFragment(new FiltersFragment());

                    }else if (item.getItemId() == R.id.itemProfile){
                        //FRAGMENT PROFILE
                        openFragment(new ProfileFragment());

                    }
                    return true;
                }
            };


}