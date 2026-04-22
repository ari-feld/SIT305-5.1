package com.example.a51videoplaer.istream.playlist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a51videoplaer.R;
import com.example.a51videoplaer.istream.database.AppDatabase;
import com.example.a51videoplaer.istream.database.PlaylistEntity;
import com.example.a51videoplaer.istream.home.HomeActivity;

import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AppDatabase db;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getInstance(this);

        userId = getIntent().getIntExtra("userId", -1);

        loadPlaylist();
    }

    private String extractVideoId(String url) {

        if (url.contains("v=")) {
            return url.substring(url.indexOf("v=") + 2).split("&")[0];
        }

        if (url.contains("youtu.be/")) {
            return url.substring(url.lastIndexOf("/") + 1);
        }

        return null;
    }

    private void loadPlaylist() {

        new Thread(() -> {

            List<PlaylistEntity> list =
                    db.playlistDao().getUserPlaylist(userId);

            runOnUiThread(() -> {

                PlaylistAdapter adapter = new PlaylistAdapter(list, item -> {

                    String videoId = extractVideoId(item.videoUrl);

                    if (videoId == null) {
                        Toast.makeText(this, "Invalid video URL", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = new Intent(this, com.example.a51videoplaer.istream.player.YouTubePlayerActivity.class);
                    intent.putExtra("videoId", videoId);
                    startActivity(intent);
                });

                recyclerView.setAdapter(adapter);
            });

        }).start();
    }
}