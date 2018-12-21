package com.example.benki.epicture.ImgurAPI.Instances;

import com.example.benki.epicture.Utils.JSONObjectCustom;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class GalleryInfo {

    private final int totalComment;
    private final int totalFavorites;
    private final int totalSubmissions;
    private final List<Trophy> trophies;

    public static GalleryInfo newGallery(JSONObjectCustom custom) throws JSONException {
        List<Trophy> trophies = new ArrayList<>();

        if (custom.isArrayExists("trophies")) {
            JSONArray array = custom.getJSONArray("trophies");

            for (int i = 0; i < array.length(); i++)
                trophies.add(Trophy.newTrophy(new JSONObjectCustom(array.getJSONObject(i))));
        }

        return new GalleryInfo(
                custom.getInt("total_gallery_comments"),
                custom.getInt("total_gallery_favorites"),
                custom.getInt("total_gallery_submissions"),
                trophies
        );
    }
}
