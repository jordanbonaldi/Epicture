package com.example.benki.epicture.ImgurAPI.Instances;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Epicture
 * Created by Jordy on 01/11/2018.
 * Copyright © 2018 Jordy. All rights reserved.
 */

@Getter
@RequiredArgsConstructor
public class Tag
{
    private final String name;
    private final int followers;
    private final int items;
    private final String description;
    private final boolean is_promoted;

    public static Tag newtag(JSONObject obj) throws JSONException {
        return new Tag(obj.getString("display_name"),
                obj.getInt("followers"),
                obj.getInt("total_items"),
                obj.getString("description"),
                obj.getBoolean("is_promoted"));
    }
}
