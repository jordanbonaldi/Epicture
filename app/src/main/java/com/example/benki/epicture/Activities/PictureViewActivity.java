package com.example.benki.epicture.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.benki.epicture.Application.Epicture;
import com.example.benki.epicture.Application.GlideApp;
import com.example.benki.epicture.ImgurAPI.ImgurAPI;
import com.example.benki.epicture.ImgurAPI.Instances.Votes;
import com.example.benki.epicture.R;

import static com.example.benki.epicture.Adapters.PicturesAdapter.PARAM_ID;
import static com.example.benki.epicture.Adapters.PicturesAdapter.PARAM_UPS;
import static com.example.benki.epicture.Adapters.PicturesAdapter.PARAM_DOWNS;
import static com.example.benki.epicture.Adapters.PicturesAdapter.PARAM_VOTE;
import static com.example.benki.epicture.Adapters.PicturesAdapter.PARAM_DESCRIPTION;
import static com.example.benki.epicture.Adapters.PicturesAdapter.PARAM_URL;
import static com.example.benki.epicture.Adapters.PicturesAdapter.PARAM_VIEWS;
import static com.example.benki.epicture.Adapters.PicturesAdapter.PARAM_TITLE;
import static com.example.benki.epicture.Adapters.PicturesAdapter.PARAM_GIF;
import static com.example.benki.epicture.Adapters.PicturesAdapter.PARAM_MP4;

/**
 * DEV_epicture_2018
 * Created by Lucas Benkemoun on 01/11/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */

public class PictureViewActivity extends AppCompatActivity {

    private String title;
    private String description;
    private String url;
    private boolean isMp4;
    private boolean isGif;
    private int ups;
    private int downs;
    private int votes;
    private int views;
    private String imageId;

    private ImageView image;
    private TextView viewsTV;
    private TextView descriptionTV;
    private VideoView video;
    private TextView upvoteTV;
    private TextView downvoteTV;
    private TextView likeTV;

    private LinearLayout voteUp;
    private LinearLayout voteDown;
    private LinearLayout favorite;
    private LinearLayout share;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_picture);

        image = findViewById(R.id.image);
        video = findViewById(R.id.video);
        viewsTV = findViewById(R.id.views);
        descriptionTV = findViewById(R.id.description);

        ImageView upvoteIm = findViewById(R.id.up_arrow);
        upvoteIm.setRotation(180);

        upvoteTV = findViewById(R.id.up_votetv);
        downvoteTV = findViewById(R.id.down_votetv);
        likeTV = findViewById(R.id.liketv);

        voteUp = findViewById(R.id.up_vote);
        voteDown = findViewById(R.id.down_vote);
        favorite = findViewById(R.id.favorite);
        share = findViewById(R.id.share);

        voteUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickUpVote(imageId);
            }
        });

        voteDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDownVote(imageId);
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickFavorite(imageId);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickShare();
            }
        });

        if (savedInstanceState != null) {
            title = savedInstanceState.getString(PARAM_TITLE);
            description = savedInstanceState.getString(PARAM_DESCRIPTION);
            url = savedInstanceState.getString(PARAM_URL);
            isMp4 = savedInstanceState.getBoolean(PARAM_MP4);
            isGif = savedInstanceState.getBoolean(PARAM_GIF);
            ups = savedInstanceState.getInt(PARAM_UPS);
            downs = savedInstanceState.getInt(PARAM_DOWNS);
            votes = savedInstanceState.getInt(PARAM_VOTE);
            views = savedInstanceState.getInt(PARAM_VIEWS);
            imageId = savedInstanceState.getString(PARAM_ID);
        }
        else if (getIntent() != null && getIntent().getExtras() != null) {
            title = getIntent().getExtras().getString(PARAM_TITLE);
            description = getIntent().getExtras().getString(PARAM_DESCRIPTION);
            url = getIntent().getExtras().getString(PARAM_URL);
            isMp4 = getIntent().getExtras().getBoolean(PARAM_MP4);
            isGif = getIntent().getExtras().getBoolean(PARAM_GIF);
            ups = getIntent().getExtras().getInt(PARAM_UPS);
            downs = getIntent().getExtras().getInt(PARAM_DOWNS);
            votes = getIntent().getExtras().getInt(PARAM_VOTE);
            views = getIntent().getExtras().getInt(PARAM_VIEWS);
            imageId = getIntent().getExtras().getString(PARAM_ID);
        }
        setTitle(title);

        if (isMp4 && !url.substring(url.length() - 3).equals("gif")) {
            video.setVisibility(View.VISIBLE);
            video.setVideoURI(Uri.parse(url));
            video.start();
        }
        else if (isGif || url.substring(url.length() - 3).equals("gif")) {
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_menu_camera);

            Glide.with(this).setDefaultRequestOptions(requestOptions).load(url).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    image.setImageDrawable(resource);
                }
            });
        }
        else {
            GlideApp.with(this)
                    .load(url)
                    .placeholder(R.drawable.ic_menu_camera)
                    .into(image);
        }

        descriptionTV.setText(!description.equals("null") && !description.equals("") ? description : "No description");
        viewsTV.setText(views + " Views");

        upvoteTV.setText(String.valueOf(ups));
        downvoteTV.setText(String.valueOf(downs));
        likeTV.setText(String.valueOf(votes));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        video.stopPlayback();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PARAM_TITLE, title);
        outState.putString(PARAM_DESCRIPTION, description);
        outState.putString(PARAM_URL, url);
        outState.putBoolean(PARAM_MP4, isMp4);
        outState.putBoolean(PARAM_GIF, isGif);
        outState.putInt(PARAM_UPS, ups);
        outState.putInt(PARAM_DOWNS, downs);
        outState.putInt(PARAM_VOTE, votes);
        outState.putInt(PARAM_VIEWS, views);
        outState.putString(PARAM_ID, imageId);
    }

    @SuppressLint("StaticFieldLeak")
    private void clickUpVote(final String imageId) {
        final Epicture epicture = (Epicture) getApplicationContext();

        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                return ImgurAPI.voteForImage(epicture.getManager(), imageId, Votes.VoteType.Up);
            }
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                Toast.makeText(PictureViewActivity.this,
                        aBoolean ? "You up voted this picture" : "You already voted for this picture",
                        Toast.LENGTH_LONG).show();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void clickDownVote(final String imageId) {
        final Epicture epicture = (Epicture) getApplicationContext();

        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                return ImgurAPI.voteForImage(epicture.getManager(), imageId, Votes.VoteType.Down);
            }
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                Toast.makeText(PictureViewActivity.this,
                        aBoolean ? "You down voted this picture" : "You already voted for this picture",
                        Toast.LENGTH_LONG).show();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void clickFavorite(final String imageId) {
        final Epicture epicture = (Epicture) getApplicationContext();

        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                return ImgurAPI.favoriteAnImage(epicture.getManager(), imageId);
            }
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                Toast.makeText(PictureViewActivity.this,
                        aBoolean ? "You added the picture to your favorites" : "This picture is already in your favorites",
                        Toast.LENGTH_LONG).show();
            }
        }.execute();
    }

    private void clickShare() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Epicture");
            String sAux = "\nLet me recommend you this picture\n\n";
            sAux = sAux + url + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Choose one"));
        } catch(Exception e) {
        }
    }

}
