package com.example.a05_18088371_nguyenhuyhao;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    ImageView play, prev, next, image_layout2, imgXoay;
    TextView songTitle, songSinger;
    SeekBar mSeekBarTime;


    List<Song> mSongs;
    static MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        play = findViewById(R.id.Play);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        image_layout2 = findViewById(R.id.image_layout2);
        songTitle = findViewById(R.id.txtNameSong);
        songSinger = findViewById(R.id.txtNameSinger);
        mSeekBarTime = findViewById(R.id.seekBar);
        imgXoay = findViewById(R.id.imgXoay);

        mSongs = (List<Song>) getIntent().getSerializableExtra("listMusic");
        Song song = (Song) getIntent().getSerializableExtra("song");
        currentIndex = getIntent().getIntExtra("index", 0);

        if (song != null) {
            image_layout2.setImageResource(song.getImageSong());//
            songTitle.setText(song.getTitle());
            songSinger.setText(song.getSingle());
            mMediaPlayer = MediaPlayer.create(getApplicationContext(), song.getResourece());
        }

        Animation xoay = AnimationUtils.loadAnimation(this, R.anim.xoay);
        imgXoay.startAnimation(xoay);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //
                mSeekBarTime.setMax(mMediaPlayer.getDuration());
                if(mMediaPlayer!=null && mMediaPlayer.isPlaying()){
                    clickStopService();
                    mMediaPlayer.pause();
                    play.setImageResource(R.drawable.tieptuc);
                }else{
                    clickStartService();
                    mMediaPlayer.start();
                    play.setImageResource(R.drawable.pause);
                }
                SongNames();

//                Context context = getApplicationContext();
//                CharSequence text = "Hello toast!";
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
            }

        });




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMediaPlayer!=null){
                    play.setImageResource(R.drawable.tieptuc);
                }
                if(currentIndex < mSongs.size() - 1){
                    currentIndex++;


                }else{
                    currentIndex = 0;
                }
                if (mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();

                }
                clickStartService();
                mMediaPlayer = MediaPlayer.create(getApplicationContext(),mSongs.get(currentIndex).getResourece());
                mMediaPlayer.start();
                SongNames();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMediaPlayer!=null){
                    play.setImageResource(R.drawable.tieptuc);
                }
                if(currentIndex > 0){
                    currentIndex--;
                }else{
                    currentIndex = mSongs.size()-1;
                }
                if (mMediaPlayer.isPlaying()){
                    mMediaPlayer.stop();
                }
                clickStartService();
                mMediaPlayer = MediaPlayer.create(getApplicationContext(),mSongs.get(currentIndex).getResourece());
                mMediaPlayer.start();
                SongNames();
            }
        });


    }


    private void clickStartService() {
        Intent intent = new Intent(this, MyService.class);
        Bundle bundle = new Bundle();

        Song song = mSongs.get(currentIndex);
        bundle.putSerializable("object_song",song);
        intent.putExtras(bundle);

        startService(intent);

    }

    private void clickStopService() {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }


    private void SongNames() {
        Song song = mSongs.get(currentIndex);
        image_layout2.setImageResource(song.getImageSong());
        songTitle.setText(song.getTitle());
        songSinger.setText(song.getSingle());

        //seek bar duration
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mSeekBarTime.setMax(mMediaPlayer.getDuration());
                mMediaPlayer.start();
            }
        });

        mSeekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mMediaPlayer.seekTo(progress);
                    mSeekBarTime.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mMediaPlayer!= null){
                    try {
                        if(mMediaPlayer.isPlaying()){
                            Message message = new Message();

                            message.what = mMediaPlayer.getCurrentPosition();
                            handler.sendMessage(message);
                            Thread.sleep(1000);
                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @SuppressLint("Handle Leak")
    Handler handler = new Handler () {
        @Override
        public void handleMessage (Message msg) {
            mSeekBarTime.setProgress(msg.what);
        }
    };

    }
