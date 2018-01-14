package com.example.cauvong.musiconline;

import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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

public class OfflineActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Song> listSong = new ArrayList<>();
    ArrayList<Song> listAllSong = new ArrayList<>();
    private List<ItemSong> mListItems;
    private SeekBar mSeek;
    private MediaPlayer mPlayer;
    private Button mBtnPlay, mBtnPause, mBtnshuffer, mBtnShufferOn, mBtnRepeat, mBtnRepeat1, mBtnRepeatAll, mBtnPrev, mBtnNext;
    private TextView mTvTimeBegin, mTvTimeEnd;
    private Handler mHandler = new Handler();
    private int currentId = 0;
    private List<String> mLvImage;
    private RelativeLayout mRelative;
    private Button mBtnImageOff, mBtnImageOn;
    private ImageView mImageView;
    private ListView listView;
    private ListView listView2;
    private ImageView imgNowPlaybtm;
    private TextView txtNowPlaybtm;
    private GetArt ga;
    private Button btnLibrary;
    public static Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_play);
        fa = this;
        if (getIntent().getSerializableExtra("music") != null) {
            listSong = new ArrayList<>();
            Song song = (Song) getIntent().getSerializableExtra("music");
            listSong.add(song);
        }
        findViews();
        initComponents();
//        startService();
    }

    @Override
    public void onBackPressed() {
//            setContentView(MainActivity());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void startService() {
        Intent intent = new Intent();
        intent.setClass(this, ServiceMedia.class);
        startService(intent);
    }

    private void findViews() {
        mSeek = (SeekBar) findViewById(R.id.seekbar);
        mBtnPlay = (Button) findViewById(R.id.btn_play);
        mBtnPause = (Button) findViewById(R.id.btn_pause);
        mBtnRepeat = (Button) findViewById(R.id.btn_repeat);
        mBtnRepeat1 = (Button) findViewById(R.id.btn_repeat_1);
        mBtnRepeatAll = (Button) findViewById(R.id.btn_repeat_all);
        mBtnshuffer = (Button) findViewById(R.id.btn_shuffle);
        mBtnShufferOn = (Button) findViewById(R.id.btn_shuffle_on);
        mBtnPrev = (Button) findViewById(R.id.btn_prev);
        mBtnNext = (Button) findViewById(R.id.btn_next);
        mTvTimeBegin = (TextView) findViewById(R.id.tv_time_run);
        mTvTimeEnd = (TextView) findViewById(R.id.tv_time_total);
        mRelative = (RelativeLayout) findViewById(R.id.relative);
        mImageView = (ImageView) findViewById(R.id.iv_background);
        listView = (ListView) findViewById(R.id.listNowPlay);
    }

    private void initComponents() {

//        ga = new GetArt(this);
        btnLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        CustomList adapter = new CustomList(this, R.layout.list_single, listSong);
//        CustomList adapter2 = new CustomList(this,R.layout.list_single, listAllSong);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                play(position);
//                Song song = listSong.get(position);
//                imgNowPlaybtm.setImageBitmap(ga.getAlbumart(Integer.parseInt(song.getImage())));
//                txtNowPlaybtm.setText(song.getTitle());
//            }
//        });

//        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                play(position);
//                Song song = listSong.get(position);
//                imgNowPlaybtm.setImageBitmap(ga.getAlbumart(Integer.parseInt(song.getImage())));
//                txtNowPlaybtm.setText(song.getTitle());
//            }
//        });

        listView.setAdapter(adapter);
//        listView2.setAdapter(adapter2);

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


    public void play(int positiion) {
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
        setSeek(mPlayer.getDuration());
        mHandler.postDelayed(runnable, 100);

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mBtnPause.setVisibility(View.INVISIBLE);
                mBtnPlay.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setImageBg() {
        Random random = new Random();
        int size = mLvImage.size();
        int rand = random.nextInt(size - 1) + 0;
        String path = mLvImage.get(rand);
        Bitmap bitmap = decodeFile(path, mRelative.getWidth(), mRelative.getHeight());
        mImageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha));
        mImageView.setImageBitmap(bitmap);
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
        if (currentId < mListItems.size() - 1) {
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
            currentId = mListItems.size() - 1;
            play(currentId);
        }
    }

    private void shufferOn() {
        mBtnShufferOn.setVisibility(View.INVISIBLE);
        mBtnshuffer.setVisibility(View.VISIBLE);
    }

    private void shuffer() {
        Collections.shuffle(mListItems);
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
                if (currentId < mListItems.size() - 1) {
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
                    mPlayer.setDataSource(mListItems.get(currentId).getmPath());
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
