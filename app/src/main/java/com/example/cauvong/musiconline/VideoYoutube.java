package com.example.cauvong.musiconline;

import java.io.Serializable;

/**
 * Created by tus on 1/16/2018.
 */

public class VideoYoutube implements Serializable{

    private String title;
    private String thumbnails;
    private String idVideo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }
}
