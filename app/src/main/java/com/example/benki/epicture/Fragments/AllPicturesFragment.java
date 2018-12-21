package com.example.benki.epicture.Fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.benki.epicture.Application.Epicture;
import com.example.benki.epicture.ImgurAPI.Enums.SectionEnum;
import com.example.benki.epicture.ImgurAPI.Enums.SortEnum;
import com.example.benki.epicture.ImgurAPI.Instances.Picture;
import com.example.benki.epicture.Adapters.PicturesAdapter;
import com.example.benki.epicture.R;

import java.util.List;

/**
 * DEV_epicture_2018
 * Created by Lucas Benkemoun on 01/11/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */
public class AllPicturesFragment extends Fragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private PicturesAdapter mAdapter;
    private AlertDialog dialog;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_pictures, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        mRecyclerView = view.findViewById(R.id.picture_list);
        mAdapter = null;
        dialog = null;
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        progressBar = view.findViewById(R.id.loading);
        loadPictures(SectionEnum.HOT, SortEnum.VIRAL, true, 0);
    }

    @SuppressLint("StaticFieldLeak")
    private void loadPictures(final SectionEnum section, final SortEnum cat, final boolean isViral, final int index) {
        final Epicture epicture = (Epicture) getActivity().getApplicationContext();

        new AsyncTask<Void, Void, List<Picture>>() {
            @Override
            protected List<Picture> doInBackground(Void... voids) {
                return section.getAdapter().adapting(epicture.getManager(), cat, isViral, index);
            }

            @Override
            protected void onPostExecute(List<Picture> pictures) {
                super.onPostExecute(pictures);
                if (mAdapter == null) {
                    mAdapter = new PicturesAdapter(getContext(), pictures);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else {
                    mAdapter.setPictures(pictures);
                    mAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);
            }
        }.execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_filter, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_filter, null);
                dialogBuilder.setView(dialogView);

                TextView popular = dialogView.findViewById(R.id.popular);
                popular.setOnClickListener(this);

                TextView newest = dialogView.findViewById(R.id.newest);
                newest.setOnClickListener(this);

                TextView popular2 = dialogView.findViewById(R.id.popular2);
                popular2.setOnClickListener(this);

                TextView newest2 = dialogView.findViewById(R.id.newest2);
                newest2.setOnClickListener(this);

                TextView random = dialogView.findViewById(R.id.random);
                random.setOnClickListener(this);

                TextView rising = dialogView.findViewById(R.id.rising);
                rising.setOnClickListener(this);

                dialog = dialogBuilder.create();

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

                wmlp.gravity = Gravity.TOP | Gravity.RIGHT;

                dialog.show();

                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popular:
                loadPictures(SectionEnum.HOT, SortEnum.VIRAL, true, 0);
                break;

            case R.id.newest:
                loadPictures(SectionEnum.HOT, SortEnum.TIME, true, 0);
                break;

            case R.id.popular2:
                loadPictures(SectionEnum.USER, SortEnum.VIRAL, true, 0);
                break;

            case R.id.newest2:
                loadPictures(SectionEnum.USER, SortEnum.TIME, true, 0);
                break;

            case R.id.random:
                loadPictures(SectionEnum.RANDOM, SortEnum.RANDOM, true, 0);
                break;

            case R.id.rising:
                loadPictures(SectionEnum.USER, SortEnum.RISING, true, 0);
                break;

            default:
                break;
        }
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }
}