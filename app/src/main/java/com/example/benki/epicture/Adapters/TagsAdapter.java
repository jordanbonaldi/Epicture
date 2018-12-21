package com.example.benki.epicture.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benki.epicture.Application.GlideApp;
import com.example.benki.epicture.Fragments.SearchFragment;
import com.example.benki.epicture.ImgurAPI.Instances.Tag;
import com.example.benki.epicture.R;

import java.util.List;

/**
 * DEV_epicture_2018
 * Created by Lucas Benkemoun on 01/11/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.MyViewHolder> {

    private List<Tag> tags;
    private Context mContext;
    private SearchFragment.TagListener listener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView followers;

        public MyViewHolder(View v) {
            super(v);

            image = v.findViewById(R.id.background);
            title = v.findViewById(R.id.title);
            followers = v.findViewById(R.id.followers);
        }
    }

    public TagsAdapter(Context context, List<Tag> tags, SearchFragment.TagListener listener) {
        this.tags = tags;
        this.mContext = context;
        this.listener = listener;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public TagsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_tag, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Tag tag = tags.get(position);

        holder.title.setText(tag.getName());
        holder.followers.setText(String.valueOf(tag.getFollowers()));

        GlideApp.with(mContext)
                .load(tag.getImages())
                .centerCrop()
                .placeholder(R.drawable.ic_menu_camera)
                .into(holder.image);

        listener.setTag(tag.getName());
        holder.itemView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }
}
