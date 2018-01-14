package com.example.cauvong.musiconline;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.FileDescriptor;

/**
 * Created by tusss on 1/13/2018.
 */

public class GetArt {

    private Context context;

    public GetArt() {
    }

    public GetArt(Context context) {
        this.context = context;
    }

    public Bitmap getAlbumart(int album_id) {
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
