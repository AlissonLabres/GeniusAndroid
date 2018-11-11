package com.example.alisson.prova_02;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Sobre extends AppCompatActivity {

    protected Button btnRanking;
    protected Button btnAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        adicionarTollbar();
    }

    private void adicionarTollbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setOnClickListener(mudarPagina(Genius.class));

        btnAluno = (Button) findViewById(R.id.btnAluno);
        btnRanking = (Button) findViewById(R.id.btnRanking);

        btnAluno.setOnClickListener(mudarPagina(Sobre.class));
        btnRanking.setOnClickListener(mudarPagina(Ranking.class));
    }

    private View.OnClickListener mudarPagina(final Class novaClass) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Clicado", Toast.LENGTH_SHORT);
                Intent nextView = new Intent(getApplicationContext(), novaClass);
                startActivity(nextView);
            }
        };
    }
}
