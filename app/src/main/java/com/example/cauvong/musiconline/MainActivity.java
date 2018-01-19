package com.example.cauvong.musiconline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Context contextOfApplication;

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

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        OnlineLibraryFragment onlineLibraryFragment = new OnlineLibraryFragment();
        transaction.add(R.id.fr_content_main, onlineLibraryFragment,"online");
        AlbumSongFragment albumSongFragment = new AlbumSongFragment();
        transaction.add(R.id.fr_content_main, albumSongFragment, "albumsong");
        MyLibraryFragment myLibraryFragment = new MyLibraryFragment();
        transaction.add(R.id.fr_content_main, myLibraryFragment, "mylibrary");
        OfflineFragment offlineFragment = new OfflineFragment();
        transaction.add(R.id.fr_content_main, offlineFragment, "playing");
        transaction.commitNow();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            return;
//            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.online_library) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if (getSupportFragmentManager().findFragmentByTag("online") != null) {
                transaction.hide(getSupportFragmentManager().findFragmentByTag("albumsong"));
                transaction.hide(getSupportFragmentManager().findFragmentByTag("mylibrary"));
                transaction.hide(getSupportFragmentManager().findFragmentByTag("playing"));
                transaction.show(getSupportFragmentManager().findFragmentByTag("online"));
            } else {
                OnlineLibraryFragment fragment = new OnlineLibraryFragment();
                transaction.add(R.id.fr_content_main, fragment, "online");
            }

            transaction.commit();
//            Intent intentToA = new Intent(this, Youtube.class);
//            startActivity(intentToA);
        } else if (id == R.id.my_library) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if (getSupportFragmentManager().findFragmentByTag("mylibrary") != null) {
                transaction.hide(getSupportFragmentManager().findFragmentByTag("albumsong"));
                transaction.hide(getSupportFragmentManager().findFragmentByTag("online"));
                transaction.hide(getSupportFragmentManager().findFragmentByTag("playing"));
                transaction.show(getSupportFragmentManager().findFragmentByTag("mylibrary"));
            } else {
                MyLibraryFragment fragment = new MyLibraryFragment();
                transaction.add(R.id.fr_content_main, fragment, "mylibrary");
            }

            transaction.commit();

        } else if (id == R.id.playlist) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            if (getSupportFragmentManager().findFragmentByTag("playing") != null) {
                transaction.hide(getSupportFragmentManager().findFragmentByTag("albumsong"));
                transaction.hide(getSupportFragmentManager().findFragmentByTag("mylibrary"));
                transaction.hide(getSupportFragmentManager().findFragmentByTag("online"));
                transaction.show(getSupportFragmentManager().findFragmentByTag("playing"));
            } else {
                OfflineFragment fragment1 = new OfflineFragment();
                transaction.add(R.id.fr_content_main, fragment1, "playing");
            }

            transaction.commit();
        } else if (id == R.id.settings) {

        } else if (id == R.id.help) {

        } else if (id == R.id.send_feedback) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

}
