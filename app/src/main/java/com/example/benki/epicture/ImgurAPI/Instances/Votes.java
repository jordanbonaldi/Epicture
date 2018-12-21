package com.example.benki.epicture.ImgurAPI.Instances;

import com.example.benki.epicture.Utils.JSONObjectCustom;

import org.json.JSONException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Votes {

    @Getter
    @RequiredArgsConstructor
    public enum VoteType {
        Up("up"),
        Down("down"),
        Unvote("veto");

        private final String type;
    }

    private final int ups;
    private final int downs;

    public static Votes newVotes(JSONObjectCustom obj) throws JSONException {
        return new Votes(obj.getInt("ups"), obj.getInt("downs"));
    }

}
