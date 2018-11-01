package com.example.benki.epicture;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.benki.epicture.ImgurAPI.ImgurAPI;
import com.example.benki.epicture.ImgurAPI.Instances.PictureManager;

import java.util.List;

/**
 * DEV_epicture_2018
 * Created by Lucas Benkemoun on 01/11/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */
public class AllPicturesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_pictures, container, false);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        final Epicture epicture = (Epicture) getActivity().getApplicationContext();

        final RecyclerView mRecyclerView = view.findViewById(R.id.picture_list);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);


        new AsyncTask<Void, Void, List<PictureManager.Pictures>>() {
            @Override
            protected List<PictureManager.Pictures> doInBackground(Void... voids) {
                return ImgurAPI.getPictures(epicture.getManager());
            }

            @Override
            protected void onPostExecute(List<PictureManager.Pictures> pictures) {
                super.onPostExecute(pictures);
                PicturesAdapter mAdapter = new PicturesAdapter(getContext(), pictures);
                mRecyclerView.setAdapter(mAdapter);
            }
        }.execute();

    }
}