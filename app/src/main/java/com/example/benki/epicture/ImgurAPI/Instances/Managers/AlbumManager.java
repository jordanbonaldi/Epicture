package com.example.benki.epicture.ImgurAPI.Instances.Managers;

import com.example.benki.epicture.ImgurAPI.Instances.Album;
import com.example.benki.epicture.ImgurAPI.Instances.ImgurInstances;
import com.example.benki.epicture.ImgurAPI.Instances.Picture;
import com.example.benki.epicture.ImgurAPI.Instances.Tag;
import com.example.benki.epicture.Utils.JSONObjectCustom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class AlbumManager implements ImgurInstances {

    @Getter
    @RequiredArgsConstructor
    public static class Uploader {
        @NonNull
        private final String title;

        @NonNull
        private final String desc;

        public HashMap<String, String> getHashMap() {
            return new HashMap<String, String>() {
                {
                    put("title", getTitle());
                    put("description", getDesc());
                    put("privacy", "public");
                }
            };
        }
    }

    @Getter
    private Album album;

    @Getter
    private List<Album> albums;

    @Override
    public void deSerialize(JSONObject object) throws JSONException {
        JSONObjectCustom js
                = new JSONObjectCustom(object);

        this.albums = new ArrayList<>();

        if (js.isArrayExists("data")) {
            JSONArray ar = object.getJSONArray("data");

            for (int i = 0; i < ar.length(); i++)
                this.albums.add(Album.newAlbum(new JSONObjectCustom(ar.getJSONObject(i))));

        } else
            this.album = Album.newAlbum(new JSONObjectCustom(js.getJSONObject("data")));

    }
}