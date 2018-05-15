package com.example.cauvong.musiconline;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by tus on 5/15/2018.
 */

public class MyService extends Service {

    private MediaPlayer mPlayer;
    private ArrayList<Song> listSong;
    private int position;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init(intent);
        mPlayer = MediaPlayer.create(this, Uri.parse(listSong.get(position).getPath()));
        if(intent.getBooleanExtra("haveseek",false)){
            mPlayer.start();
            mPlayer.seekTo(intent.getIntExtra("seek", 0));
        } else {
            mPlayer.start();
        }
        intent.putExtra("haveseek", true);
        intent.putExtra("seek",mPlayer.getCurrentPosition());
        Intent send = new Intent(getApplicationContext(), MainActivity.class);
//        send.putExtra()
//        sendBroadcast(new Intent(""));
        startForeground(1, NotificationGenerator.customNotification(getApplicationContext(), listSong.get(position)));
        return START_REDELIVER_INTENT;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
    }

    private void init(Intent intent){
        listSong = (ArrayList<Song>) intent.getSerializableExtra("listsong");
        position = intent.getIntExtra("song", 0);
    }


}
