package com.sara.nasaimageexplorer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model class for NASA APOD response.
 *
 * @author Sara
 * @version 1.0
 */
public class NasaApod {

    @SerializedName("title")
    private String title;

    @SerializedName("date")
    private String date;

    @SerializedName("explanation")
    private String explanation;

    @SerializedName("url")
    private String url;

    @SerializedName("media_type")
    private String mediaType;

    @SerializedName("hdurl")
    private String hdUrl;


    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getUrl() {
        return url;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getHdUrl() {
        return hdUrl;
    }
}