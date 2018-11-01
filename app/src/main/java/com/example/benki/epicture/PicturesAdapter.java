package com.example.benki.epicture;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benki.epicture.ImgurAPI.Instances.PictureManager;

import java.util.List;

/**
 * DEV_epicture_2018
 * Created by Lucas Benkemoun on 01/11/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */

public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.MyViewHolder> {

    private List<PictureManager.Pictures> pictures;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView points;

        public MyViewHolder(View v) {
            super(v);

            image = v.findViewById(R.id.image);
            title = v.findViewById(R.id.title);
            points = v.findViewById(R.id.points);
        }
    }

    public PicturesAdapter(Context context, List<PictureManager.Pictures> pictures) {
        this.pictures = pictures;
        this.mContext = context;
    }

    @Override
    public PicturesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PictureManager.Pictures picture = pictures.get(position);
        holder.points.setText(picture.getVote() + " Points");
        holder.title.setText(picture.getTitle());

        GlideApp.with(mContext)
                .load(picture.getUrl())
                .placeholder(R.drawable.ic_menu_camera)
                .into(holder.image);
        }

    @Override
    public int getItemCount() {
        return pictures.size();
    }
}
