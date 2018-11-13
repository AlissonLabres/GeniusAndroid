package com.example.alisson.prova_02;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Classes.ButtonColor;
import Classes.CountTimer;

public class Genius extends AppCompatActivity {

    protected Button btnPrimary;
    protected Button btnSegundo;
    protected Button btnTerceiro;
    protected Button btnQuarto;

    protected Button btnRanking;
    protected Button btnAluno;

    protected Button btnStart;
    protected TextView tvVida;
    protected TextView tvFase;

    private List<Integer> listFase;
    private List<Integer> listButtonClicado;
    private List<ButtonColor> listButtonColor;
    private Integer qntdCliquesPorFase;

    protected CountTimer countTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genius);

        adicionarTollbar();
        iniciarInstancias();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStart.setVisibility(View.INVISIBLE);
                iniciarFase(obterFase());
                iniciarContador();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() { ativarCliques(); }
                }, 5*1001);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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

    private void iniciarInstancias() {
        btnStart = (Button) findViewById(R.id.btnStart);
        btnPrimary = (Button) findViewById(R.id.btnPrimeiro);
        btnSegundo = (Button) findViewById(R.id.btnSegundo);
        btnTerceiro = (Button) findViewById(R.id.btnTerceiro);
        btnQuarto = (Button) findViewById(R.id.btnQuarto);
        tvVida = (TextView) findViewById(R.id.tvVida);
        tvFase = (TextView) findViewById(R.id.tvFase);

        listButtonClicado = new ArrayList<>();
        listButtonColor = Arrays.asList(
            new ButtonColor(btnPrimary, Color.GREEN),
            new ButtonColor(btnSegundo, Color.RED),
            new ButtonColor(btnTerceiro, Color.YELLOW),
            new ButtonColor(btnQuarto, Color.BLUE)
        );
    }

    private void iniciarFase(Integer fase) {
        zerarContadoresEDesabilitarButtons();

//        List<Integer> listTeste = Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1);
//            case 1: listFase = listTeste;
//            case 2: listFase = listTeste;
//            case 3: listFase = listTeste;
//            case 4: listFase = listTeste;
//            case 5: listFase = listTeste;

        switch (fase) {
            case 1: listFase = Arrays.asList(1, 2, 3, 4, 1, 2, 3, 4); break;
            case 2: listFase = Arrays.asList(1, 3, 1, 3, 1, 3, 1, 3); break;
            case 3: listFase = Arrays.asList(4, 1, 1, 2, 3, 3, 3, 1); break;
            case 4: listFase = Arrays.asList(1, 2, 3, 4, 1, 2, 3, 4); break;
            case 5: listFase = Arrays.asList(4, 3, 2, 1, 1, 2, 3, 4); break;
        }
    }

    private void iniciarContador() {
        desativarCliques();
        countTimer = new CountTimer(
                5*1000,
                600,
                listButtonColor,
                listFase,
                getApplicationContext()
        );
        countTimer.start();
    }

    private void desativarCliques() {
        btnPrimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });

        btnSegundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });

        btnTerceiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });

        btnQuarto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });
    }

    private void ativarCliques() {
        btnPrimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarNovoClique(1);
                verificarNovoItemDaFase();
            }
        });

        btnSegundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarNovoClique(2);
                verificarNovoItemDaFase();
            }
        });

        btnTerceiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarNovoClique(3);
                verificarNovoItemDaFase();
            }
        });

        btnQuarto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarNovoClique(4);
                verificarNovoItemDaFase();
            }
        });
    }

    private void adicionarNovoClique(Integer addButtonClicado) {
        listButtonClicado.add(addButtonClicado);
        alterarCorButton(addButtonClicado - 1);
        qntdCliquesPorFase++;
    }

    private void alterarCorButton(Integer posicaoNaLista) {
        ButtonColor buttonColor = listButtonColor.get(posicaoNaLista);
        buttonColor.getButton().setBackgroundColor(buttonColor.getColor());

        alterarButtonCorPadrao(posicaoNaLista);
    }

    private void alterarButtonCorPadrao(final Integer posicaoNaLista) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ButtonColor buttonColor =  listButtonColor.get(posicaoNaLista);
                buttonColor.getButton().setBackgroundColor(Color.parseColor("#BBBBBB"));
            }
        }, 300);
    }

    private void faseConcluida() {
        this.desativarCliques();

        if(obterFase() == 5) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(Genius.this);
            alerta
                .setTitle("Fim de Jogo")
                .setMessage("Parabéns, você finalizou o jogo com sucesso :)")
                .setNeutralButton("OK", jogoConcluido())
                .show();
        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(Genius.this);
            alerta
                .setTitle("Fase " + obterFase() + " concluida")
                .setMessage("Você acertou a sequência da fase " + obterFase() + ".\n\n\nClique em OK e START para iniciar a próxima fase.")
                .setNeutralButton("OK", proximaFase())
                .show();
        }
    }

    private DialogInterface.OnClickListener proximaFase() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alterarFase(obterFase() + 1);
                zerarContadoresEDesabilitarButtons();

                btnStart.setVisibility(View.VISIBLE);
            }
        };
    }

    private DialogInterface.OnClickListener jogoConcluido() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                irParaSalvarRanking(
                    "Parabéns, você concluiu o Jogo :)" +
                    "\n\n\nPor favor informe seu nome para adicionarmos ao Ranking sua pontuação",
                    true
                );
            }
        };
    }

    private void verificarNovoItemDaFase() {
        final Integer iterator = listButtonClicado.size() - 1;
        final Integer numeroDaFase = listFase.get(iterator);
        final Integer numeroClicado = listButtonClicado.get(iterator);

        if(numeroClicado != numeroDaFase) { diminuirVidaDoUsuario(); }
        if(qntdCliquesPorFase == 8) { faseConcluida(); }
    }

    private void diminuirVidaDoUsuario() {
        this.desativarCliques();
        alterarVida(obterVida() - 1);

        if(obterVida() > 0) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(Genius.this);
            alerta
                .setTitle("Errou")
                .setMessage("você infelizmente erro a sequência do Jogo. \nClique em START para tentar novamente!")
                .setNeutralButton("OK", vidaPerdida())
                .show();
        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(Genius.this);
            alerta
                .setTitle("Fim do Jogo")
                .setMessage("você infelizmente perdeu suas vidas no jogo.")
                .setNeutralButton("OK", jogoPerdido())
                .show();
        }
    }

    private DialogInterface.OnClickListener vidaPerdida() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                zerarContadoresEDesabilitarButtons();

                btnStart.setVisibility(View.VISIBLE);
            }
        };
    }

    private DialogInterface.OnClickListener jogoPerdido() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                irParaSalvarRanking(
                    "Infelizmente você perdeu o Jogo, mas poderá tentar novamente quando quiser :)" +
                    "\n\n\nPor favor informe seu nome para adicionarmos ao Ranking sua pontuação", false
                );
            }
        };
    }

    private void zerarContadoresEDesabilitarButtons() {
        this.desativarCliques();
        qntdCliquesPorFase = 0;
        listButtonClicado = new ArrayList<>();
    }

    private void irParaSalvarRanking(String textParaApresentacao, boolean jogoConcluido) {
        Intent nextView = new Intent(getApplicationContext(), SalvarRanking.class);

        Integer pontosGanhos = calcularPontosJogo();

        if(jogoConcluido) pontosGanhos += 16;
        nextView.putExtra("Pontos", pontosGanhos.toString());
        nextView.putExtra(
                "Texto",
                textParaApresentacao
        );
        startActivity(nextView);
    }

    private Integer calcularPontosJogo() {
        final Integer VIDA = 8;
        final Integer FASE = 15;

        Integer pontosVida = VIDA * obterVida();
        Integer pontosFase = FASE * (obterFase() - 1);

        return pontosVida + pontosFase;
    }

    private Integer obterVida() {
        return Integer.parseInt(tvVida.getText().toString());
    }

    private void alterarVida(Integer novaVida) {
        tvVida.setText(novaVida.toString());
    }

    private Integer obterFase() {
        return Integer.parseInt(tvFase.getText().toString());
    }

    private void alterarFase(Integer novaFase) {
        tvFase.setText(novaFase.toString());
    }
}
