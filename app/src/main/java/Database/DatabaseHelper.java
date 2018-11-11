package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DBNAME = "DBProva";
    static final String TABELA_RANKING = "ranking";
    static final String COL_ID = "id";
    static final String COL_NOME = "nome";
    static final String COL_PONTO = "ponto";
    static final int DBVERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            "CREATE TABLE " + TABELA_RANKING + " (" + COL_ID + " INTEGER PRIMARY KEY , " + COL_NOME + " TEXT, " + COL_PONTO + " TEXT ); "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_RANKING);
    }
}