package frt.gurgur.theconfession.model;

import com.google.gson.annotations.SerializedName;

public class HashtagAddRequestModel {

    @SerializedName("hashtag")
    private String hashtag;


    public HashtagAddRequestModel(String hashtag) {
        this.hashtag = hashtag;

    }

    public String getHashtag() {
        return hashtag;
    }


}
