package com.example.benki.epicture.ImgurAPI.Instances.Managers;

import com.example.benki.epicture.ImgurAPI.Instances.Base;
import com.example.benki.epicture.ImgurAPI.Instances.ImgurInstances;
import com.example.benki.epicture.Utils.JSONObjectCustom;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Getter;

public class AccountBaseManager implements ImgurInstances {

    @Getter
    private Base base;

    @Override
    public void deSerialize(JSONObject object) throws JSONException {
        JSONObjectCustom custom = new JSONObjectCustom(object.getJSONObject("data"));
        this.base = Base.newBase(custom);
    }
}
