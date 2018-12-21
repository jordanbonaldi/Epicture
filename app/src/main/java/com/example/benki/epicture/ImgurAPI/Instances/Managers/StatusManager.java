package com.example.benki.epicture.ImgurAPI.Instances.Managers;

import com.example.benki.epicture.ImgurAPI.Instances.ImgurInstances;
import com.example.benki.epicture.Utils.JSONObjectCustom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class StatusManager implements ImgurInstances {
    private boolean success;

    private JSONObjectCustom data;

    @Override
    public void deSerialize(JSONObject object) throws JSONException {
        this.success = object.getBoolean("success");
        if (object.has("data"))
            this.data = new JSONObjectCustom(object);
    }
}
