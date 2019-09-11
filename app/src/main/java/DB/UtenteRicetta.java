package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UtenteRicetta {
    public static final String DATABASE_NAME = "MyDB.db";
    public static final String UTENTERICETTA_TABLE_NAME = "utenteRicetta";
    public static final String UTENTERICETTA_COLUMN_ID = "id";
    public static final String UTENTERICETTA_COLUMN_MAIL = "mail";
    public static final String UTENTERICETTA_COLUMN_RICETTA = "ricetta";

    static final int DATABASE_VERSIONE =6;

    static final String DATABASE_CREAZIONE =
            "CREATE TABLE utenteRicetta (id integer primary key autoincrement not null, mail text not null, ricetta text not null );";
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;


    public UtenteRicetta(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public UtenteRicetta open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }


    public long inserisciUtenteRicetta(String mail, String ricetta) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(UTENTERICETTA_COLUMN_MAIL,mail);
        initialValues.put(UTENTERICETTA_COLUMN_RICETTA,ricetta);

        return db.insert(UTENTERICETTA_TABLE_NAME, null, initialValues);
    }

    //dato un utente ottiene tutte le ricette associate a lui
    public Cursor ottieniTutteRicette(String mail) {
        Cursor mCursore=db.query(
                UTENTERICETTA_TABLE_NAME, new String[]{UTENTERICETTA_COLUMN_ID,UTENTERICETTA_COLUMN_MAIL,UTENTERICETTA_COLUMN_RICETTA},
                UTENTERICETTA_COLUMN_MAIL+"="+"'"+mail+"'",null,null,null,null);
        Log.i("Prova3", mCursore.toString());

        if (mCursore != null) {
            mCursore.moveToFirst();
        }
        return mCursore;
    }


    public boolean deleteR(String s, String u)
    {
        return db.delete(UTENTERICETTA_TABLE_NAME, UTENTERICETTA_COLUMN_RICETTA + "=" + "'"+s+"'"
                +" AND "+ UTENTERICETTA_COLUMN_MAIL +"=" +"'"+u+"'", null) > 0;

    }


    public boolean deleteId(int s)
    {
        return db.delete(UTENTERICETTA_TABLE_NAME, UTENTERICETTA_COLUMN_ID + "=" + s, null) > 0;
    }

}

