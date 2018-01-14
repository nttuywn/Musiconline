package com.example.cauvong.musiconline;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by tusss on 1/10/2018.
 */

public class CustomGrid extends BaseAdapter {

    private Context context;

    private ArrayList<Album> listAlbum;

    private int myLayout;

    public CustomGrid(Context context, int layout, ArrayList<Album> listAlbum) {
        this.context = context;
        this.myLayout = layout;
        this.listAlbum = listAlbum;
    }

    @Override
    public int getCount() {
        return listAlbum.size();
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
        view = inflater.inflate(myLayout, null);
        ImageView image = (ImageView) view.findViewById(R.id.imgGrid);
        TextView txt = (TextView) view.findViewById(R.id.txtAlbums);

        Album album = listAlbum.get(position);
        Song song = album.getListSong().get(0);

        txt.setText(album.getAlbumName());
        image.setImageBitmap(getAlbumart(Integer.parseInt(song.getImage())));

        return view;
    }


    private Bitmap getAlbumart(int album_id) {
        Context applicationContext = context.getApplicationContext();
        Bitmap bm = null;
        try {
            final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");

            Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);

            ParcelFileDescriptor pfd = applicationContext.getContentResolver().openFileDescriptor(uri, "r");

            if (pfd != null) {
                FileDescriptor fd = pfd.getFileDescriptor();
                bm = BitmapFactory.decodeFileDescriptor(fd);
            }
        } catch (Exception e) {
        }
        return bm;
    }
}
