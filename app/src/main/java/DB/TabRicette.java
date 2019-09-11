package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TabRicette {
    public static final String DATABASE_NAME = "MyDB.db";
    public static final String RICETTA_TABLE_NAME = "ricette";
    public static final String RICETTA_COLUMN_ID = "id";
    public static final String RICETTA_COLUMN_NOME = "nome";
    public static final String RICETTA_COLUMN_CATEGORIA = "categoria";
    public static final String RICETTA_COLUMN_DESCRIZIONE = "descrizione";
    public static final String RICETTA_COLUMN_FOTO = "foto";
    public static final String RICETTA_COLUMN_INGREDIENTI = "ingredienti";
    public static final String RICETTA_COLUMN_RICETTA = "ricetta";

    static final int DATABASE_VERSIONE = 5;

    static final String DATABASE_CREAZIONE =
            "CREATE TABLE ricette (id integer primary key autoincrement not null, nome text not null, categoria text not null ," +
                    "descrizione text not null, foto text, ingredienti text not null, ricetta text not null );";
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public TabRicette(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public TabRicette open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long inserisciRicetta(String nome, String categoria, String descrizione, String foto, String ingredienti, String ricetta) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(RICETTA_COLUMN_NOME, nome);
        initialValues.put(RICETTA_COLUMN_CATEGORIA, categoria);
        initialValues.put(RICETTA_COLUMN_DESCRIZIONE, descrizione);
        initialValues.put(RICETTA_COLUMN_FOTO, foto);
        initialValues.put(RICETTA_COLUMN_INGREDIENTI, ingredienti);
        initialValues.put(RICETTA_COLUMN_RICETTA, ricetta);
        return db.insert(RICETTA_TABLE_NAME, null, initialValues);
    }

    public Cursor ottieniTutteRicette() {
        return db.query(
                RICETTA_TABLE_NAME, new String[]{RICETTA_COLUMN_ID, RICETTA_COLUMN_NOME, RICETTA_COLUMN_CATEGORIA, RICETTA_COLUMN_DESCRIZIONE,
                        RICETTA_COLUMN_FOTO, RICETTA_COLUMN_INGREDIENTI, RICETTA_COLUMN_RICETTA},
                null, null, null, null, null);
    }

    public Cursor ottieniRicetteCategoria(String categoria) throws SQLException {

        Cursor mCursore = db.query(true, RICETTA_TABLE_NAME, new String[]
                        {RICETTA_COLUMN_ID, RICETTA_COLUMN_NOME, RICETTA_COLUMN_CATEGORIA, RICETTA_COLUMN_DESCRIZIONE,
                                RICETTA_COLUMN_FOTO, RICETTA_COLUMN_INGREDIENTI, RICETTA_COLUMN_RICETTA},
                RICETTA_COLUMN_CATEGORIA + "=" + "'"+categoria+"'", null, null, null, null, null);
        if (mCursore != null) {
            mCursore.moveToFirst();
        }
        return mCursore;
    }

    public Cursor ottieniRicetteId(int id) throws SQLException {

        Cursor mCursore = db.query(true, RICETTA_TABLE_NAME, new String[]
                        {RICETTA_COLUMN_ID, RICETTA_COLUMN_NOME, RICETTA_COLUMN_CATEGORIA, RICETTA_COLUMN_DESCRIZIONE,
                                RICETTA_COLUMN_FOTO, RICETTA_COLUMN_INGREDIENTI, RICETTA_COLUMN_RICETTA},
                RICETTA_COLUMN_ID + "=" + id, null, null, null, null, null);
        if (mCursore != null) {
            mCursore.moveToFirst();
        }
        return mCursore;
    }


    public Cursor ottieniRicetteNome(String nome) throws SQLException {

        Cursor mCursore = db.query(true, RICETTA_TABLE_NAME, new String[]
                        {RICETTA_COLUMN_ID, RICETTA_COLUMN_NOME, RICETTA_COLUMN_CATEGORIA, RICETTA_COLUMN_DESCRIZIONE,
                                RICETTA_COLUMN_FOTO, RICETTA_COLUMN_INGREDIENTI, RICETTA_COLUMN_RICETTA},
                RICETTA_COLUMN_NOME + "=" + nome, null, null, null, null, null);
        if (mCursore != null) {
            mCursore.moveToFirst();
        }
        return mCursore;
    }
}
