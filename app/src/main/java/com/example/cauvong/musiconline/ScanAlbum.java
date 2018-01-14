package com.example.cauvong.musiconline;

import java.util.ArrayList;

/**
 * Created by tusss on 1/14/2018.
 */

public class ScanAlbum {


    public ScanAlbum() {
    }

    public ArrayList getAlbum(ArrayList<Song> listSongs) {
        ArrayList<Album> listAlbums = new ArrayList<Album>();
        Album album = new Album();
        album.setAlbumName(listSongs.get(0).getAlbum());
        ArrayList<Song> listS = new ArrayList<>();
        listS.add(listSongs.get(0));
        album.setListSong(listS);
        listAlbums.add(album);

        for (int i = 1; i < listSongs.size(); i++) {
            Song song = listSongs.get(i);
            if (checkAlbum(song.getAlbum(), listAlbums) == -1) {
                Album album2 = new Album();
                album2.setAlbumName(song.getAlbum());
                ArrayList<Song> listS2 = new ArrayList<>();
                listS2.add(song);
                album2.setListSong(listS2);
                listAlbums.add(album2);

            } else {
                listAlbums.get(checkAlbum(song.getAlbum(), listAlbums)).getListSong().add(song);
            }
        }
        return listAlbums;
    }

    private int checkAlbum(String albumName, ArrayList<Album> listAlbum) {
        for (int i = 0; i < listAlbum.size(); i++) {
            Album album = listAlbum.get(i);
            if (albumName.compareTo(album.getAlbumName()) == 0) {
                return i;
            }
        }
        return -1;
    }

}
