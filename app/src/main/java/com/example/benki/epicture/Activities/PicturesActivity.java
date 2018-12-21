package com.example.benki.epicture.Activities;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.benki.epicture.Fragments.AllPicturesFragment;
import com.example.benki.epicture.Fragments.ProfileFragment;
import com.example.benki.epicture.Fragments.SearchFragment;
import com.example.benki.epicture.Fragments.UploadFragment;
import com.example.benki.epicture.R;

/**
 * Epitech
 * Created by Lucas Benkemoun on 30/10/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */
public class PicturesActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String PARAM_STATE = "state";
    private Fragment mContent;
    private int state;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pictures);

        state = 0;
        if (savedInstanceState != null) {
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, AllPicturesFragment.class.getSimpleName());
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, ProfileFragment.class.getSimpleName());
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, SearchFragment.class.getSimpleName());
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, UploadFragment.class.getSimpleName());
            state = savedInstanceState.getInt(PARAM_STATE);
        }

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        BottomNavigationView navigationView = findViewById(R.id.navigation);

        switch (state) {
            case 0:
                ft.add(R.id.fragment_container, new AllPicturesFragment()).commit();
                setTitle("Home");
                break;
            case 1:
                ft.add(R.id.fragment_container, new SearchFragment()).commit();
                setTitle("Search image");
                break;
            case 2:
                ft.add(R.id.fragment_container, new UploadFragment()).commit();
                setTitle("Upload image");
                break;
            case 3:
                ft.add(R.id.fragment_container, new ProfileFragment()).commit();
                setTitle("Profile");
                break;
        }
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()) {
            case R.id.action_all_pictures:
                fragmentTransaction.replace(R.id.fragment_container, new AllPicturesFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                setTitle("Home");
                state = 0;
                break;
            case R.id.action_search:
                fragmentTransaction.replace(R.id.fragment_container, new SearchFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                setTitle("Search image");
                state = 1;
                break;
            case R.id.action_upload:
                fragmentTransaction.replace(R.id.fragment_container, new UploadFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                setTitle("Upload image");
                state = 2;
                break;
            case R.id.action_profile:
                fragmentTransaction.replace(R.id.fragment_container, new ProfileFragment()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                setTitle("Profile");
                state = 3;
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(PARAM_STATE, state);
        if (getSupportFragmentManager() != null && mContent != null) {
            getSupportFragmentManager().putFragment(outState, AllPicturesFragment.class.getSimpleName(), mContent);
            getSupportFragmentManager().putFragment(outState, ProfileFragment.class.getSimpleName(), mContent);
            getSupportFragmentManager().putFragment(outState, SearchFragment.class.getSimpleName(), mContent);
            getSupportFragmentManager().putFragment(outState, UploadFragment.class.getSimpleName(), mContent);
        }
    }

    @Override
    public void onBackPressed() {

    }
}
