package com.example.benki.epicture.ImgurAPI.Instances;

import com.example.benki.epicture.Utils.JSONObjectCustom;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Epicture
 * Created by Jordy on 01/11/2018.
 * Copyright © 2018 Jordy. All rights reserved.
 */

@Getter
@Setter
@RequiredArgsConstructor
public class Album
{
    private final int id;
    private final String title;
    private final String desc;
    private final String url;
    private final int views;
    private final List<Picture> picture;

    public static Album newAlbum(JSONObjectCustom obj) throws JSONException {
        List<Picture> picture = new ArrayList<>();

        if (obj.isArrayExists("images")) {
            JSONArray array = obj.getJSONArray("images");
            for (int i = 0; i < array.length(); i++) {
                picture.add(Picture.newPicture(new JSONObjectCustom(array.getJSONObject(i))));
            }
        }

        return new Album(obj.getInt("id"),
                obj.getString("title"),
                obj.getString("description"),
                obj.getString("link"),
                obj.getInt("views"), picture);
    }
}
