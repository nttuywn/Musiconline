package com.example.cauvong.musiconline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class Youtube extends YouTubeBaseActivity {

    private YouTubePlayerView ytb;
    private String idVideo;
    private ListView listViewVideos;
    private CustomYtbList customYtbList;
    private ArrayList<VideoYoutube> listVideos;
    private Intent intentToA;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_test);

        Intent intent = getIntent();
        idVideo = intent.getStringExtra("idVideo");
        Bundle bud = intent.getBundleExtra("list");
        listVideos = (ArrayList<VideoYoutube>) bud.getSerializable("listvideos");


        ytb = (YouTubePlayerView) findViewById(R.id.ytbVideo);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(idVideo);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        ytb.initialize("AIzaSyBCepNUGcqELdmLdgOgaUri_kcQ7vXeziQ",onInitializedListener);

        listViewVideos = (ListView) findViewById(R.id.listVideos);
        customYtbList = new CustomYtbList(this, R.layout.custom_youtube_list, listVideos);
        intentToA = new Intent(this, Youtube.class);
        listViewVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Bundle bud = new Bundle();
                bud.putSerializable("listvideos", listVideos);
                intentToA.putExtra("idVideo", listVideos.get(position).getIdVideo());
                intentToA.putExtra("list", bud);
                startActivity(intentToA);
            }
        });

        listViewVideos.setAdapter(customYtbList);
    }
}
