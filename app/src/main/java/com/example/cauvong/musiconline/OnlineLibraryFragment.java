package com.example.cauvong.musiconline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cauvong on 12/29/2017.
 */

public class OnlineLibraryFragment extends Fragment {

    private String apiKey = "AIzaSyBCepNUGcqELdmLdgOgaUri_kcQ7vXeziQ";
    private String url = "";
    private ListView videoListview;
    private ArrayList<VideoYoutube> listVideos;
    private CustomYtbList customYtbList;
    private Button btnSearch;
    private EditText txtSearch;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_list, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSearch = (Button) getView().findViewById(R.id.btnSearch);
        txtSearch = (EditText) getView().findViewById(R.id.txtSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer keyWord = new StringBuffer(txtSearch.getText().toString());
                keyWord = convertString(keyWord);
                url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + keyWord + "&type=video&key=" + apiKey + "&maxResults=10";
                videoListview = (ListView) getView().findViewById(R.id.listVideos);
                listVideos = new ArrayList<>();
                customYtbList = new CustomYtbList(getActivity(), R.layout.custom_youtube_list, listVideos);
                getJsonYoutube(url);


                videoListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        Intent intentToA = new Intent(getActivity(), Youtube.class);
                        Bundle bud = new Bundle();
                        bud.putSerializable("listvideos", listVideos);
                        intentToA.putExtra("idVideo", listVideos.get(position).getIdVideo());
                        intentToA.putExtra("list", bud);
                        startActivity(intentToA);
                    }
                });

                videoListview.setAdapter(customYtbList);
            }
        });
    }

    public void getJsonYoutube(final String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonItems = response.getJSONArray("items");
                            String title = "";
                            String url = "";
                            String idVideo = "";

                            for(int i = 0; i < jsonItems.length(); i++){
                                JSONObject jsonObject = jsonItems.getJSONObject(i);
                                JSONObject jsonSnippet = jsonObject.getJSONObject("snippet");
                                JSONObject jsonId = jsonObject.getJSONObject("id");
                                JSONObject jsonThumbnails = jsonSnippet.getJSONObject("thumbnails");
                                JSONObject jsonMedium = jsonThumbnails.getJSONObject("medium");
                                title = jsonSnippet.getString("title");
                                url = jsonMedium.getString("url");
                                idVideo = jsonId.getString("videoId");

                                VideoYoutube video = new VideoYoutube();
                                video.setTitle(title);
                                video.setThumbnails(url);
                                video.setIdVideo(idVideo);

                                listVideos.add(video);
                            }
                            customYtbList.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Loi!", Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private StringBuffer convertString(StringBuffer keyWord){
        for(int i = 0; i < keyWord.length(); i++){
            if(keyWord.codePointAt(i) == 32){
                keyWord.setCharAt(i,'_');
            }
        }
        return keyWord;
    }

}
