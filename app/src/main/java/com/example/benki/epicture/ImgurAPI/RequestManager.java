package com.example.benki.epicture.ImgurAPI;

import com.example.benki.epicture.ImgurAPI.Instances.ImgurInstances;
import com.example.benki.epicture.Utils.UrlRequester;

import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class RequestManager {

    private final String username;

    private final String access;

    private final String clientid;

    private final HashMap<String, String> params;

    @Getter
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
            formatted = formatted.replaceAll("@" + i, params.get(i - 1));
        }

        return formatted;
    }

    public ImgurInstances newRequest(String url, RequestProcessing process, Class<? extends ImgurInstances> clazz, String ... params) {
        String formattedUrl = this.formattingUrl(url, Arrays.asList(params));

        System.out.println(formattedUrl);

        ImgurInstances instance;

        this.setHeader(process.setAuthorisation(this.settings));

        try {
            String _response = UrlRequester.launchRequest(formattedUrl, this.params);
            System.out.println(_response);
            JSONObject _obj = new JSONObject(_response);

            final Constructor constructor = clazz.getDeclaredConstructors()[0];
            instance = (ImgurInstances) constructor.newInstance();
            instance.deSerialize(_obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return instance;
    }

    public ImgurInstances newPostRequest(String url, RequestProcessing process, UrlRequester.actionOnPost act, Class<? extends ImgurInstances> clazz, String ... params) {
        String formattedUrl = this.formattingUrl(url, Arrays.asList(params));
        ImgurInstances instance;

        this.setHeader(process.setAuthorisation(this.settings));

        System.out.println(formattedUrl);

        try {
            String _response = UrlRequester.launchPostRequest(formattedUrl, this.params, act);
            System.out.println(_response);
            JSONObject _obj = new JSONObject(_response);

            final Constructor constructor = clazz.getDeclaredConstructors()[0];
            instance = (ImgurInstances) constructor.newInstance();
            instance.deSerialize(_obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return instance;
    }

}
