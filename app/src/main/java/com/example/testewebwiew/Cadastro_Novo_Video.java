package com.example.testewebwiew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Cadastro_Novo_Video extends AppCompatActivity {

    private EditText editTextUrl, editTextTitulo;
    private Button buttonSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_novo_video);

        // Inicializa os componentes
        editTextUrl = findViewById(R.id.url);
        editTextTitulo = findViewById(R.id.descricao);
        buttonSalvar = findViewById(R.id.button);

        // Configura o botão para salvar e retornar os dados
        buttonSalvar.setOnClickListener(v -> salvarVideo());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void salvarVideo() {
        String url = editTextUrl.getText().toString().trim();
        String titulo = editTextTitulo.getText().toString().trim();

        if (url.isEmpty()) {
            editTextUrl.setError("URL é obrigatória");
            return;
        }

        // Extrai o ID do vídeo da URL (se for uma URL completa)
        String videoId = extrairIdDoVideo(url);

        // Retorna os dados para a MainActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("VIDEO_ID", videoId);
        resultIntent.putExtra("TITULO", titulo);
        setResult(RESULT_OK, resultIntent);
        finish(); // Fecha a activity e volta para a MainActivity
    }

    private String extrairIdDoVideo(String url) {
        // Se já for um ID (sem caracteres especiais), retorna direto
        if (url.matches("[a-zA-Z0-9_-]{11}")) {
            return url;
        }

        // Extrai o ID de URLs como:
        // "https://youtu.be/dQw4w9WgXcQ" ou "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
        String padrao = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\\?videoId=)([^#\\&\\?\\n]+)";
        java.util.regex.Pattern compiledPattern = java.util.regex.Pattern.compile(padrao);
        java.util.regex.Matcher matcher = compiledPattern.matcher(url);
        if (matcher.find()) {
            return matcher.group();
        }
        return url; // Fallback: assume que é um ID se não encontrar padrão
    }
}