package com.example.benki.epicture.ImgurAPI.Instances;

import com.example.benki.epicture.Utils.JSONObjectCustom;

import org.json.JSONException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Trophy {

    private final long id;
    private final String name;
    private final String description;
    private final String data;
    private final String image;

    public static Trophy newTrophy(JSONObjectCustom custom) throws JSONException {
        return new Trophy(
                custom.getLong("id"),
                custom.getString("name"),
                custom.getString("description"),
                custom.getString("data"),
                custom.getString("image")
        );
    }
}
