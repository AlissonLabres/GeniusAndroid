package com.example.alisson.prova_02;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                Intent nextView = new Intent(getApplicationContext(), novaClass);
                startActivity(nextView);
            }
        };
    }

    private void criarListView() {
        obterListaDeRanking();
        lvRanking.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                long idUsuario = Long.parseLong(lvRanking.getAdapter().getItem(position).toString().substring(16, 17));

                AlertDialog.Builder alerta = new AlertDialog.Builder(parent.getContext());
                alerta
                    .setTitle("Excluir usuário")
                    .setMessage("Você tem certeza que deseja excluir o usuário?")
                    .setNeutralButton("Sim", apagarItem(idUsuario))
                    .setNegativeButton("Não", cancelarExclusao())
                    .show();

                return true;
            }
        });
    }

    private void obterListaDeRanking() {
        List<String> arrayAdapterRanking = new ArrayList<>();
        for (RankingModel ranking : rankingDataSource.obterTodosRankings()) {
            arrayAdapterRanking.add(
                "ID do Usuário - " + ranking.getId() + "\n\nNome - " + ranking.getNome().toString() + "\n\nPonto - " + ranking.getPonto().toString() + "\n"
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

    private Dialog.OnClickListener cancelarExclusao() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        };
    }

    private Dialog.OnClickListener apagarItem(final long idItem) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RankingModel ranking = rankingDataSource.obterRankingPorId(idItem);
                rankingDataSource.deletarRankingPorId(ranking);

                obterListaDeRanking();
            }
        };
    }
}
