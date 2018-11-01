package com.example.benki.epicture.ImgurAPI;

import android.util.Log;

import com.example.benki.epicture.ImgurAPI.Instances.ImgurInstances;
import com.example.benki.epicture.Utils.UrlRequester;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * DEV_epicture_2018
 * Created by Lucas Benkemoun on 31/10/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */
public class RequestManager {

    private final String username;

    private final String access;

    private final HashMap<String, String> params;

    private final UserSettings settings;

    public class UserSettings {
        String access;
        String username;

        public UserSettings(String access, String username) {
            this.access = access;
            this.username = username;
        }

        public String getAccess() {
            return access;
        }

        public String getUsername() {
            return username;
        }
    }

    public interface RequestProcessing {
        String setAuthorisation(UserSettings settings);
    }

    public RequestManager(String username, String access) {
        this.username = username;
        this.access = access;
        this.params = new HashMap<>();
        this.settings = new UserSettings(this.access, this.username);
    }

    private void setHeader(String authorisation) {
        this.params.clear();
        this.params.put("Authorization", authorisation);
    }

    private String formattingUrl(String url, List<String> params) {
        String formatted = url;

        for (int i = 1; i <= params.size(); i++) {
            formatted = url.replaceAll("@" + i, params.get(i - 1));
        }

        return formatted;
    }

    public ImgurInstances newRequest(String url, RequestProcessing process, Class<? extends ImgurInstances> clazz, String ... params) {
        String formattedUrl = this.formattingUrl(url, Arrays.asList(params));
        ImgurInstances instance;

        this.setHeader(process.setAuthorisation(this.settings));

        try {
            String _response = UrlRequester.launchRequest(formattedUrl, this.params);
            Log.d("JORDI", "WESH=" + _response);
            JSONObject _obj = new JSONObject(_response);

            final Constructor constructor = clazz.getDeclaredConstructors()[0];
            instance = (ImgurInstances) constructor.newInstance();
            instance.deSerialize(_obj);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("JORDI", "" + Log.getStackTraceString(e));
            return null;
        }

        return instance;
    }

}
