package com.example.alisson.prova_02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Database.RankingDataSource;

public class SalvarRanking extends AppCompatActivity {

    protected TextView tvTexto;
    protected TextView tvPontuacao;
    protected EditText edNome;
    protected Button btnSalvarRanking;

    private RankingDataSource rankingDataSource;
    private Integer pontos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salvar_ranking);

        rankingDataSource = new RankingDataSource(getApplicationContext());
        rankingDataSource.open();

        tvTexto = (TextView) findViewById(R.id.tvTexto);
        tvPontuacao = (TextView) findViewById(R.id.tvPontuacao);
        edNome = (EditText) findViewById(R.id.edNome);
        btnSalvarRanking = (Button) findViewById(R.id.btnSalvarRanking);

        obterDadosGenius();

        btnSalvarRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarNovoRanking();
            }
        });
    }

    private  void salvarNovoRanking() {
        rankingDataSource.criarRanking(edNome.getText().toString(), pontos.toString());
        Intent nextView = new Intent(getApplicationContext(), Ranking.class);
        startActivity(nextView);
    }

    private void obterDadosGenius() {
        Intent viewAtual = getIntent();
        if(viewAtual.getExtras() != null) {
            String obterPontos = viewAtual.getStringExtra("Pontos");
            String obterTexto = viewAtual.getStringExtra("Texto");

            if(obterPontos != null) {
                pontos = Integer.parseInt(obterPontos);
                tvPontuacao.setText("Sua pontução foi de " + pontos.toString() + " pontos");
            }
            else { pontos = 0; tvPontuacao.setText("Erro ao obter pontos do Jogo"); }

            if(obterTexto != null ) { tvTexto.setText(obterTexto); }
            else { tvTexto.setText("Erro ao iniciar tela."); }
        } else {
            pontos = 0;

            String MESSAGEERRO = "Erro ao iniciar tela.";
            tvTexto.setText(MESSAGEERRO);
            tvPontuacao.setText(MESSAGEERRO);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(rankingDataSource != null) {
            rankingDataSource.close();
        }
    }
}
