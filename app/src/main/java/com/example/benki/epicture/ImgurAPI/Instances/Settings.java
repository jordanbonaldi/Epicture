package com.example.benki.epicture.ImgurAPI.Instances;

import com.example.benki.epicture.Utils.JSONObjectCustom;

import org.json.JSONException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Settings {

    private final String account;
    private final String email;
    private final String album_privacy;
    private final boolean messaging;
    private final boolean newsletter;

    public static Settings newSetting(JSONObjectCustom obj) {
        return new Settings(
                obj.getString("account_url"),
                obj.getString("email"),
                obj.getString("album_privacy"),
                obj.getBoolean("messaging_enabled"),
                obj.getBoolean("newsletter_subscribed")
        );
    }
}
