package com.example.benki.epicture.Fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.benki.epicture.Adapters.PicturesAdapter;
import com.example.benki.epicture.Application.Epicture;
import com.example.benki.epicture.Application.GlideApp;
import com.example.benki.epicture.ImgurAPI.ImgurAPI;
import com.example.benki.epicture.ImgurAPI.Instances.Base;
import com.example.benki.epicture.ImgurAPI.Instances.Picture;
import com.example.benki.epicture.ImgurAPI.Instances.User;
import com.example.benki.epicture.R;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;

/**
 * DEV_epicture_2018
 * Created by Lucas Benkemoun on 05/11/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */

public class ProfileFragment extends Fragment {

    private RecyclerView recyclerView;
    private PicturesAdapter mAdapter;

    private TextView username;
    private TextView status;
    private TextView points;
    private TextView trophies;
    private TextView date;
    private ImageView background;
    private CircleImageView photoProfile;

    private TextView posts;
    private TextView favorites;

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.my_images);
        username = view.findViewById(R.id.name);
        points = view.findViewById(R.id.points);
        trophies = view.findViewById(R.id.trophies);
        date = view.findViewById(R.id.date);
        status = view.findViewById(R.id.status);
        background = view.findViewById(R.id.background);
        photoProfile = view.findViewById(R.id.photo_profile);

        posts = view.findViewById(R.id.posts);
        favorites = view.findViewById(R.id.favorites);
        progressBar = view.findViewById(R.id.loading);

        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorites.setTextColor(getResources().getColor(R.color.darkgrey));
                posts.setTextColor(getResources().getColor(R.color.white));
                loadPictures(true);
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorites.setTextColor(getResources().getColor(R.color.white));
                posts.setTextColor(getResources().getColor(R.color.darkgrey));
                loadPictures(false);
            }
        });

        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadPictures(true);
        loadProfile();
    }

    @SuppressLint("StaticFieldLeak")
    private void loadPictures(final boolean isPosts) {
        final Epicture epicture = (Epicture) getActivity().getApplicationContext();

        new AsyncTask<Void, Void, List<Picture>>() {
            @Override
            protected List<Picture> doInBackground(Void... voids) {
                if (isPosts)
                    return ImgurAPI.getUserPictures(epicture.getManager());
                else
                    return ImgurAPI.getFavorites(epicture.getManager(), 0);
            }

            @Override
            protected void onPostExecute(List<Picture> pictures) {
                super.onPostExecute(pictures);
                if (mAdapter == null) {
                    mAdapter = new PicturesAdapter(getContext(), pictures);
                    recyclerView.setAdapter(mAdapter);
                }
                else {
                    mAdapter.setPictures(pictures);
                    mAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(GONE);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void loadProfile() {
        final Epicture epicture = (Epicture) getActivity().getApplicationContext();

        new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... voids) {
                return ImgurAPI.getAllUserInfo(epicture.getManager());
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);

                Base base = user.getBase();
                username.setText(epicture.getUsername());
                status.setText(base.getReputationName());
                points.setText(base.getReputation() + " Points");
                Date dateData = new Date(System.currentTimeMillis() - base.getCreated());

                String filename = dateData.toString();
                String[] parts = filename.split("\\:");
                String res = parts[0].substring(0, parts[0].length() - 2);

                date.setText("Created " + res);
                trophies.setText(user.getInfo().getTrophies().size() + " Trophies");
                if (getActivity() != null) {
                    GlideApp.with(getActivity())
                            .load(base.getCover())
                            .placeholder(R.drawable.ic_menu_camera)
                            .into(background);
                    GlideApp.with(getActivity())
                            .load(base.getAvatar())
                            .placeholder(R.drawable.ic_menu_camera)
                            .into(photoProfile);
                }
            }
        }.execute();
    }
}
