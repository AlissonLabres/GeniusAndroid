package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RankingDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private String[] allColumns = {
        DatabaseHelper.COL_ID, DatabaseHelper.COL_NOME, DatabaseHelper.COL_PONTO
    };

    public RankingDataSource(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException{
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
    }

    private RankingModel rankingApartirDeCursor(Cursor cursor){
        RankingModel rankingModel = new RankingModel();
        rankingModel.setId(cursor.getLong(0));
        rankingModel.setNome(cursor.getString(1));
        rankingModel.setPonto(cursor.getString(2));
        return rankingModel;
    }

    public RankingModel criarRanking(String nome, String pontos){
        ContentValues valores = new ContentValues();

        valores.put(DatabaseHelper.COL_NOME, nome);
        valores.put(DatabaseHelper.COL_PONTO, pontos);

        long insertId = database.insert(
            DatabaseHelper.TABELA_RANKING, null, valores
        );

        Cursor cursor = database.query(
            DatabaseHelper.TABELA_RANKING,
            allColumns,
            DatabaseHelper.COL_ID + " = " + insertId,
            null,
            null,
            null,
            null
        );

        cursor.moveToFirst();
        RankingModel novoRankingModel = rankingApartirDeCursor(cursor);

        cursor.close();
        return novoRankingModel;
    }

    public void deletarRankingPorId(RankingModel rankingModel) {
        long id = rankingModel.getId();

        database.delete(
            DatabaseHelper.TABELA_RANKING,
            DatabaseHelper.COL_ID + " = " + id,
            null
        );
    }

    public void deletarEstudantePorNomeEPontos(String nome, String pontos) {
        database.execSQL(
            "DELETE FROM " + DatabaseHelper.TABELA_RANKING + " WHERE nome = '" + nome + "' OR matricula = '" + pontos + "'"
        );
    }

    public List<RankingModel> obterTodosRankings() {
        List<RankingModel> rankingModels = new ArrayList<>();
        Cursor cursor = database.query(
            DatabaseHelper.TABELA_RANKING,
            allColumns,
            null,
            null,
            null,
            null,
            null
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            RankingModel rankingModel = rankingApartirDeCursor(cursor);
            rankingModels.add(rankingModel);
            cursor.moveToNext();
        }

        cursor.close();
        return rankingModels;
    }
}
