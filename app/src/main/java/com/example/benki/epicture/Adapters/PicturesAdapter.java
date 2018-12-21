package com.example.benki.epicture.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.benki.epicture.Activities.PictureViewActivity;
import com.example.benki.epicture.Application.GlideApp;
import com.example.benki.epicture.ImgurAPI.Instances.Picture;
import com.example.benki.epicture.R;

import java.util.List;

/**
 * DEV_epicture_2018
 * Created by Lucas Benkemoun on 01/11/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */

public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.MyViewHolder> {

    public static final String PARAM_UPS = "ups";
    public static final String PARAM_DOWNS = "downs";
    public static final String PARAM_VOTE = "vote";
    public static final String PARAM_DESCRIPTION = "description";
    public static final String PARAM_URL = "url";
    public static final String PARAM_VIEWS = "views";
    public static final String PARAM_TITLE = "title";
    public static final String PARAM_GIF = "gif";
    public static final String PARAM_MP4 = "mp4";
    public static final String PARAM_ID = "id";


    private List<Picture> pictures;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public ImageView arrow;
        public TextView title;
        public TextView points;

        public MyViewHolder(View v) {
            super(v);

            image = v.findViewById(R.id.image);
            arrow = v.findViewById(R.id.arrow);
            title = v.findViewById(R.id.title);
            points = v.findViewById(R.id.points);
        }
    }

    public PicturesAdapter(Context context, List<Picture> pictures) {
        this.pictures = pictures;
        this.mContext = context;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    @Override
    public PicturesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_image, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Picture picture = pictures.get(position);
        if (picture.getPoints() >= 0)
            holder.arrow.setRotationX(180);
        holder.points.setText(picture.getPoints() + " Points");
        holder.title.setText(picture.getTitle());

        final String url = picture.is_folder() ? picture.getPictures().get(0).getUrl() : picture.getUrl();
        final boolean isGif = picture.is_folder() ? picture.getPictures().get(0).is_gif() : picture.is_gif();
        final boolean isMp4 = picture.is_folder() ? picture.getPictures().get(0).is_mp4() : picture.is_mp4();

        if (isMp4 || isGif) {
            final ImageView imageView = holder.image;
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_menu_camera);

            Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(url).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    imageView.setImageDrawable(resource);
                }
            });
        }
         else {
            GlideApp.with(mContext)
                    .load(url)
                    .placeholder(R.drawable.ic_menu_camera)
                    .into(holder.image);
        }

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PictureViewActivity.class);
                intent.putExtra(PARAM_UPS, picture.getUps());
                intent.putExtra(PARAM_DOWNS, picture.getDowns());
                intent.putExtra(PARAM_URL, url);
                intent.putExtra(PARAM_DESCRIPTION, picture.is_folder() ? picture.getPictures().get(0).getDescription() : picture.getDescription());
                intent.putExtra(PARAM_VIEWS, picture.getViews());
                intent.putExtra(PARAM_VOTE, picture.getVote());
                intent.putExtra(PARAM_TITLE, picture.getTitle());
                intent.putExtra(PARAM_GIF, isGif);
                intent.putExtra(PARAM_MP4, isMp4);
                intent.putExtra(PARAM_ID, picture.getId());
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return pictures.size();
    }
}
