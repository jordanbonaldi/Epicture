package com.example.benki.epicture.ImgurAPI.Instances.Managers;

import com.example.benki.epicture.ImgurAPI.Instances.ImgurInstances;
import com.example.benki.epicture.ImgurAPI.Instances.Picture;
import com.example.benki.epicture.Utils.JSONObjectCustom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * DEV_epicture_2018
 * Created by Lucas Benkemoun on 31/10/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */
public class PictureManager implements ImgurInstances {

    @Getter
    @RequiredArgsConstructor
    public static class Uploader {
        @NonNull
        private final String image;

        @NonNull
        private final String title;

        @NonNull
        private final String desc;

        private final String album;

        public HashMap<String, String> getHashMap() {
            return new HashMap<String, String>() {
                {
                    put("image", getImage());
                    put("title", getTitle());
                    put("description", getDesc());
                    if (Uploader.this.album != null)
                        put("album", getAlbum());
                }
            };
        }
    }

    @Getter
    private List<Picture> pictures = new ArrayList<>();

    @Override
    public void deSerialize(JSONObject object) throws JSONException {
        JSONObjectCustom custom = new JSONObjectCustom(object);
        if (custom.isArrayExists("data")) {
            JSONArray array = object.getJSONArray("data");

            for (int index = 0; index < array.length(); index++) {
                JSONObject obj = array.getJSONObject(index);

                pictures.add(Picture.newPicture(new JSONObjectCustom(obj)));
            }
        } else
            pictures.add(Picture.newPicture(new JSONObjectCustom(custom.getJSONObject("data"))));

    }
}
