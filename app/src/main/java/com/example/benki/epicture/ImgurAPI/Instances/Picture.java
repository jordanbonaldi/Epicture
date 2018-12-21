package com.example.benki.epicture.ImgurAPI.Instances;

import com.example.benki.epicture.Utils.JSONObjectCustom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
    private final boolean is_mp4;
    private final boolean is_folder;
    private final boolean is_gif;
    private final List<Tag> tags;
    private final List<Picture> pictures;

    public static Picture newPicture(JSONObjectCustom obj) throws JSONException {
        List<Tag> tags = new ArrayList<>();
        List<Picture> pictures = new ArrayList<>();
        JSONArray array = obj.getJSONArray("tags");

        for (int index = 0; index < array.length(); index++) {
            JSONObject __obj = array.getJSONObject(index);

            tags.add(Tag.newtag(new JSONObjectCustom(__obj)));
        }

        if (obj.isArrayExists("images")) {
            array = obj.getJSONArray("images");

            for (int index = 0; index < array.length(); index++) {
                JSONObject __obj = array.getJSONObject(index);

                pictures.add(Picture.newPicture(new JSONObjectCustom(__obj)));
            }
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
                obj.getBoolean("is_album"),
                obj.getString("link").contains(".mp4"),
                pictures.size() > 0,
                obj.getString("link").contains(".gif"),
                tags, pictures);
    }
}
