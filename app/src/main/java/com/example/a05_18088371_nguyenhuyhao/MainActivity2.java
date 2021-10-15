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


        mSongs = (List<Song>) getIntent().getSerializableExtra("listMusic");
        Song song = (Song) getIntent().getSerializableExtra("song");
        currentIndex = getIntent().getIntExtra("index", 0);

        if (song != null) {
            image_layout2.setImageResource(song.getImageSong());//
            songTitle.setText(song.getTitle());
            songSinger.setText(song.getSingle());
            mMediaPlayer = MediaPlayer.create(getApplicationContext(), song.getResourece());
        }

    }
}