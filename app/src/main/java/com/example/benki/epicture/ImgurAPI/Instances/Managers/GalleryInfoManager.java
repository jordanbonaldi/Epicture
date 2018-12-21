package com.example.benki.epicture.ImgurAPI.Instances.Managers;

import com.example.benki.epicture.ImgurAPI.Instances.GalleryInfo;
import com.example.benki.epicture.ImgurAPI.Instances.ImgurInstances;
import com.example.benki.epicture.Utils.JSONObjectCustom;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Getter;

public class GalleryInfoManager implements ImgurInstances {

    @Getter
    private GalleryInfo gallery;

    @Override
    public void deSerialize(JSONObject object) throws JSONException {
        System.out.println(object);
        this.gallery = GalleryInfo.newGallery(new JSONObjectCustom(object.getJSONObject("data")));
    }
}
