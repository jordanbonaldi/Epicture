package com.example.benki.epicture.ImgurAPI.Instances;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * DEV_epicture_2018
 * Created by Lucas Benkemoun on 31/10/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */
public class PictureManager implements ImgurInstances {

    public class Pictures {

        private String id;
        private String title;
        private String description;
        private String date;
        private int views;
        private String url;
        private String vote;
        private String deletehash;

        public Pictures(String id, String title, String description, String date, int views, String url, String vote, String deletehash) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.date = date;
            this.views = views;
            this.url = url;
            this.vote = vote;
            this.deletehash = deletehash;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getDate() {
            return date;
        }

        public int getViews() {
            return views;
        }

        public String getUrl() {
            return url;
        }

        public String getVote() {
            return vote;
        }

        public String getDeletehash() {
            return deletehash;
        }
    }

    private List<Pictures> pictures = new LinkedList<>();

    @Override
    public void deSerialize(JSONObject object) throws JSONException {
        JSONArray array = object.getJSONArray("data");

        for (int index = 0; index < array.length(); index++) {
            JSONObject obj = array.getJSONObject(index);

            pictures.add(
                    new Pictures(obj.getString("id"),
                            obj.getString("title"),
                            obj.getString("description"),
                            obj.getString("datetime"),
                            obj.getInt("views"),
                            obj.getString("link"),
                            obj.getString("vote"),
                            obj.getString("deletehash"))
            );
        }
    }

    public List<Pictures> getPictures() {
        return pictures;
    }
}
