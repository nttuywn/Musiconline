package com.example.cauvong.musiconline;

import java.util.ArrayList;

/**
 * Created by tusss on 1/10/2018.
 */

public class Album {

    private String albumName;
    private ArrayList<Song> listSong;


    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public ArrayList<Song> getListSong() {
        return listSong;
    }

    public void setListSong(ArrayList<Song> listSong) {
        this.listSong = listSong;
    }

}
