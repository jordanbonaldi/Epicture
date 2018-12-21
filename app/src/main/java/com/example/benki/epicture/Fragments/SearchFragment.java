package com.example.benki.epicture.Fragments;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.benki.epicture.Adapters.PicturesAdapter;
import com.example.benki.epicture.Adapters.TagsAdapter;
import com.example.benki.epicture.Application.Epicture;
import com.example.benki.epicture.ImgurAPI.Enums.SectionEnum;
import com.example.benki.epicture.ImgurAPI.ImgurAPI;
import com.example.benki.epicture.ImgurAPI.Instances.Picture;
import com.example.benki.epicture.ImgurAPI.Instances.Tag;
import com.example.benki.epicture.R;

import java.util.List;

import static android.view.View.GONE;

/**
 * DEV_epicture_2018
 * Created by Lucas Benkemoun on 05/11/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */

public class SearchFragment extends Fragment {
    private RecyclerView picturesRecyclerView;
    private RecyclerView tagsRecyclerView;
    private PicturesAdapter picturesAdapter;
    private TagsAdapter tagsAdapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        picturesRecyclerView = view.findViewById(R.id.picture_list);
        picturesAdapter = null;
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        picturesRecyclerView.setLayoutManager(mLayoutManager);

        tagsRecyclerView = view.findViewById(R.id.tag_list);
        tagsAdapter = null;
        StaggeredGridLayoutManager mLayoutManager2 = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        tagsRecyclerView.setLayoutManager(mLayoutManager2);

        progressBar = view.findViewById(R.id.loading);
        progressBar.setVisibility(GONE);
        loadTags();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_search, menu);
        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            }
        }

        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                loadPictures(s, 0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private void loadPictures(final String query, final int index) {
        final Epicture epicture = (Epicture) getActivity().getApplicationContext();
        progressBar.setVisibility(View.VISIBLE);
        new AsyncTask<Void, Void, List<Picture>>() {
            @Override
            protected List<Picture> doInBackground(Void... voids) {
                return ImgurAPI.getSearchPictures(epicture.getManager(), SectionEnum.HOT.getName(), query, index);
            }

            @Override
            protected void onPostExecute(List<Picture> pictures) {
                super.onPostExecute(pictures);
                if (picturesAdapter == null) {
                    picturesAdapter = new PicturesAdapter(getContext(), pictures);
                    picturesRecyclerView.setAdapter(picturesAdapter);
                }
                else {
                    picturesAdapter.setPictures(pictures);
                    picturesAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(GONE);
                tagsRecyclerView.setVisibility(View.GONE);
                picturesRecyclerView.setVisibility(View.VISIBLE);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void loadTags() {
        final Epicture epicture = (Epicture) getActivity().getApplicationContext();
        progressBar.setVisibility(View.VISIBLE);
        new AsyncTask<Void, Void, List<Tag>>() {
            @Override
            protected List<Tag> doInBackground(Void... voids) {
                return ImgurAPI.getAllTags(epicture.getManager());
            }

            @Override
            protected void onPostExecute(List<Tag> tags) {
                super.onPostExecute(tags);
                if (tagsAdapter == null) {
                    tagsAdapter = new TagsAdapter(getContext(), tags, new TagListener(""));
                    tagsRecyclerView.setAdapter(tagsAdapter);
                }
                else {
                    tagsAdapter.setTags(tags);
                    tagsAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(GONE);
                tagsRecyclerView.setVisibility(View.VISIBLE);
                picturesRecyclerView.setVisibility(View.GONE);
            }
        }.execute();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                break;
        }
        return true;
    }

    public class TagListener implements View.OnClickListener {
        String tag;

        public TagListener(String tag) {
            this.tag = tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        @SuppressLint("StaticFieldLeak")
        @Override
        public void onClick(View v) {
            final Epicture epicture = (Epicture) getActivity().getApplicationContext();
            progressBar.setVisibility(View.VISIBLE);
            new AsyncTask<Void, Void, List<Picture>>() {
                @Override
                protected List<Picture> doInBackground(Void... voids) {
                    return ImgurAPI.getSearchTagPictures(epicture.getManager(), SectionEnum.HOT.getName(), tag, 0).getPictures();
                }

                @Override
                protected void onPostExecute(List<Picture> pictures) {
                    super.onPostExecute(pictures);
                    if (picturesAdapter == null) {
                        picturesAdapter = new PicturesAdapter(getContext(), pictures);
                        picturesRecyclerView.setAdapter(picturesAdapter);
                    } else {
                        picturesAdapter.setPictures(pictures);
                        picturesAdapter.notifyDataSetChanged();
                    }
                    progressBar.setVisibility(GONE);
                    tagsRecyclerView.setVisibility(View.GONE);
                    picturesRecyclerView.setVisibility(View.VISIBLE);
                }
            }.execute();
        }
    }
}
