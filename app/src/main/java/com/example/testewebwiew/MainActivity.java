package com.example.testewebwiew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class MainActivity extends AppCompatActivity {
    private PlayerUiController playerUiController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtubePlayerr);
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.setEnableAutomaticInitialization(false);

        View controlsUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.custom_controls);

        YouTubePlayerListener youTubePlayerListener = new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                playerUiController = new PlayerUiController(controlsUi, youTubePlayerView, youTubePlayer);
                youTubePlayer.addListener(playerUiController);
                YouTubePlayerUtils.loadOrCueVideo(youTubePlayer, getLifecycle(), "N6NJUYTmCYQ", 0F);
            }
        };

        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
        youTubePlayerView.initialize(youTubePlayerListener, options);

        // Configurar o botão "cadastroNewVideo" para abrir a Activity Cadastro_Novo_Video
        Button btnCadastroNewVideo = findViewById(R.id.cadastroNewVideo);
        btnCadastroNewVideo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Cadastro_Novo_Video.class);
            startActivity(intent);
        });
    }

    public void loadVideo(String videoId, float startTime) {
        if (playerUiController != null) {
            playerUiController.changeVideo(videoId, startTime);
        }
    }
}