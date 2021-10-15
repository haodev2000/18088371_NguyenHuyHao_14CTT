package com.example.a05_18088371_nguyenhuyhao;

import java.io.Serializable;

public class Song implements Serializable {
    private String title;
    private String single;
    private int imageSong;
    private int resourece;

    public Song(String title, String single, int imageSong, int resourece) {
        this.title = title;
        this.single = single;
        this.imageSong = imageSong;
        this.resourece = resourece;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public int getImageSong() {
        return imageSong;
    }

    public void setImageSong(int imageSong) {
        this.imageSong = imageSong;
    }

    public int getResourece() {
        return resourece;
    }

    public void setResourece(int resourece) {
        this.resourece = resourece;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", single='" + single + '\'' +
                ", imageSong=" + imageSong +
                ", resourece=" + resourece +
                '}';
    }
}
