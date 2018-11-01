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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class RequestManager {

    private final String username;

    private final String access;

    private final String clientid;

    private final HashMap<String, String> params;

    private final UserSettings settings;

    @Getter
    @RequiredArgsConstructor
    public class UserSettings {
        private final String access;
        private final String username;
        private final String id;
    }

    public interface RequestProcessing {
        String setAuthorisation(UserSettings settings);
    }

    public RequestManager(String username, String access, String clientid) {
        this.username = username;
        this.access = access;
        this.clientid = clientid;
        this.params = new HashMap<>();
        this.settings = new UserSettings(this.access, this.username, this.clientid);
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
