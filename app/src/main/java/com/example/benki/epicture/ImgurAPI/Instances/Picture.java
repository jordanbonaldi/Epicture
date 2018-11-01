package com.example.benki.epicture.ImgurAPI.Instances;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Epicture
 * Created by Jordy on 01/11/2018.
 * Copyright © 2018 Jordy. All rights reserved.
 */
@Getter
@RequiredArgsConstructor
public class Picture {
    private final String id;
    private final String title;
    private final String description;
    private final String date;
    private final int views;
    private final String url;
    private final String vote;
    private final String deletehash;
    private final int ups;
    private final int downs;
    private final int points;
    private final int comments;
    private final boolean is_album;
    private final List<Tag> tags;

    public static Picture newPicture(JSONObject obj) throws JSONException {
        List<Tag> tags = new ArrayList<>();
        JSONArray array = obj.getJSONArray("tags");

        for (int index = 0; index < array.length(); index++) {
            JSONObject __obj = array.getJSONObject(index);

            tags.add(Tag.newtag(__obj));
        }

        return new Picture(obj.getString("id"),
                obj.getString("title"),
                obj.getString("description"),
                obj.getString("datetime"),
                obj.getInt("views"),
                obj.getString("link"),
                obj.getString("vote"),
                obj.getString("deletehash"),
                obj.getInt("ups"),
                obj.getInt("downs"),
                obj.getInt("points"),
                obj.getInt("comment_count"),
                obj.getBoolean("is_album"), tags);
    }
}
