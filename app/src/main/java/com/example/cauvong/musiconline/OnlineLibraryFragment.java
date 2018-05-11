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

    private String apiKey = "AIzaSypackage com.example.cauvong.musiconline;\n" +
            "\n" +
            "import android.app.Activity;\n" +
            "import android.content.Context;\n" +
            "import android.content.SharedPreferences;\n" +
            "import android.graphics.Bitmap;\n" +
            "import android.graphics.BitmapFactory;\n" +
            "import android.media.MediaPlayer;\n" +
            "import android.os.Bundle;\n" +
            "import android.os.Handler;\n" +
            "import android.support.annotation.Nullable;\n" +
            "import android.support.v4.app.Fragment;\n" +
            "import android.support.v4.app.FragmentManager;\n" +
            "import android.support.v4.app.FragmentTransaction;\n" +
            "import android.view.LayoutInflater;\n" +
            "import android.view.View;\n" +
            "import android.view.ViewGroup;\n" +
            "import android.widget.AdapterView;\n" +
            "import android.widget.Button;\n" +
            "import android.widget.GridView;\n" +
            "import android.widget.ImageView;\n" +
            "import android.widget.ListView;\n" +
            "import android.widget.SeekBar;\n" +
            "import android.widget.TextView;\n" +
            "import android.widget.Toast;\n" +
            "\n" +
            "import java.io.IOException;\n" +
            "import java.util.ArrayList;\n" +
            "import java.util.Collections;\n" +
            "import java.util.concurrent.TimeUnit;\n" +
            "\n" +
            "/**\n" +
            " * Created by tusss on 1/11/2018.\n" +
            " */\n" +
            "\n" +
            "public class OfflineFragment extends Fragment implements View.OnClickListener {\n" +
            "\n" +
            "    private ArrayList<Song> listSong = new ArrayList<>();\n" +
            "    private ArrayList<Song> listAllSong = new ArrayList<>();\n" +
            "    private ArrayList<Album> listAlbum = new ArrayList<>();\n" +
            "    private SeekBar mSeek;\n" +
            "    private MediaPlayer mPlayer;\n" +
            "    private Button mBtnPlay, mBtnPause, mBtnshuffer, mBtnShufferOn, mBtnRepeat, mBtnRepeat1, mBtnRepeatAll, mBtnPrev, mBtnNext;\n" +
            "    private TextView mTvTimeBegin, mTvTimeEnd;\n" +
            "    private Handler mHandler = new Handler();\n" +
            "    private int currentId = 0;\n" +
            "    private ListView listNowPlay;\n" +
            "    private ListView listSongs;\n" +
            "    private ListView listAlbumSong;\n" +
            "    private ImageView imgNowPlaybtm;\n" +
            "    private TextView txtNowPlaybtm;\n" +
            "    private ImageView imgNowPlaybtm2;\n" +
            "    private TextView txtNowPlaybtm2;\n" +
            "    private GetArt ga;\n" +
            "    private TextView txtArtistPlaying;\n" +
            "    private TextView txtArtistPlaying2;\n" +
            "    private GridView gridView;\n" +
            "    private TextView txtNotiSongName;\n" +
            "    private ImageView imgNotiSongImg;\n" +
            "\n" +
            "\n" +
            "    @Nullable\n" +
            "    @Override\n" +
            "    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {\n" +
            "        View view = inflater.inflate(R.layout.now_play, container, false);\n" +
            "\n" +
            "        return view;\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {\n" +
            "\n" +
            "        super.onViewCreated(view, savedInstanceState);\n" +
            "        ScanMusic sc = new ScanMusic(getActivity());\n" +
            "        listAllSong = sc.getMusic();\n" +
            "        listSong = sc.getMusic();\n" +
            "        ScanAlbum scanA = new ScanAlbum();\n" +
            "        listAlbum = scanA.getAlbum(listSong);\n" +
            "        findViews();\n" +
            "        initComponents();\n" +
            "    }\n" +
            "\n" +
            "    private void findViews() {\n" +
            "        mSeek = (SeekBar) getView().findViewById(R.id.seekbar);\n" +
            "        mBtnPlay = (Button) getView().findViewById(R.id.btn_noti_play);\n" +
            "        mBtnPause = (Button) getView().findViewById(R.id.btn_noti_pause);\n" +
            "        mBtnRepeat = (Button) getView().findViewById(R.id.btn_repeat);\n" +
            "        mBtnRepeat1 = (Button) getView().findViewById(R.id.btn_repeat_1);\n" +
            "        mBtnRepeatAll = (Button) getView().findViewById(R.id.btn_repeat_all);\n" +
            "        mBtnshuffer = (Button) getView().findViewById(R.id.btn_shuffle);\n" +
            "        mBtnShufferOn = (Button) getView().findViewById(R.id.btn_shuffle_on);\n" +
            "        mBtnPrev = (Button) getView().findViewById(R.id.btn_prev);\n" +
            "        mBtnNext = (Button) getView().findViewById(R.id.btn_next);\n" +
            "        mTvTimeBegin = (TextView) getView().findViewById(R.id.tv_time_run);\n" +
            "        mTvTimeEnd = (TextView) getView().findViewById(R.id.tv_time_total);\n" +
            "        listNowPlay = (ListView) getActivity().findViewById(R.id.listNowPlay);\n" +
            "        listSongs = (ListView) getActivity().findViewById(R.id.listSongs);\n" +
            "        listAlbumSong = (ListView) getActivity().findViewById(R.id.listAlbumSongs);\n" +
            "        imgNowPlaybtm = (ImageView) getActivity().findViewById(R.id.imgNowPlaybtm);\n" +
            "        txtNowPlaybtm = (TextView) getActivity().findViewById(R.id.txtNowPlaybtm);\n" +
            "        imgNowPlaybtm2 = (ImageView) getActivity().findViewById(R.id.imgNowPlaybtm2);\n" +
            "        txtNowPlaybtm2 = (TextView) getActivity().findViewById(R.id.txtNowPlaybtm2);\n" +
            "        txtArtistPlaying = (TextView) getActivity().findViewById(R.id.txtArtistPlaying);\n" +
            "        txtArtistPlaying2 = (TextView) getActivity().findViewById(R.id.txtArtistPlaying2);\n" +
            "        gridView = (GridView) getActivity().findViewById(R.id.gridALbums);\n" +
            "    }\n" +
            "\n" +
            "    private void initComponents() {\n" +
            "\n" +
            "        ga = new GetArt(getActivity());\n" +
            "        CustomList adapter = new CustomList(getActivity(), R.layout.list_single, listSong);\n" +
            "        CustomList adapter2 = new CustomList(getActivity(), R.layout.list_single, listAllSong);\n" +
            "        CustomGrid gridAdapter = new CustomGrid(getActivity(), R.layout.grid_single, listAlbum);\n" +
            "\n" +
            "        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
            "            @Override\n" +
            "            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {\n" +
            "\n" +
            "                Album al = listAlbum.get(position);\n" +
            "                listSong = al.getListSong();\n" +
            "                CustomList adapterAS = new CustomList(getActivity(), R.layout.list_single, listSong);\n" +
            "                listAlbumSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
            "                    @Override\n" +
            "                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {\n" +
            "                        play(position);\n" +
            "                        Song song = listSong.get(position);\n" +
            "                        imgNowPlaybtm2.setImageBitmap(ga.getAlbumart(Integer.parseInt(song.getImage())));\n" +
            "                        txtNowPlaybtm2.setText(song.getTitle());\n" +
            "                        txtArtistPlaying2.setText(song.getArtist());\n" +
            "                        imgNowPlaybtm.setImageBitmap(ga.getAlbumart(Integer.parseInt(song.getImage())));\n" +
            "                        txtNowPlaybtm.setText(song.getTitle());\n" +
            "                        txtArtistPlaying.setText(song.getArtist());\n" +
            "                        NotificationGenerator.customNotification(getActivity(), song);\n" +
            "                        CustomList adapter = new CustomList(getActivity(), R.layout.list_single, listSong);\n" +
            "                        listNowPlay.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
            "                            @Override\n" +
            "                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {\n" +
            "                                play(position);\n" +
            "                                Song song = listSong.get(position);\n" +
            "                                imgNowPlaybtm2.setImageBitmap(ga.getAlbumart(Integer.parseInt(song.getImage())));\n" +
            "                                txtNowPlaybtm2.setText(song.getTitle());\n" +
            "                                txtArtistPlaying2.setText(song.getArtist());\n" +
            "                                imgNowPlaybtm.setImageBitmap(ga.getAlbumart(Integer.parseInt(song.getImage())));\n" +
            "                                txtNowPlaybtm.setText(song.getTitle());\n" +
            "                                txtArtistPlaying.setText(song.getArtist());\n" +
            "                            }\n" +
            "                        });\n" +
            "                        listNowPlay.setAdapter(adapter);\n" +
            "                    }\n" +
            "                });\n" +
            "                listAlbumSong.setAdapter(adapterAS);\n" +
            "                FragmentManager manager = getActivity().getSupportFragmentManager();\n" +
            "                FragmentTransaction transaction = manager.beginTransaction();\n" +
            "                transaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag(\"mylibrary\"));\n" +
            "                transaction.show(getActivity().getSupportFragmentManager().findFragmentByTag(\"albumsong\"));\n" +
            "                addPreferences();\n" +
            "                transaction.commit();\n" +
            "            }\n" +
            "        });\n" +
            "\n" +
            "        listNowPlay.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
            "            @Override\n" +
            "            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {\n" +
            "                listSong = listAllSong;\n" +
            "                Song song = listAllSong.get(position);\n" +
            "                Bitmap image = ga.getAlbumart(Integer.parseInt(song.getImage()));\n" +
            "                play(position);\n" +
            "                imgNowPlaybtm.setImageBitmap(image);\n" +
            "                txtNowPlaybtm.setText(song.getTitle());\n" +
            "                txtArtistPlaying.setText(song.getArtist());\n" +
            "                imgNowPlaybtm2.setImageBitmap(image);\n" +
            "                txtNowPlaybtm2.setText(song.getTitle());\n" +
            "                txtArtistPlaying2.setText(song.getArtist());\n" +
            "                NotificationGenerator.customNotification(getActivity(), song);\n" +
            "                CustomList adapter = new CustomList(getActivity(), R.layout.list_single, listSong);\n" +
            "                listNowPlay.setAdapter(adapter);\n" +
            "            }\n" +
            "        });\n" +
            "\n" +
            "        listSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {\n" +
            "            @Override\n" +
            "            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {\n" +
            "                listSong = listAllSong;\n" +
            "                Song song = listAllSong.get(position);\n" +
            "                play(position);\n" +
            "                imgNowPlaybtm.setImageBitmap(ga.getAlbumart(Integer.parseInt(song.getImage())));\n" +
            "                txtNowPlaybtm.setText(song.getTitle());\n" +
            "                txtArtistPlaying.setText(song.getArtist());\n" +
            "                imgNowPlaybtm2.setImageBitmap(ga.getAlbumart(Integer.parseInt(song.getImage())));\n" +
            "                txtNowPlaybtm2.setText(song.getTitle());\n" +
            "                txtArtistPlaying2.setText(song.getArtist());\n" +
            "                NotificationGenerator.customNotification(getActivity(), song);\n" +
            "                CustomList adapter = new CustomList(getActivity(), R.layout.list_single, listSong);\n" +
            "                listSongs.setAdapter(adapter);\n" +
            "            }\n" +
            "        });\n" +
            "\n" +
            "        imgNowPlaybtm.setOnClickListener(new View.OnClickListener() {\n" +
            "            @Override\n" +
            "            public void onClick(View v) {\n" +
            "                FragmentManager manager = getActivity().getSupportFragmentManager();\n" +
            "                FragmentTransaction transaction = manager.beginTransaction();\n" +
            "                transaction.show(getActivity().getSupportFragmentManager().findFragmentByTag(\"playing\"));\n" +
            "                transaction.commit();\n" +
            "            }\n" +
            "        });\n" +
            "\n" +
            "        txtArtistPlaying.setOnClickListener(new View.OnClickListener() {\n" +
            "            @Override\n" +
            "            public void onClick(View v) {\n" +
            "                FragmentManager manager = getActivity().getSupportFragmentManager();\n" +
            "                FragmentTransaction transaction = manager.beginTransaction();\n" +
            "                transaction.show(getActivity().getSupportFragmentManager().findFragmentByTag(\"playing\"));\n" +
            "                transaction.commit();\n" +
            "            }\n" +
            "        });\n" +
            "\n" +
            "        txtNowPlaybtm.setOnClickListener(new View.OnClickListener() {\n" +
            "            @Override\n" +
            "            public void onClick(View v) {\n" +
            "                FragmentManager manager = getActivity().getSupportFragmentManager();\n" +
            "                FragmentTransaction transaction = manager.beginTransaction();\n" +
            "                transaction.show(getActivity().getSupportFragmentManager().findFragmentByTag(\"playing\"));\n" +
            "                transaction.commit();\n" +
            "            }\n" +
            "        });\n" +
            "\n" +
            "        imgNowPlaybtm2.setOnClickListener(new View.OnClickListener() {\n" +
            "            @Override\n" +
            "            public void onClick(View v) {\n" +
            "                FragmentManager manager = getActivity().getSupportFragmentManager();\n" +
            "                FragmentTransaction transaction = manager.beginTransaction();\n" +
            "                transaction.show(getActivity().getSupportFragmentManager().findFragmentByTag(\"playing\"));\n" +
            "                transaction.commit();\n" +
            "            }\n" +
            "        });\n" +
            "\n" +
            "        txtArtistPlaying2.setOnClickListener(new View.OnClickListener() {\n" +
            "            @Override\n" +
            "            public void onClick(View v) {\n" +
            "                FragmentManager manager = getActivity().getSupportFragmentManager();\n" +
            "                FragmentTransaction transaction = manager.beginTransaction();\n" +
            "                transaction.show(getActivity().getSupportFragmentManager().findFragmentByTag(\"playing\"));\n" +
            "                transaction.commit();\n" +
            "            }\n" +
            "        });\n" +
            "\n" +
            "        txtNowPlaybtm2.setOnClickListener(new View.OnClickListener() {\n" +
            "            @Override\n" +
            "            public void onClick(View v) {\n" +
            "                FragmentManager manager = getActivity().getSupportFragmentManager();\n" +
            "                FragmentTransaction transaction = manager.beginTransaction();\n" +
            "                transaction.show(getActivity().getSupportFragmentManager().findFragmentByTag(\"playing\"));\n" +
            "                transaction.commit();\n" +
            "            }\n" +
            "        });\n" +
            "\n" +
            "        listNowPlay.setAdapter(adapter);\n" +
            "        listSongs.setAdapter(adapter2);\n" +
            "        gridView.setAdapter(gridAdapter);\n" +
            "\n" +
            "\n" +
            "        mBtnPlay.setOnClickListener(this);\n" +
            "        mBtnPause.setOnClickListener(this);\n" +
            "        mBtnRepeat.setOnClickListener(this);\n" +
            "        mBtnRepeat1.setOnClickListener(this);\n" +
            "        mBtnRepeatAll.setOnClickListener(this);\n" +
            "        mBtnshuffer.setOnClickListener(this);\n" +
            "        mBtnShufferOn.setOnClickListener(this);\n" +
            "        mBtnPrev.setOnClickListener(this);\n" +
            "        mBtnNext.setOnClickListener(this);\n" +
            "        mPlayer = new MediaPlayer();\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "    private void addPreferences(){\n" +
            "        SharedPreferences sp = getActivity().getSharedPreferences(\"MyPrefs\", Context.MODE_PRIVATE);\n" +
            "        SharedPreferences.Editor edit = sp.edit();\n" +
            "        edit.putString(\"back\", \"albumsong\");\n" +
            "        edit.commit();\n" +
            "    }\n" +
            "\n" +
            "    private void setSeek(int duration) {\n" +
            "        long minuteEnd = TimeUnit.MILLISECONDS.toMinutes(duration);\n" +
            "        long secondEnd = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(minuteEnd);\n" +
            "        mTvTimeEnd.setText(String.format(\"%d:%d\", minuteEnd, secondEnd));\n" +
            "        mSeek.setMax(duration);\n" +
            "        mSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {\n" +
            "            @Override\n" +
            "            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {\n" +
            "                if (fromUser) {\n" +
            "                    mPlayer.seekTo(progress);\n" +
            "                }\n" +
            "            }\n" +
            "\n" +
            "            @Override\n" +
            "            public void onStartTrackingTouch(SeekBar seekBar) {\n" +
            "\n" +
            "            }\n" +
            "\n" +
            "            @Override\n" +
            "            public void onStopTrackingTouch(SeekBar seekBar) {\n" +
            "\n" +
            "            }\n" +
            "        });\n" +
            "    }\n" +
            "\n" +
            "    private void play(int positiion) {\n" +
            "        if (mPlayer.isPlaying()) {\n" +
            "            mPlayer.stop();\n" +
            "            mPlayer.release();\n" +
            "            mPlayer = null; // dùng xong xóa,,,,\n" +
            "            mHandler.removeCallbacks(runnable);\n" +
            "        }\n" +
            "        try {\n" +
            "            mPlayer = new MediaPlayer(); // Mỗi lần dùng mới thì phải tạo lại...\n" +
            "            mPlayer.setDataSource(listSong.get(positiion).getPath());\n" +
            "            mPlayer.prepare();\n" +
            "            mPlayer.start();\n" +
            "        } catch (IOException e) {\n" +
            "            e.printStackTrace();\n" +
            "        }\n" +
            "\n" +
            "        mBtnPlay.setVisibility(View.INVISIBLE);\n" +
            "        mBtnPause.setVisibility(View.VISIBLE);\n" +
            "        setSeek(Integer.parseInt(listSong.get(positiion).getLength()));\n" +
            "        mHandler.postDelayed(runnable, 100);\n" +
            "\n" +
            "        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {\n" +
            "            @Override\n" +
            "            public void onCompletion(MediaPlayer mp) {\n" +
            "                mBtnPause.setVisibility(View.INVISIBLE);\n" +
            "                mBtnPlay.setVisibility(View.VISIBLE);\n" +
            "            }\n" +
            "        });\n" +
            "//        if(mBtnRepeat1.getVisibility() == View.VISIBLE) repeat1();\n" +
            "//        if(mBtnShufferOn.getVisibility() == View.VISIBLE) shuffer();\n" +
            "//        playNext();\n" +
            "    }\n" +
            "\n" +
            "    public static Bitmap decodeFile(String f, int WIDTH, int HIGHT) {\n" +
            "        try {\n" +
            "            //Decode image size\n" +
            "            BitmapFactory.Options o = new BitmapFactory.Options();\n" +
            "            o.inJustDecodeBounds = true;\n" +
            "            BitmapFactory.decodeFile(f, o);\n" +
            "\n" +
            "            //The new size we want to scale to\n" +
            "            final int REQUIRED_WIDTH = WIDTH;\n" +
            "            final int REQUIRED_HIGHT = HIGHT;\n" +
            "            //Find the correct scale value. It should be the power of 2.\n" +
            "            int scale = 1;\n" +
            "            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH && o.outHeight / scale / 2 >= REQUIRED_HIGHT)\n" +
            "                scale *= 2;\n" +
            "\n" +
            "            //Decode with inSampleSize\n" +
            "            BitmapFactory.Options o2 = new BitmapFactory.Options();\n" +
            "            o2.inSampleSize = scale;\n" +
            "            return BitmapFactory.decodeFile(f, o2);\n" +
            "        } catch (Exception e) {\n" +
            "        }\n" +
            "        return null;\n" +
            "    }\n" +
            "\n" +
            "    private Runnable runnable = new Runnable() {\n" +
            "        @Override\n" +
            "        public void run() {\n" +
            "            int timeBegin = mPlayer.getCurrentPosition();\n" +
            "            long minuteBegin = TimeUnit.MILLISECONDS.toMinutes(timeBegin);\n" +
            "            long secondBegin = TimeUnit.MILLISECONDS.toSeconds(timeBegin) - TimeUnit.MINUTES.toSeconds(minuteBegin);\n" +
            "            mTvTimeBegin.setText(String.format(\"%d:%d\", minuteBegin, secondBegin));\n" +
            "            mHandler.postDelayed(this, 100);\n" +
            "            mSeek.setProgress(timeBegin);\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    @Override\n" +
            "    public void onClick(View v) {\n" +
            "        switch (v.getId()) {\n" +
            "            case R.id.btn_noti_play:\n" +
            "                playing();\n" +
            "                break;\n" +
            "            case R.id.btn_noti_pause:\n" +
            "                pauseSong();\n" +
            "                break;\n" +
            "            case R.id.btn_next:\n" +
            "                playNext();\n" +
            "                break;\n" +
            "            case R.id.btn_prev:\n" +
            "                playPreveuos();\n" +
            "                break;\n" +
            "            case R.id.btn_shuffle:\n" +
            "                shuffer();\n" +
            "                break;\n" +
            "            case R.id.btn_shuffle_on:\n" +
            "                shufferOn();\n" +
            "                break;\n" +
            "            case R.id.btn_repeat:\n" +
            "                mBtnRepeat.setVisibility(View.INVISIBLE);\n" +
            "                mBtnRepeat1.setVisibility(View.VISIBLE);\n" +
            "                repeat1();\n" +
            "                break;\n" +
            "            case R.id.btn_repeat_1:\n" +
            "                mBtnRepeat1.setVisibility(View.INVISIBLE);\n" +
            "                mBtnRepeatAll.setVisibility(View.VISIBLE);\n" +
            "                repeatAll();\n" +
            "                break;\n" +
            "            case R.id.btn_repeat_all:\n" +
            "                repeat();\n" +
            "                break;\n" +
            "            default:\n" +
            "                break;\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "    private void pauseSong() {\n" +
            "        mPlayer.pause();\n" +
            "        mBtnPlay.setVisibility(View.VISIBLE);\n" +
            "        mBtnPause.setVisibility(View.INVISIBLE);\n" +
            "    }\n" +
            "\n" +
            "    private void playing() {\n" +
            "        mPlayer.start();\n" +
            "//        play(0);\n" +
            "        mBtnPlay.setVisibility(View.INVISIBLE);\n" +
            "        mBtnPause.setVisibility(View.VISIBLE);\n" +
            "        setSeek(mPlayer.getDuration());\n" +
            "    }\n" +
            "\n" +
            "    private void playNext() {\n" +
            "        if (currentId < listSong.size() - 1) {\n" +
            "            play(++currentId);\n" +
            "        } else {\n" +
            "            currentId = 0;\n" +
            "            play(currentId);\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    private void playPreveuos() {\n" +
            "        if (currentId > 0) {\n" +
            "            play(--currentId);\n" +
            "        } else {\n" +
            "            currentId = listSong.size() - 1;\n" +
            "            play(currentId);\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    private void shufferOn() {\n" +
            "        mBtnShufferOn.setVisibility(View.INVISIBLE);\n" +
            "        mBtnshuffer.setVisibility(View.VISIBLE);\n" +
            "    }\n" +
            "\n" +
            "    private void shuffer() {\n" +
            "        Collections.shuffle(listSong);\n" +
            "        mBtnShufferOn.setVisibility(View.VISIBLE);\n" +
            "        mBtnshuffer.setVisibility(View.INVISIBLE);\n" +
            "    }\n" +
            "\n" +
            "    private void repeat() {\n" +
            "        mBtnRepeatAll.setVisibility(View.INVISIBLE);\n" +
            "        mBtnRepeat.setVisibility(View.VISIBLE);\n" +
            "        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {\n" +
            "            @Override\n" +
            "            public void onCompletion(MediaPlayer mp) {\n" +
            "                mBtnPause.setVisibility(View.INVISIBLE);\n" +
            "                mBtnPlay.setVisibility(View.VISIBLE);\n" +
            "            }\n" +
            "        });\n" +
            "    }\n" +
            "\n" +
            "    private void repeatAll() {\n" +
            "        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {\n" +
            "            @Override\n" +
            "            public void onCompletion(MediaPlayer mp) {\n" +
            "                if (currentId < listSong.size() - 1) {\n" +
            "                    play(++currentId);\n" +
            "                } else {\n" +
            "                    currentId = 0;\n" +
            "                    play(currentId);\n" +
            "                }\n" +
            "                if (mBtnRepeatAll.getVisibility() == View.VISIBLE) {\n" +
            "                    repeatAll();\n" +
            "                }\n" +
            "            }\n" +
            "        });\n" +
            "    }\n" +
            "\n" +
            "    private void repeat1() {\n" +
            "        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {\n" +
            "            @Override\n" +
            "            public void onCompletion(MediaPlayer mp) {\n" +
            "                mPlayer = new MediaPlayer();\n" +
            "                try {\n" +
            "                    mPlayer.setDataSource(listSong.get(currentId).getPath());\n" +
            "                    mPlayer.prepare();\n" +
            "                    mPlayer.start();\n" +
            "                    if (mBtnRepeat1.getVisibility() == View.VISIBLE)\n" +
            "                        repeat1();\n" +
            "                } catch (IOException e) {\n" +
            "                    e.printStackTrace();\n" +
            "                }\n" +
            "            }\n" +
            "        });\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "}\nBCepNUGcqELdmLdgOgaUri_kcQ7vXeziQ";
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
