package com.example.benki.epicture.ImgurAPI.Instances;

import com.example.benki.epicture.Utils.JSONObjectCustom;

import org.json.JSONException;

import java.util.HashMap;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Base {

    @NonNull
    private long id;
    @NonNull
    private String url;
    @NonNull
    private String bio;
    @NonNull
    private String avatar;
    @NonNull
    private String cover;
    @NonNull
    private int reputation;
    @NonNull
    private String reputationName;
    @NonNull
    private long created;
    @NonNull
    private boolean is_blocked;

    public HashMap<String, String> getHash() {
        return new HashMap<String, String>(){{
           put("bio", getBio());
           put("avatar", getAvatar());
        }};
    }

    public static Base newBase(JSONObjectCustom custom) throws JSONException {
        return new Base(
                custom.getLong("id"),
                custom.getString("url"),
                custom.getString("bio"),
                custom.getString("avatar"),
                custom.getString("cover"),
                custom.getInt("reputation"),
                custom.getString("reputation_name"),
                custom.getLong("created"),
                custom.getBoolean("is_blocked")
        );
    }
}
