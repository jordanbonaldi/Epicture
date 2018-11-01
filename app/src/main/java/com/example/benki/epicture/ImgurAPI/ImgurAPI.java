package com.example.benki.epicture.ImgurAPI;

import com.example.benki.epicture.Epicture;
import com.example.benki.epicture.ImgurAPI.Instances.PictureManager;

import java.util.List;

/**
 * DEV_epicture_2018
 * Created by Lucas Benkemoun on 31/10/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */
public class ImgurAPI {

    public static List<PictureManager.Pictures> getPictures(RequestManager manager) {
        PictureManager pm = (PictureManager) manager.newRequest("https://api.imgur.com/3/account/me/images", new RequestManager.RequestProcessing() {
            @Override
            public String setAuthorisation(RequestManager.UserSettings settings) {
                return "Bearer " + settings.getAccess();
            }
        }, PictureManager.class);

        return pm.getPictures();
    }
}
