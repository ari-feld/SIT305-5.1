package com.example.a51videoplaer.istream.home;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a51videoplaer.R;
import com.example.a51videoplaer.istream.database.AppDatabase;
import com.example.a51videoplaer.istream.database.PlaylistEntity;
import com.example.a51videoplaer.istream.auth.LoginActivity;
import com.example.a51videoplaer.istream.player.YouTubePlayerActivity;
import com.example.a51videoplaer.istream.playlist.PlaylistActivity;

public class HomeActivity extends AppCompatActivity {

    EditText urlInput;
    Button playBtn, addBtn, playlistBtn, logoutBtn;

    AppDatabase db;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        urlInput = findViewById(R.id.urlInput);
        playBtn = findViewById(R.id.btnPlay);
        addBtn = findViewById(R.id.btnAdd);
        playlistBtn = findViewById(R.id.btnPlaylist);
        logoutBtn = findViewById(R.id.btnLogout);

        db = AppDatabase.getInstance(this);

        userId = getIntent().getIntExtra("userId", -1);


        // PLAY VIDEO
        playBtn.setOnClickListener(v -> {

            String url = urlInput.getText().toString();
            String videoId = extractVideoId(url);

            if (videoId == null) {
                Toast.makeText(this, "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, YouTubePlayerActivity.class);
            intent.putExtra("videoId", videoId);
            startActivity(intent);
        });

        // ADD TO PLAYLIST
        addBtn.setOnClickListener(v -> {

            String url = urlInput.getText().toString();

            PlaylistEntity item = new PlaylistEntity();
            item.userId = userId;
            item.videoUrl = url;

            new Thread(() -> {
                db.playlistDao().insert(item);

                runOnUiThread(() ->
                        Toast.makeText(this, "Added to playlist", Toast.LENGTH_SHORT).show()
                );
            }).start();
        });

        // GO TO PLAYLIST
        playlistBtn.setOnClickListener(v -> {
            if (userId == -1) {
                Toast.makeText(this, "User session error", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, PlaylistActivity.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });

        // LOGOUT
        logoutBtn.setOnClickListener(v -> {

            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private String extractVideoId(String url) {
        try {
            if (url.contains("v=")) {
                return url.split("v=")[1].split("&")[0];
            }
            if (url.contains("youtu.be/")) {
                return url.substring(url.lastIndexOf("/") + 1);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}