package com.example.a51videoplaer.istream.playlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.a51videoplaer.R;
import com.example.a51videoplaer.istream.database.PlaylistEntity;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onClick(PlaylistEntity item);
    }

    List<PlaylistEntity> list;
    OnItemClickListener listener;

    public PlaylistAdapter(List<PlaylistEntity> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.videoUrlText);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_playlist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PlaylistEntity item = list.get(position);

        holder.text.setText(item.videoUrl);

        holder.itemView.setOnClickListener(v -> listener.onClick(item));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}