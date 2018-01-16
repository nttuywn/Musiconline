package com.example.cauvong.musiconline;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    private ListView listView;
    private ListView listView2;
    private ImageView imgNowPlaybtm;
    private TextView txtNowPlaybtm;
    private ImageView imgNowPlaybtm2;
    private TextView txtNowPlaybtm2;
    private GetArt ga;
    private TextView txtArtistPlaying;
    private TextView txtArtistPlaying2;
    private GridView gridView;
    private ListView listAlbumSong;


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
        if (savedInstanceState != null) {
            Song obj = (Song) savedInstanceState.getSerializable("nowplaying");
            listSong.add(obj);
        } else {
            listSong = sc.getMusic();
        }
        ScanAlbum scanA = new ScanAlbum();
        listAlbum = scanA.getAlbum(listSong);
        findViews();
        initComponents();
    }

    private void findViews() {
        mSeek = (SeekBar) getView().findViewById(R.id.seekbar);
        mBtnPlay = (Button) getView().findViewById(R.id.btn_play);
        mBtnPause = (Button) getView().findViewById(R.id.btn_pause);
        mBtnRepeat = (Button) getView().findViewById(R.id.btn_repeat);
        mBtnRepeat1 = (Button) getView().findViewById(R.id.btn_repeat_1);
        mBtnRepeatAll = (Button) getView().findViewById(R.id.btn_repeat_all);
        mBtnshuffer = (Button) getView().findViewById(R.id.btn_shuffle);
        mBtnShufferOn = (Button) getView().findViewById(R.id.btn_shuffle_on);
        mBtnPrev = (Button) getView().findViewById(R.id.btn_prev);
        mBtnNext = (Button) getView().findViewById(R.id.btn_next);
        mTvTimeBegin = (TextView) getView().findViewById(R.id.tv_time_run);
        mTvTimeEnd = (TextView) getView().findViewById(R.id.tv_time_total);
        listView = (ListView) getView().findViewById(R.id.listNowPlay);
        listView2 = (ListView) getActivity().findViewById(R.id.listSongs);
        imgNowPlaybtm = (ImageView) getActivity().findViewById(R.id.imgNowPlaybtm);
        txtNowPlaybtm = (TextView) getActivity().findViewById(R.id.txtNowPlaybtm);
        imgNowPlaybtm2 = (ImageView) getActivity().findViewById(R.id.imgNowPlaybtm2);
        txtNowPlaybtm2 = (TextView) getActivity().findViewById(R.id.txtNowPlaybtm2);
        txtArtistPlaying = (TextView) getActivity().findViewById(R.id.txtArtistPlaying);
        txtArtistPlaying2 = (TextView) getActivity().findViewById(R.id.txtArtistPlaying2);
        gridView = (GridView) getActivity().findViewById(R.id.gridALbums);
        listAlbumSong = (ListView) getActivity().findViewById(R.id.listAlbumSongs);
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
                        txtArtistPlaying.setText(song.getArtist());
                        imgNowPlaybtm.setImageBitmap(ga.getAlbumart(Integer.parseInt(song.getImage())));
                        txtNowPlaybtm.setText(song.getTitle());
                        txtArtistPlaying.setText(song.getArtist());
                        CustomList adapter = new CustomList(getActivity(), R.layout.list_single, listSong);
                        listView.setAdapter(adapter);
                    }
                });
                listAlbumSong.setAdapter(adapterAS);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("mylibrary"));
                transaction.show(getActivity().getSupportFragmentManager().findFragmentByTag("albumsong"));
                transaction.commit();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                txtArtistPlaying.setText(song.getArtist());
                CustomList adapter = new CustomList(getActivity(), R.layout.list_single, listSong);
                listView.setAdapter(adapter);
            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                txtArtistPlaying.setText(song.getArtist());
                CustomList adapter = new CustomList(getActivity(), R.layout.list_single, listSong);
                listView.setAdapter(adapter);
            }
        });

        imgNowPlaybtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.show(getActivity().getSupportFragmentManager().findFragmentByTag("playing"));
                transaction.commit();
            }
        });

        txtArtistPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.show(getActivity().getSupportFragmentManager().findFragmentByTag("playing"));
                transaction.commit();
            }
        });

        txtNowPlaybtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.show(getActivity().getSupportFragmentManager().findFragmentByTag("playing"));
                transaction.commit();
            }
        });

        imgNowPlaybtm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.show(getActivity().getSupportFragmentManager().findFragmentByTag("playing"));
                transaction.commit();
            }
        });

        txtArtistPlaying2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.show(getActivity().getSupportFragmentManager().findFragmentByTag("playing"));
                transaction.commit();
            }
        });

        txtNowPlaybtm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.show(getActivity().getSupportFragmentManager().findFragmentByTag("playing"));
                transaction.commit();
            }
        });

        listView.setAdapter(adapter);
        listView2.setAdapter(adapter2);
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
        mPlayer = new MediaPlayer();

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
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null; // dùng xong xóa,,,,
            mHandler.removeCallbacks(runnable);
        }
        try {
            mPlayer = new MediaPlayer(); // Mỗi lần dùng mới thì phải tạo lại...
            mPlayer.setDataSource(listSong.get(positiion).getPath());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mBtnPlay.setVisibility(View.INVISIBLE);
        mBtnPause.setVisibility(View.VISIBLE);
        setSeek(Integer.parseInt(listSong.get(positiion).getLength()));
        mHandler.postDelayed(runnable, 100);

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mBtnPause.setVisibility(View.INVISIBLE);
                mBtnPlay.setVisibility(View.VISIBLE);
            }
        });
//        if(mBtnRepeat1.getVisibility() == View.VISIBLE) repeat1();
//        if(mBtnShufferOn.getVisibility() == View.VISIBLE) shuffer();
//        playNext();
    }

    public static Bitmap decodeFile(String f, int WIDTH, int HIGHT) {
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(f, o);

            //The new size we want to scale to
            final int REQUIRED_WIDTH = WIDTH;
            final int REQUIRED_HIGHT = HIGHT;
            //Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH && o.outHeight / scale / 2 >= REQUIRED_HIGHT)
                scale *= 2;

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(f, o2);
        } catch (Exception e) {
        }
        return null;
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
            case R.id.btn_play:
                playing();
                break;
            case R.id.btn_pause:
                pauseSong();
                break;
            case R.id.btn_next:
                playNext();
                break;
            case R.id.btn_prev:
                playPreveuos();
                break;
            case R.id.btn_shuffle:
                shuffer();
                break;
            case R.id.btn_shuffle_on:
                shufferOn();
                break;
            case R.id.btn_repeat:
                mBtnRepeat.setVisibility(View.INVISIBLE);
                mBtnRepeat1.setVisibility(View.VISIBLE);
                repeat1();
                break;
            case R.id.btn_repeat_1:
                mBtnRepeat1.setVisibility(View.INVISIBLE);
                mBtnRepeatAll.setVisibility(View.VISIBLE);
                repeatAll();
                break;
            case R.id.btn_repeat_all:
                repeat();
                break;
            default:
                break;
        }
    }


    private void pauseSong() {
        mPlayer.pause();
        mBtnPlay.setVisibility(View.VISIBLE);
        mBtnPause.setVisibility(View.INVISIBLE);
    }

    private void playing() {
        play(0);
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

    private void shufferOn() {
        mBtnShufferOn.setVisibility(View.INVISIBLE);
        mBtnshuffer.setVisibility(View.VISIBLE);
    }

    private void shuffer() {
        Collections.shuffle(listSong);
        mBtnShufferOn.setVisibility(View.VISIBLE);
        mBtnshuffer.setVisibility(View.INVISIBLE);
    }

    private void repeat() {
        mBtnRepeatAll.setVisibility(View.INVISIBLE);
        mBtnRepeat.setVisibility(View.VISIBLE);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mBtnPause.setVisibility(View.INVISIBLE);
                mBtnPlay.setVisibility(View.VISIBLE);
            }
        });
    }

    private void repeatAll() {
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (currentId < listSong.size() - 1) {
                    play(++currentId);
                } else {
                    currentId = 0;
                    play(currentId);
                }
                if (mBtnRepeatAll.getVisibility() == View.VISIBLE) {
                    repeatAll();
                }
            }
        });
    }

    private void repeat1() {
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mPlayer = new MediaPlayer();
                try {
                    mPlayer.setDataSource(listSong.get(currentId).getPath());
                    mPlayer.prepare();
                    mPlayer.start();
                    if (mBtnRepeat1.getVisibility() == View.VISIBLE)
                        repeat1();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
