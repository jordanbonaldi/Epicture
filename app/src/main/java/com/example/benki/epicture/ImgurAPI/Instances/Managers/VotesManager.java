package com.example.benki.epicture.ImgurAPI.Instances.Managers;

import com.example.benki.epicture.ImgurAPI.Instances.ImgurInstances;
import com.example.benki.epicture.ImgurAPI.Instances.Votes;
import com.example.benki.epicture.Utils.JSONObjectCustom;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Getter;

public class VotesManager implements ImgurInstances {

    @Getter
    private Votes votes;

    @Override
    public void deSerialize(JSONObject object) throws JSONException {
        this.votes = Votes.newVotes(new JSONObjectCustom(object.getJSONObject("data")));
    }
}
