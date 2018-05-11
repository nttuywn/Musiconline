package com.example.cauvong.musiconline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

/**
 * Created by cauvong on 12/29/2017.
 */

public class OnlineLibraryFragment extends Fragment implements View.OnClickListener {

    private String apiKey = "AIzaSyBCepNUGcqELdmLdgOgaUri_kcQ7vXeziQ";
    private String url = "";
    private ListView videoListview;
    private ArrayList<VideoYoutube> listVideos;
    private CustomYtbList customYtbList;
    private Button btnSearch, rcsearch1, rcsearch2, rcsearch3, rcsearch4, rcsearch5;
    private EditText txtSearch;
    private ArrayList<String> listSearchKey = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_list, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView();
        initComponent();
    }

    public void findView(){
        btnSearch = (Button) getView().findViewById(R.id.btnSearch);
        txtSearch = (EditText) getView().findViewById(R.id.txtSearch);
        rcsearch1 = (Button) getView().findViewById(R.id.rcsearch1);
        rcsearch2 = (Button) getView().findViewById(R.id.rcsearch2);
        rcsearch3 = (Button) getView().findViewById(R.id.rcsearch3);
        rcsearch4 = (Button) getView().findViewById(R.id.rcsearch4);
        rcsearch5 = (Button) getView().findViewById(R.id.rcsearch5);

        btnSearch.setOnClickListener(this);
        rcsearch1.setOnClickListener(this);
        rcsearch2.setOnClickListener(this);
        rcsearch3.setOnClickListener(this);
        rcsearch4.setOnClickListener(this);
        rcsearch5.setOnClickListener(this);
    }

    public void initComponent(){
        SharedPreferences sp = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Set <String> set = sp.getStringSet("recentsearch", null);
        if(set == null) return;
        convertRecentSearch(set);
        recentButton();
    }

    public void recentButton(){
        for(int i = listSearchKey.size()-1; i >= 0; i--){
            if(i == listSearchKey.size()-1) rcsearch1.setText(listSearchKey.get(i).toString());
            if(listSearchKey.size() > 2 && i == listSearchKey.size()-2) rcsearch2.setText(listSearchKey.get(i).toString());
            if(listSearchKey.size() > 3 && i == listSearchKey.size()-3) rcsearch3.setText(listSearchKey.get(i).toString());
            if(listSearchKey.size() > 4 && i == listSearchKey.size()-4) rcsearch4.setText(listSearchKey.get(i).toString());
            if(listSearchKey.size() > 5 && i == listSearchKey.size()-5) rcsearch5.setText(listSearchKey.get(i).toString());
        }
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
                            recentButton();
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

    public void savedSearchRequest(){
        SharedPreferences sp = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        Set<String> listSet = new HashSet<String>();
        listSet.add(listSearchKey.toString());
        edit.putStringSet("recentsearch", listSet);
        edit.commit();
    }

    private StringBuffer convertString(StringBuffer keyWord){
        for(int i = 0; i < keyWord.length(); i++){
            if(keyWord.codePointAt(i) == 32){
                keyWord.setCharAt(i,'_');
            }
        }
        return keyWord;
    }

    private ArrayList<String> convertRecentSearch(Set<String> rcsList){
        StringBuffer listCV = new StringBuffer(rcsList.toString());
        ArrayList<String> converted = new ArrayList<>();
        for(int i = 0; i < listCV.length(); i++){
            if(listCV.codePointAt(i) == 91){
                listCV.setCharAt(i,' ');
            }
            if(listCV.codePointAt(i) == 93){
                listCV.setCharAt(i,',');
            }
        }
        for(int i = 1; i < listCV.length(); i++){
            if(listCV.codePointAt(i) == 44 ){
                listSearchKey.add(listCV.substring(1,i).toString());
                listCV.delete(0,i+1);
                i = 0;
            }
        }
        return converted;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rcsearch1:
                searchYTB(rcsearch1.getText().toString());
                break;
            case R.id.rcsearch2:
                searchYTB(rcsearch2.getText().toString());
                break;
            case R.id.rcsearch3:
                searchYTB(rcsearch3.getText().toString());
                break;
            case R.id.rcsearch4:
                searchYTB(rcsearch4.getText().toString());
                break;
            case R.id.rcsearch5:
                searchYTB(rcsearch5.getText().toString());
                break;
            case R.id.btnSearch:
                searchYTB(txtSearch.getText().toString());
                break;
        }
    }

    private void searchYTB(String ytbName){
        StringBuffer keyWord = new StringBuffer(ytbName);
        keyWord = convertString(keyWord);
        url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=" + keyWord + "&type=video&key=" + apiKey + "&maxResults=10";
        videoListview = (ListView) getView().findViewById(R.id.listVideos);
        listVideos = new ArrayList<>();
        customYtbList = new CustomYtbList(getActivity(), R.layout.custom_youtube_list, listVideos);
        getJsonYoutube(url);
        listSearchKey.add(ytbName);
        savedSearchRequest();

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
}
