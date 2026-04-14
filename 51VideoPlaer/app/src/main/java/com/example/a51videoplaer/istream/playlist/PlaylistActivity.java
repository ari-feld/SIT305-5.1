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

    private void loadPlaylist() {

        new Thread(() -> {

            List<PlaylistEntity> list =
                    db.playlistDao().getUserPlaylist(userId);

            runOnUiThread(() -> {

                PlaylistAdapter adapter = new PlaylistAdapter(list, item -> {

                    // Click video → go back to Home and play it
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("videoUrl", item.videoUrl);

                    startActivity(intent);
                    finish();
                });

                recyclerView.setAdapter(adapter);
            });

        }).start();
    }
}