package com.ign.agajan.agajancodefoo;

/**
 * Created by ajumakuliyev on 4/30/17.
 */

public class VideoModel {
    private String headline;
    private String posterUrl;
    private String videoUrl;

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
