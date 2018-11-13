package com.example.alisson.prova_02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Database.RankingDataSource;
import Database.RankingModel;

public class Ranking extends AppCompatActivity {

    protected ListView lvRanking;
    protected Button btnAluno;

    private RankingDataSource rankingDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        rankingDataSource = new RankingDataSource(getApplicationContext());
        rankingDataSource.open();

        adicionarTollbar();
        criarListView();
    }

    private void adicionarTollbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setOnClickListener(mudarPagina(Genius.class));

        btnAluno = (Button) findViewById(R.id.btnAluno);
        btnAluno.setOnClickListener(mudarPagina(Sobre.class));
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

    private void criarListView() {
        List<String> arrayAdapterRanking = new ArrayList<>();
        for (RankingModel ranking : rankingDataSource.obterTodosRankings()) {
            arrayAdapterRanking.add(
                    "Nome: " + ranking.getNome().toString() + "\nPonto: " + ranking.getPonto().toString()
            );
        }

        lvRanking = (ListView) findViewById(R.id.lvRanking);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_activated_1,
                arrayAdapterRanking
        );

        lvRanking.setAdapter(adapter);
    }
}
