package com.example.benki.epicture;

import android.app.Application;

import com.example.benki.epicture.ImgurAPI.RequestManager;

/**
 * Epitech
 * Created by Lucas Benkemoun on 30/10/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */
public class Epicture extends Application {

    private String refreshToken = "";
    private String username = "";
    private String accessToken = "";
    private String cliendid = "f98676cf23e9ab9";

    private RequestManager manager;

    public RequestManager getManager() {
        return manager;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void createManager() {
        manager = new RequestManager(username, accessToken, cliendid);
    }
}
