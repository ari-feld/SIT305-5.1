package com.example.task51;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.task51.database.BookmarkEntity;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {

    List<BookmarkEntity> list;
    OnBookmarkClickListener listener;

    // UPDATED constructor WITH listener
    public BookmarkAdapter(List<BookmarkEntity> list, OnBookmarkClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bookmark, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        BookmarkEntity item = list.get(position);

        holder.title.setText(item.title);
        holder.desc.setText(item.description);

        // CLICK EVENT (IMPORTANT)
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, desc;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bookmarkTitle);
            desc = itemView.findViewById(R.id.bookmarkDesc);
        }
    }

    // Listener interface
    public interface OnBookmarkClickListener {
        void onClick(BookmarkEntity item);
    }
}