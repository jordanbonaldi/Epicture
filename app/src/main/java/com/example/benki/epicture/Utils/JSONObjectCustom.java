package com.example.benki.epicture.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

/**
 * DEV_epicture_2018
 * Created by Lucas Benkemoun on 04/11/2018.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */
public class JSONObjectCustom extends JSONObject {

    public JSONObjectCustom(JSONObject obj) throws JSONException{
        super(obj.toString());

    }

    interface Invoke<T> {
        T invoke(String name) throws JSONException;
    }

    public <T> T getObj(Invoke<T> method, T def, String name)
    {
        T t = def;
        try {
            t = method.invoke(name);
        } catch (Exception e) {
            return t;
        }
        return t;
    }

    public boolean isArrayExists(String name) {
        try {
            this.getJSONArray(name);
        } catch(Exception e) {
            return false;
        }
        return true;
    }

    public boolean isExists(String name) {
        try {
            this.get(name);
        } catch(Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String getString(String name) {
        return this.getObj(new Invoke<String>() {
            @Override
            public String invoke(String name) throws JSONException{
                return JSONObjectCustom.super.getString(name);
            }
        }, "", name);
    }

    @Override
    public boolean getBoolean(String name) {
        return this.getObj(new Invoke<Boolean>() {
            @Override
            public Boolean invoke(String name) throws JSONException{
                return JSONObjectCustom.super.getBoolean(name);
            }
        }, false, name);
    }

    @Override
    public int getInt(String name) throws JSONException {
        return this.getObj(new Invoke<Integer>() {
            @Override
            public Integer invoke(String name) throws JSONException{
                return JSONObjectCustom.super.getInt(name);
            }
        }, 0, name);
    }
}
