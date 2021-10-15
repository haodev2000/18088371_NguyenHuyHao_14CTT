package com.example.a05_18088371_nguyenhuyhao;

import static com.example.a05_18088371_nguyenhuyhao.MyAplication.CHANEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {


    private Song mSong;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Huy Hảo","MyService onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            Song song = (Song) bundle.get("object_song");

            if(song != null) {
                mSong = song;
                sendNotification(song);
            }
        }
        return START_NOT_STICKY;
    }

    private void sendNotification(Song song) {

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), song.getImageSong());

        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.notification);
        remoteViews.setTextViewText(R.id.tv_title_song,song.getTitle());
        remoteViews.setTextViewText(R.id.tv_single_song,song.getSingle());
        remoteViews.setImageViewBitmap(R.id.img_song,bitmap);
        remoteViews.setImageViewResource(R.id.img_play_or_pause, R.drawable.tieptuc);
        remoteViews.setImageViewResource(R.id.img_clear, R.drawable.tat);


        // CHANEL_ID ghi r nhấn alt enter để import từ MyAplication
        Notification notification = new NotificationCompat.Builder(this,CHANEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_album_24)
                .setContentIntent(pendingIntent)
                .setCustomContentView(remoteViews)
                .setSound(null)
                .build();

        startForeground(1, notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Huy Hảo","MyService onDestroy");
    }


}
