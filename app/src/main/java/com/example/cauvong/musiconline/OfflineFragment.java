package com.example.cauvong.musiconline;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by tusss on 1/11/2018.
 */

public class OfflineFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Song> listSong = new ArrayList<>();
    private ArrayList<Song> listAllSong = new ArrayList<>();
    private ArrayList<Album> listAlbum = new ArrayList<>();
    private SeekBar mSeek;
    private MediaPlayer mPlayer;
    private Button mBtnPlay, mBtnPause, mBtnshuffer, mBtnShufferOn, mBtnRepeat, mBtnRepeat1, mBtnRepeatAll, mBtnPrev, mBtnNext;
    private TextView mTvTimeBegin, mTvTimeEnd;
    private Handler mHandler = new Handler();
    private int currentId = 0;
    private ListView listNowPlay;
    private ListView listSongs;
    private ListView listAlbumSong;
    private ImageView imgNowPlaybtm;
    private TextView txtNowPlaybtm;
    private ImageView imgNowPlaybtm2;
    private TextView txtNowPlaybtm2;
    private GetArt ga;
    private TextView txtArtistPlaying;
    private TextView txtArtistPlaying2;
    private GridView gridView;
    private TextView txtNotiSongName;
    private ImageView imgNotiSongImg;
    private Intent myService;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.now_play, container, false);

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        ScanMusic sc = new ScanMusic(getActivity());
        listAllSong = sc.getMusic();
        listSong = sc.getMusic();
        ScanAlbum scanA = new ScanAlbum();
        listAlbum = scanA.getAlbum(listSong);
        findViews();
        initComponents();
    }

    private void findViews() {
        mSeek = (SeekBar) getView().findViewById(R.id.seekbar);
        mBtnPlay = (Button) getView().findViewById(R.id.btn_noti_play);
        mBtnPause = (Button) getView().findViewById(R.id.btn_noti_pause);
        mBtnRepeat = (Button) getView().findViewById(R.id.btn_repeat);
        mBtnRepeat1 = (Button) getView().findViewById(R.id.btn_repeat_1);
        mBtnRepeatAll = (Button) getView().findViewById(R.id.btn_repeat_all);
        mBtnshuffer = (Button) getView().findViewById(R.id.btn_shuffle);
        mBtnShufferOn = (Button) getView().findViewById(R.id.btn_shuffle_on);
        mBtnPrev = (Button) getView().findViewById(R.id.btn_prev);
        mBtnNext = (Button) getView().findViewById(R.id.btn_next);
        mTvTimeBegin = (TextView) getView().findViewById(R.id.tv_time_run);
        mTvTimeEnd = (TextView) getView().findViewById(R.id.tv_time_total);
        listNowPlay = (ListView) getActivity().findViewById(R.id.listNowPlay);
        listSongs = (ListView) getActivity().findViewById(R.id.listSongs);
        listAlbumSong = (ListView) getActivity().findViewById(R.id.listAlbumSongs);
        imgNowPlaybtm = (ImageView) getActivity().findViewById(R.id.imgNowPlaybtm);
        txtNowPlaybtm = (TextView) getActivity().findViewById(R.id.txtNowPlaybtm);
        imgNowPlaybtm2 = (ImageView) getActivity().findViewById(R.id.imgNowPlaybtm2);
        txtNowPlaybtm2 = (TextView) getActivity().findViewById(R.id.txtNowPlaybtm2);
        txtArtistPlaying = (TextView) getActivity().findViewById(R.id.txtArtistPlaying);
        txtArtistPlaying2 = (TextView) getActivity().findViewById(R.id.txtArtistPlaying2);
        gridView = (GridView) getActivity().findViewById(R.id.gridALbums);
    }

    private void initComponents() {

        ga = new GetArt(getActivity());
        CustomList adapter = new CustomList(getActivity(), R.layout.list_single, listSong);
        CustomList adapter2 = new CustomList(getActivity(), R.layout.list_single, listAllSong);
        CustomGrid gridAdapter = new CustomGrid(getActivity(), R.layout.grid_single, listAlbum);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Album al = listAlbum.get(position);
                listSong = al.getListSong();
                CustomList adapterAS = new CustomList(getActivity(), R.layout.list_single, listSong);
                listAlbumSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        play(position);
                        Song song = listSong.get(position);
                        imgNowPlaybtm2.setImageBitmap(ga.getAlbumart(Integer.parseInt(song.getImage())));
                        txtNowPlaybtm2.setText(song.getTitle());
                        txtArtistPlaying2.setText(song.getArtist());
                        imgNowPlaybtm.setImageBitmap(ga.getAlbumart(Integer.parseInt(song.getImage())));
                        txtNowPlaybtm.setText(song.getTitle());
                        txtArtistPlaying.setText(song.getArtist());
//                        NotificationGenerator.customNotification(getActivity(), song);
                        CustomList adapter = new CustomList(getActivity(), R.layout.list_single, listSong);
                        listNowPlay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                play(position);
                                Song song = listSong.get(position);
                                imgNowPlaybtm2.setImageBitmap(ga.getAlbumart(Integer.parseInt(song.getImage())));
                                txtNowPlaybtm2.setText(song.getTitle());
                                txtArtistPlaying2.setText(song.getArtist());
                                imgNowPlaybtm.setImageBitmap(ga.getAlbumart(Integer.parseInt(song.getImage())));
                                txtNowPlaybtm.setText(song.getTitle());
                                txtArtistPlaying.setText(song.getArtist());
                            }
                        });
                        listNowPlay.setAdapter(adapter);
                    }
                });
                listAlbumSong.setAdapter(adapterAS);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("mylibrary"));
                transaction.show(getActivity().getSupportFragmentManager().findFragmentByTag("albumsong"));
                addPreferences();
                transaction.commit();
            }
        });

        listNowPlay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listSong = listAllSong;
                Song song = listAllSong.get(position);
                Bitmap image = ga.getAlbumart(Integer.parseInt(song.getImage()));
                play(position);
                imgNowPlaybtm.setImageBitmap(image);
                txtNowPlaybtm.setText(song.getTitle());
                txtArtistPlaying.setText(song.getArtist());
                imgNowPlaybtm2.setImageBitmap(image);
                txtNowPlaybtm2.setText(song.getTitle());
                txtArtistPlaying2.setText(song.getArtist());
//                NotificationGenerator.customNotification(getActivity(), song);
            }
        });

        listSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listSong = listAllSong;
                Song song = listAllSong.get(position);
                play(position);
                imgNowPlaybtm.setImageBitmap(ga.getAlbumart(Integer.parseInt(song.getImage())));
                txtNowPlaybtm.setText(song.getTitle());
                txtArtistPlaying.setText(song.getArtist());
                imgNowPlaybtm2.setImageBitmap(ga.getAlbumart(Integer.parseInt(song.getImage())));
                txtNowPlaybtm2.setText(song.getTitle());
                txtArtistPlaying2.setText(song.getArtist());
//                NotificationGenerator.customNotification(getActivity(), song);
                CustomList adapter = new CustomList(getActivity(), R.layout.list_single, listSong);
                listSongs.setAdapter(adapter);
            }
        });


        listNowPlay.setAdapter(adapter);
        listSongs.setAdapter(adapter2);
        gridView.setAdapter(gridAdapter);


        mBtnPlay.setOnClickListener(this);
        mBtnPause.setOnClickListener(this);
        mBtnRepeat.setOnClickListener(this);
        mBtnRepeat1.setOnClickListener(this);
        mBtnRepeatAll.setOnClickListener(this);
        mBtnshuffer.setOnClickListener(this);
        mBtnShufferOn.setOnClickListener(this);
        mBtnPrev.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        imgNowPlaybtm.setOnClickListener(this);
        txtNowPlaybtm2.setOnClickListener(this);
        txtArtistPlaying2.setOnClickListener(this);
        imgNowPlaybtm2.setOnClickListener(this);
        txtNowPlaybtm.setOnClickListener(this);
        txtArtistPlaying.setOnClickListener(this);
        mPlayer = new MediaPlayer();

    }

    private void addPreferences(){
        SharedPreferences sp = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("back", "albumsong");
        edit.commit();
    }

    private void setSeek(int duration) {
        long minuteEnd = TimeUnit.MILLISECONDS.toMinutes(duration);
        long secondEnd = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(minuteEnd);
        mTvTimeEnd.setText(String.format("%d:%d", minuteEnd, secondEnd));
        mSeek.setMax(duration);
        mSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mPlayer.seekTo(progress);
                    getActivity().stopService(new Intent(getActivity(), MyService.class));
                    myService = new Intent(getActivity(), MyService.class);
                    myService.putExtra("listsong", listSong);
                    myService.putExtra("seek", progress);
                    myService.putExtra("song", currentId);
                    myService.putExtra("haveseek", true);
                    getActivity().startService(myService);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void play(int positiion) {
        getActivity().stopService(new Intent(getActivity(), MyService.class));
        myService = new Intent(getActivity(), MyService.class);
        myService.putExtra("listsong", listSong);
        myService.putExtra("song", positiion);
        myService.putExtra("repeat", mBtnRepeat1.getVisibility());
        myService.putExtra("repeatall", mBtnRepeatAll.getVisibility());
        getActivity().startService(myService);
        currentId = positiion;
        try {
            mPlayer = new MediaPlayer(); // Mỗi lần dùng mới thì phải tạo lại...
            mPlayer.setDataSource(listSong.get(positiion).getPath());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.setVolume(0,0);
        mBtnPlay.setVisibility(View.INVISIBLE);
        mBtnPause.setVisibility(View.VISIBLE);
        setSeek(Integer.parseInt(listSong.get(positiion).getLength()));
        mHandler.postDelayed(runnable, 100);

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mBtnRepeat1.getVisibility() == 0) {
                    repeat1();
                }
                if(mBtnRepeatAll.getVisibility() == 0) {
                    repeatAll();
                }
                if(!mPlayer.isPlaying()){
                    mBtnPause.setVisibility(View.INVISIBLE);
                    mBtnPlay.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int timeBegin = mPlayer.getCurrentPosition();
            long minuteBegin = TimeUnit.MILLISECONDS.toMinutes(timeBegin);
            long secondBegin = TimeUnit.MILLISECONDS.toSeconds(timeBegin) - TimeUnit.MINUTES.toSeconds(minuteBegin);
            mTvTimeBegin.setText(String.format("%d:%d", minuteBegin, secondBegin));
            mHandler.postDelayed(this, 100);
            mSeek.setProgress(timeBegin);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_noti_play:
                playing();
                break;
            case R.id.btn_noti_pause:
                pauseSong();
                break;
            case R.id.btn_next:
                playNext();
                break;
            case R.id.btn_prev:
                playPreveuos();
                break;
            case R.id.btn_shuffle:
                mBtnShufferOn.setVisibility(View.VISIBLE);
                mBtnshuffer.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_shuffle_on:
                mBtnShufferOn.setVisibility(View.INVISIBLE);
                mBtnshuffer.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_repeat:
                mBtnRepeat.setVisibility(View.INVISIBLE);
                mBtnRepeat1.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_repeat_1:
                mBtnRepeat1.setVisibility(View.INVISIBLE);
                mBtnRepeatAll.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_repeat_all:
                mBtnRepeat.setVisibility(View.VISIBLE);
                mBtnRepeatAll.setVisibility(View.INVISIBLE);
                break;
            case R.id.imgNowPlaybtm:
                changeFragment();
                break;
            case R.id.txtNowPlaybtm:
                changeFragment();
                break;
            case R.id.imgNowPlaybtm2:
                changeFragment();
                break;
            case R.id.txtNowPlaybtm2:
                changeFragment();
                break;
            case R.id.txtArtistPlaying:
                changeFragment();
                break;
            case R.id.txtArtistPlaying2:
                changeFragment();
                break;
            default:
                break;
        }
    }

    private void changeFragment(){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.show(getActivity().getSupportFragmentManager().findFragmentByTag("playing"));
        transaction.commit();
    }

    private void pauseSong() {
        mPlayer.pause();
        getActivity().stopService(new Intent(getActivity(), MyService.class));
        mBtnPlay.setVisibility(View.VISIBLE);
        mBtnPause.setVisibility(View.INVISIBLE);
    }

    private void playing() {
        mPlayer.start();
        myService = new Intent(getActivity(), MyService.class);
        myService.putExtra("listsong", listSong);
        myService.putExtra("seek", mPlayer.getCurrentPosition());
        myService.putExtra("song", currentId);
        myService.putExtra("haveseek", true);
        getActivity().startService(myService);
        mBtnPlay.setVisibility(View.INVISIBLE);
        mBtnPause.setVisibility(View.VISIBLE);
        setSeek(mPlayer.getDuration());
    }

    private void playNext() {
        if (currentId < listSong.size() - 1) {
            play(++currentId);
        } else {
            currentId = 0;
            play(currentId);
        }
    }

    private void playPreveuos() {
        if (currentId > 0) {
            play(--currentId);
        } else {
            currentId = listSong.size() - 1;
            play(currentId);
        }
    }

    private void repeatAll() {
        mPlayer = new MediaPlayer();
        if(mBtnShufferOn.getVisibility() == 0) {
            Random rd = new Random();
            currentId = rd.nextInt(listSong.size() - 1);
        } else {
            if (currentId < listSong.size() - 1) {
                currentId++;
            } else {
                currentId = 0;
            }
        }
        play(currentId);
        setSeek(Integer.parseInt(listSong.get(currentId).getLength()));
        mHandler.postDelayed(runnable, 100);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mBtnRepeat1.getVisibility() == 0) {
                    repeat1();
                }
                if(mBtnRepeatAll.getVisibility() == 0) {
                    repeatAll();
                }
                if(!mPlayer.isPlaying()){
                    mBtnPause.setVisibility(View.INVISIBLE);
                    mBtnPlay.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void repeat1() {
        mPlayer = new MediaPlayer();
        play(currentId);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(mBtnRepeat1.getVisibility() == 0) {
                    repeat1();
                }
                if(mBtnRepeatAll.getVisibility() == 0) {
                    repeatAll();
                }
                if(!mPlayer.isPlaying()){
                    mBtnPause.setVisibility(View.INVISIBLE);
                    mBtnPlay.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}
