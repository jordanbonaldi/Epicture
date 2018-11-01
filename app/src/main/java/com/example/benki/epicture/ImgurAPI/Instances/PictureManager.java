package com.example.benki.epicture.ImgurAPI.Instances;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;

/**
 * DEV_epicture_2018
 * Created by Lucas Benkemoun on 31/10/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */
public class PictureManager implements ImgurInstances {

    @Getter
    private List<Picture> pictures = new LinkedList<>();

    @Override
    public void deSerialize(JSONObject object) throws JSONException {
        JSONArray array = object.getJSONArray("data");

        for (int index = 0; index < array.length(); index++) {
            JSONObject obj = array.getJSONObject(index);

            pictures.add(Picture.newPicture(obj));
        }
    }
}
