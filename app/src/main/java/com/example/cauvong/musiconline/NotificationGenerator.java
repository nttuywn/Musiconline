package com.example.cauvong.musiconline;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.CalendarContract;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by tus on 1/20/2018.
 */

public class NotificationGenerator {

    private static final int NOTIFICATION_ID_OPNE_ACTIVITY = 9;
    public static final String NOTIFY_PREVIOUS = "com.android.music.musicservicecommand";
    public static final String NOTIFY_PAUSE = "com.android.music.musicservicecommand";
    public static final String NOTIFY_PLAY = "com.android.music.musicservicecommand";
    public static final String NOTIFY_NEXT = "com.android.music.musicservicecommand";


    public static void customNotification(Context context, Song song){
        RemoteViews expandRemote = new RemoteViews(context.getPackageName(), R.layout.notification_bar);

        NotificationCompat.Builder nc = new NotificationCompat.Builder(context);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notiIntent = new Intent(context, MainActivity.class);
        notiIntent.setAction(Intent.ACTION_MAIN);
        notiIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notiIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notiIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        nc.setContentIntent(pendingIntent);
        nc.setSmallIcon(R.mipmap.ic_launcher);
        nc.setAutoCancel(true);
        nc.setCustomBigContentView(expandRemote);
        GetArt ga = new GetArt(context);
        expandRemote.setTextViewText(R.id.txtNotiSongName, song.getTitle());
        expandRemote.setImageViewBitmap(R.id.imgNotiSongImg, ga.getAlbumart(Integer.parseInt(song.getImage())));
//        nc.setContentTitle("Online");
//        nc.setContentText("CLick");

        setListener(expandRemote, context);
        nm.notify(NOTIFICATION_ID_OPNE_ACTIVITY, nc.build());

    }

    private static void setListener(RemoteViews remoteViews, Context context){
        Intent previous = new Intent(NOTIFY_PREVIOUS);
        Intent pause = new Intent(NOTIFY_PAUSE);
        Intent play = new Intent(NOTIFY_PLAY);
        Intent next = new Intent(NOTIFY_NEXT);

        PendingIntent pPrevious = PendingIntent.getBroadcast(context, 0, previous, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btn_noti_prev, pPrevious);

        PendingIntent pPause = PendingIntent.getBroadcast(context, 0, pause, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btn_noti_pause, pPause);

        PendingIntent pPlay = PendingIntent.getBroadcast(context, 0, play, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btn_noti_play, pPlay);

        PendingIntent pNext = PendingIntent.getBroadcast(context, 0, next, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.btn_noti_next, pNext);
    }
}
