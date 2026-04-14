package com.example.task51;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    List<NewsItem> fullList;
    List<NewsItem> displayList;
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(NewsItem item);
    }

    public NewsAdapter(List<NewsItem> list, OnItemClickListener listener) {
        this.fullList = new ArrayList<>(list);
        this.displayList = new ArrayList<>(list);
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.image);
            title = view.findViewById(R.id.title);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsItem item = displayList.get(position);

        holder.title.setText(item.title);
        holder.image.setImageResource(item.imageResId);

        holder.itemView.setOnClickListener(v -> listener.onClick(item));
    }

    public void filter(String text) {

        displayList.clear();

        if (text == null || text.isEmpty()) {
            displayList.addAll(fullList);
        } else {
            text = text.toLowerCase();

            for (NewsItem item : fullList) {
                if (item.title.toLowerCase().contains(text)) {
                    displayList.add(item);
                }
            }
        }

        notifyDataSetChanged();
    }
    public void updateList(List<NewsItem> newList) {
        displayList.clear();
        displayList.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }
}
