package com.example.cauvong.musiconline;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArraySet;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Context contextOfApplication;
    public Stack<String> listFragmentBackList = new Stack<>();
    private ArrayList<String> listFragment = new ArrayList<>();
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        InitFragment();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
//        Toast.makeText(getApplicationContext(), "Dasdas", Toast.LENGTH_SHORT).show();
        ImageView imgUser = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imgUser);
        TextView txtUsrname = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtUserName);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if(isLoggedIn){
            txtUsrname.setText(Profile.getCurrentProfile().getName());
            Picasso.with(getApplicationContext()).load(Profile.getCurrentProfile().getProfilePictureUri(100,100).toString()).fit().into(imgUser);
        } else {
            txtUsrname.setText("Guest");
            Picasso.with(getApplicationContext()).load("https://i1.wp.com/www.winhelponline.com/blog/wp-content/uploads/2017/12/user.png?fit=256%2C256&quality=100").fit().into(imgUser);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(listFragmentBackList.size() <= 1){
                return;
            }
            else {
                SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                if(sp.getString("back",null).compareTo(" ") != 0) changeFragment("mylibrary");
                else {
                    listFragmentBackList.pop();
                    changeFragment(listFragmentBackList.peek().toString());
                }
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (id == R.id.online_library) {
            listFragmentBackList.push("online");
            edit.putString("back", " ");
            edit.commit();
            changeFragment("online");
        } else if (id == R.id.my_library) {
            listFragmentBackList.push("mylibrary");
            edit.putString("back", " ");
            edit.commit();
            changeFragment("mylibrary");
        } else if (id == R.id.playlist) {
            listFragmentBackList.push("playing");
            edit.putString("back", " ");
            edit.commit();
            changeFragment("playing");
        } else if (id == R.id.login) {
            Intent myIntent = new Intent(this, FacebookActivity.class);
            startActivity(myIntent);
        } else if (id == R.id.settings){
//
        }

        return true;
    }

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    private void InitFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        OnlineLibraryFragment onlineLibraryFragment = new OnlineLibraryFragment();
        transaction.add(R.id.fr_content_main, onlineLibraryFragment,"online");
        listFragment.add("online");
        AlbumSongFragment albumSongFragment = new AlbumSongFragment();
        transaction.add(R.id.fr_content_main, albumSongFragment, "albumsong");
        listFragment.add("albumsong");
        MyLibraryFragment myLibraryFragment = new MyLibraryFragment();
        transaction.add(R.id.fr_content_main, myLibraryFragment, "mylibrary");
        listFragment.add("mylibrary");
        OfflineFragment offlineFragment = new OfflineFragment();
        transaction.add(R.id.fr_content_main, offlineFragment, "playing");
        listFragment.add("playing");
        listFragmentBackList.push("playing");
        transaction.commitNow();

    }

    public void changeFragment(String nameFragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        for(int i = 0; i < listFragment.size(); i++){
            if(listFragment.get(i).compareTo(nameFragment) == 0) {
                transaction.show(getSupportFragmentManager().findFragmentByTag(nameFragment));
                continue;
            } else {
                transaction.hide(getSupportFragmentManager().findFragmentByTag(listFragment.get(i).toString()));
            }
        }
        transaction.commit();
    }

}
