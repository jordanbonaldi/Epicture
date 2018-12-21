package com.example.benki.epicture.ImgurAPI.Instances.Managers;

import com.example.benki.epicture.ImgurAPI.Instances.ImgurInstances;
import com.example.benki.epicture.ImgurAPI.Instances.Picture;
import com.example.benki.epicture.ImgurAPI.Instances.Tag;
import com.example.benki.epicture.Utils.JSONObjectCustom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class TagsManager implements ImgurInstances {

    @Getter
    private Tag tag;

    @Getter
    private List<Picture> pictures = new ArrayList<>();

    @Getter
    private List<Tag> tags = new ArrayList<>();

    @Override
    public void deSerialize(JSONObject object) throws JSONException {
        JSONObjectCustom custom = new JSONObjectCustom(object.getJSONObject("data"));
        if (custom.isArrayExists("items")) {
            this.tag = Tag.newtag(custom);
            JSONArray array = custom.getJSONArray("items");

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                pictures.add(Picture.newPicture(new JSONObjectCustom(obj)));
            }
            return;
        }

        JSONArray array = custom.getJSONArray("tags");
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            tags.add(Tag.newtag(new JSONObjectCustom(obj)));
        }
    }
}
