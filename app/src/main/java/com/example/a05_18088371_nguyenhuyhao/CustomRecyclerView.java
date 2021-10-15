package com.example.a05_18088371_nguyenhuyhao;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomRecyclerView extends RecyclerView.Adapter<CustomRecyclerView.ViewHolder>{
    List<Song> mSongs;
    OnClickListener listener;

    public CustomRecyclerView(List<Song> mSongs, OnClickListener listener) {
        this.mSongs = mSongs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerView.ViewHolder holder, int position) {
        Song song = mSongs.get(position);
        holder.asong = song;
        holder.imageView.setImageResource(song.getImageSong());
        holder.songSinger.setText(song.getSingle());
        holder.songTitle.setText(song.getTitle());
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView songTitle, songSinger;
        Song asong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgItem);

            songTitle = itemView.findViewById(R.id.txtTenBaiHat);
            songSinger = itemView.findViewById(R.id.txtCaSi);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.clickItem(asong);
                }
            });
        }
    }
}
