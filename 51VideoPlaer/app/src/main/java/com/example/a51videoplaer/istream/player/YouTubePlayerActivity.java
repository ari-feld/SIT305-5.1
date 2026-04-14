package com.example.a51videoplaer.istream.player;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a51videoplaer.R;

public class YouTubePlayerActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);

        webView = findViewById(R.id.webView);

        // Get videoId from intent
        String videoId = getIntent().getStringExtra("videoId");

        if (videoId == null || videoId.isEmpty()) {
            finish();
            return;
        }

        // WebView setup
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        String html =
                "<html>" +
                        "<body style='margin:0;padding:0;'>" +
                        "<iframe width='100%' height='100%' " +
                        "src='https://www.youtube.com/embed/" + videoId + "?autoplay=1' " +
                        "frameborder='0' allowfullscreen>" +
                        "</iframe>" +
                        "</body>" +
                        "</html>";

        webView.loadDataWithBaseURL(
                "https://www.youtube.com",
                html,
                "text/html",
                "utf-8",
                null
        );
    }
}