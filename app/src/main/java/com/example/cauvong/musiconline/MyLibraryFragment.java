package com.example.cauvong.musiconline;

import android.Manifest;
import android.app.ListActivity;
import android.app.TabActivity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.util.ArrayList;

/**
 * Created by tusss on 1/1/2018.
 */

public class MyLibraryFragment extends Fragment {

    private static final int MY_PERMISSION_REQUEST = 1;

    ArrayList<Song> listSongs;
    GetArt ga;

    public MyLibraryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_library_tabhost, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        addControl();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
        } else {
            ScanMusic sc = new ScanMusic(getActivity());
            listSongs = sc.getMusic();
            doStuff();
        }
    }

    public void doStuff() {

//        GridView gridView = (GridView) getView().findViewById(R.id.gridALbums);
//        ArrayList<Album> listAlbum = getAlbum(listSongs);
//        CustomGrid gridAdapter = new CustomGrid(getActivity(), R.layout.grid_single, listAlbum);
//
//        ga = new GetArt(getActivity());
//        gridView.setAdapter(gridAdapter);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getActivity(), "Permission Granted!", Toast.LENGTH_SHORT).show();
                        doStuff();
                    } else {
                        Toast.makeText(getActivity(), "No Permission Granted!", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                    return;
                }
            }
        }
    }

    public void addControl() {
        TabHost tabHost = (TabHost) getView().findViewById(R.id.tabML);

        tabHost.setup();
        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setIndicator("Artists");
        tab2.setContent(R.id.tabA);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("t3");
        tab3.setIndicator("Albums");
        tab3.setContent(R.id.tabAlbums);
        tabHost.addTab(tab3);

        TabHost.TabSpec tab4 = tabHost.newTabSpec("t4");
        tab4.setIndicator("Songs");
        tab4.setContent(R.id.tabSongs);
        tabHost.addTab(tab4);
    }
}