package com.example.cauvong.musiconline;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.util.ArrayList;

/**
 * Created by tusss on 1/9/2018.
 */

public class CustomList extends BaseAdapter {

    private Context context;
    private ArrayList<Song> listSongs;
    private int mylayout;

    CustomList(Context context, int layout, ArrayList<Song> listSongs) {
        this.context = context;
        this.mylayout = layout;
        this.listSongs = listSongs;

    }

    @Override
    public int getCount() {
        return listSongs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(mylayout, null);
        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView txtDuration = (TextView) view.findViewById(R.id.txtDuration);
        TextView txtArtists = (TextView) view.findViewById(R.id.txtArtists);

        ImageView imageView = (ImageView) view.findViewById(R.id.img);
        final Song song = listSongs.get(position);
        txtTitle.setText(song.getTitle());
        txtDuration.setText(changeDuration(song.getLength()));
        txtArtists.setText(song.getArtist());

        GetArt ga = new GetArt(context);
        imageView.setImageBitmap(ga.getAlbumart(Integer.parseInt(song.getImage())));

        return view;
    }


    private String changeDuration(String length) {
        int change = Integer.parseInt(length);
        int minute = change / 60000;
        int second = (change % 60000) / 1000;
        return minute + ":" + second;
    }

}