package com.example.cauvong.musiconline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by tus on 1/20/2018.
 */

public class NotificationBroadCast extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals(NotificationGenerator.NOTIFY_PLAY)){
            Toast.makeText(context, "NOTiFI Play", Toast.LENGTH_LONG).show();
        } else if(intent.getAction().equals(NotificationGenerator.NOTIFY_PAUSE)){
            Toast.makeText(context, "NOTiFI Pause", Toast.LENGTH_LONG).show();
        } else if(intent.getAction().equals(NotificationGenerator.NOTIFY_NEXT)){
            Toast.makeText(context, "NOTiFI Next", Toast.LENGTH_LONG).show();
        } else if(intent.getAction().equals(NotificationGenerator.NOTIFY_PREVIOUS)){
            Toast.makeText(context, "NOTiFI Previuos", Toast.LENGTH_LONG).show();
        }
    }
}
