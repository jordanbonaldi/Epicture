package com.example.benki.epicture.ImgurAPI.Instances;

import com.example.benki.epicture.Utils.JSONObjectCustom;

import lombok.Setter;
import org.json.JSONException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Epicture
 * Created by Jordy on 01/11/2018.
 * Copyright © 2018 Jordy. All rights reserved.
 */

@Getter
@Setter
@RequiredArgsConstructor
public class Tag
{
    private final String name;
    private final String followName;
    private final int followers;
    private final int items;
    private final String description;
    private final boolean is_promoted;
    private final String hash;
    private Picture picture;

    public String getImages() {
        return "https://i.imgur.com/" + this.hash + ".jpg";
    }

    public static Tag newtag(JSONObjectCustom obj) throws JSONException {
        return new Tag(obj.getString("display_name"),
                obj.getString("name"),
                obj.getInt("followers"),
                obj.getInt("total_items"),
                obj.getString("description"),
                obj.getBoolean("is_promoted"),
                obj.getString("background_hash"));
    }
}
