package com.example.cauvong.musiconline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tus on 1/16/2018.
 */

public class CustomYtbList extends BaseAdapter {

    private Context context;
    private ArrayList<VideoYoutube> listVideos;
    private int mylayout;

    public CustomYtbList(Context context, int layout, ArrayList<VideoYoutube> listVideos) {
        this.context = context;
        this.mylayout = layout;
        this.listVideos = listVideos;

    }

    @Override
    public int getCount() {
        return listVideos.size();
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
        TextView txtTitle = (TextView) view.findViewById(R.id.txtYtbTitle);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgYtb);

        VideoYoutube video = listVideos.get(position);
        txtTitle.setText(video.getTitle());

        Picasso.with(context).load(video.getThumbnails()).into(imageView);

        return view;
    }


}
