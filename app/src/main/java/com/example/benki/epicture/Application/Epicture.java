package com.example.benki.epicture.Application;

import android.app.Application;

import com.example.benki.epicture.ImgurAPI.RequestManager;

import lombok.Getter;
import lombok.Setter;

/**
 * Epitech
 * Created by Lucas Benkemoun on 30/10/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */
@Getter @Setter
public class Epicture extends Application {

    private String refreshToken = "";
    private String username = "";
    private String accessToken = "";
    private String cliendid = "f98676cf23e9ab9";
    private RequestManager manager;

    public void createManager() {
        manager = new RequestManager(username, accessToken, cliendid);
    }
}
