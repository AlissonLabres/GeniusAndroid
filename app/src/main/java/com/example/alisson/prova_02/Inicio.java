package com.example.alisson.prova_02;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Inicio extends AppCompatActivity {

    Button btnJogar;
    Button btnSobre;
    Button btnRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        btnJogar = (Button) findViewById(R.id.btnJogar);
        btnJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent nextView = new Intent(getApplicationContext(), Genius.class);
            startActivityForResult(nextView, 0);
            }
        });

        btnSobre = (Button) findViewById(R.id.btnSobre);
        btnSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent nextView = new Intent(getApplicationContext(), Sobre.class);
            startActivityForResult(nextView, 0);
            }
        });

        btnRank = (Button) findViewById(R.id.btnRank);
        btnRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent nextView = new Intent(getApplicationContext(), Ranking.class);
            startActivityForResult(nextView, 0);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
