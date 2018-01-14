package com.example.cauvong.musiconline;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by tusss on 1/12/2018.
 */

public class ScanMusic {

    private Activity ac;

    public ScanMusic(Activity ac) {
        this.ac = ac;
    }

    public ArrayList getMusic() {
        Context applicationContext = ac.getApplicationContext();
        ContentResolver contentResolver = applicationContext.getContentResolver();
        Uri songURI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songURI, null, null, null, null);

        ArrayList<Song> arrayList = new ArrayList<Song>();

        if (songCursor != null && songCursor.moveToFirst()) {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtists = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songAlbum = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int songComposer = songCursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER);
            int songFilename = songCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
            int songLength = songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int songSize = songCursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
            int songDatecreated = songCursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED);
            int songImage = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int songPath = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                Song song = new Song();
                song.setTitle(songCursor.getString(songTitle));
                song.setArtist(songCursor.getString(songArtists));
                song.setAlbum(songCursor.getString(songAlbum));
                song.setComposer(songCursor.getString(songComposer));
                song.setFilename(songCursor.getString(songFilename));
                song.setLength(songCursor.getString(songLength));
                song.setSize(songCursor.getString(songSize));
                song.setDatecreated(songCursor.getString(songDatecreated));
                song.setImage(songCursor.getString(songImage));
                song.setPath(songCursor.getString(songPath));
                arrayList.add(song);
            } while (songCursor.moveToNext());
        }
        return arrayList;
    }
}
