package com.example.alisson.prova_02;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
                tvPontuacao.setText("\n\n\nSua pontução foi de " + pontos.toString() + " pontos\n\n\n");
            } else { abrirAlertaDeErro(); }

            if(obterTexto != null ) { tvTexto.setText(obterTexto); }
            else { abrirAlertaDeErro(); }
        } else {
            abrirAlertaDeErro();
        }
    }

    private AlertDialog abrirAlertaDeErro() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(getApplicationContext());
        return alerta
            .setTitle("Erro ao carregar a página")
            .setMessage("erro ao iniciar sua tela. \n\n\nVocê será redirecionado para o Inicio")
            .setNeutralButton("OK", retornarAoInicioQuandoErroDeDados())
            .show();
    }

    private DialogInterface.OnClickListener retornarAoInicioQuandoErroDeDados() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent nextView = new Intent(getApplicationContext(), Inicio.class);
                startActivity(nextView);
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(rankingDataSource != null) { rankingDataSource.close(); }
    }
}
