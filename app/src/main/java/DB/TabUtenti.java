package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TabUtenti {
    public static final String DATABASE_NAME = "MyDB.db";
    public static final String UTENTI_TABLE_NAME = "utenti";
    public static final String UTENTI_COLUMN_ID = "email";
    public static final String UTENTI_COLUMN_NAME = "nome";
    public static final String UTENTI_COLUMN_PASSWORD = "password";
    public static final String UTENTI_COLUMN_NICK = "nick";
    public static final String UTENTI_COLUMN_TELEFONO = "telefono";
    public static final String UTENTI_COLUMN_BIO = "bio";
    public static final String UTENTI_COLUMN_IMG_PR = "profilo";


    static final int DATABASE_VERSIONE = 5;

    static final String DATABASE_CREAZIONE =
            "CREATE TABLE utenti (email text primary key not null, password text not null, nome text ," +
                    "nick text, telefono text, bio text, profilo text );";
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public TabUtenti(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public TabUtenti open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    /*
    metodo che inserisce un nuovo utente nel db con solo mail e password
     */
    public long inserisciUtente(String mail, String password) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(UTENTI_COLUMN_ID, mail);
        initialValues.put(UTENTI_COLUMN_PASSWORD, password);
        return db.insert(UTENTI_TABLE_NAME, null, initialValues);
    }

    /*
    metodo che cancella un utente dal db
     */
    public boolean cancellaUtente(String mail) {
        return db.delete(UTENTI_TABLE_NAME, UTENTI_COLUMN_ID + "=" + mail, null) > 0;
    }

    /*
    metodo che ottiene tutti gli utenti dal db con tutti gli attributi
     */
    public Cursor ottieniTuttiUtenti() {
        return db.query(
                UTENTI_TABLE_NAME, new String[]{UTENTI_COLUMN_ID, UTENTI_COLUMN_PASSWORD, UTENTI_COLUMN_NAME,
                        UTENTI_COLUMN_NICK, UTENTI_COLUMN_TELEFONO, UTENTI_COLUMN_BIO, UTENTI_COLUMN_IMG_PR},
                null, null, null, null, null);
    }


    /*
    metodo che ottiene un utente dal db con tutti gli attributi
    */
    public Cursor ottieniUtente(String mail) throws SQLException {
        Cursor mCursore = db.query(true, UTENTI_TABLE_NAME, new String[]
                        {UTENTI_COLUMN_PASSWORD, UTENTI_COLUMN_NAME, UTENTI_COLUMN_NICK,
                                UTENTI_COLUMN_TELEFONO, UTENTI_COLUMN_BIO, UTENTI_COLUMN_IMG_PR},
                UTENTI_COLUMN_ID + "=" + mail, null, null, null, null, null);
        if (mCursore != null) {
            mCursore.moveToFirst();
        }
        return mCursore;

    }


    /*
    metodo che aggiorna gli attributi del db
     */
    public boolean aggiornaUtente(String mail, String password, String name, String telefono, String nick, String bio, String imgCod) {
        ContentValues args = new ContentValues();
        if (name != null)
            args.put(UTENTI_COLUMN_NAME, name);
        if (telefono != null)
            args.put(UTENTI_COLUMN_TELEFONO, telefono);
        if (nick != null)
            args.put(UTENTI_COLUMN_NICK, nick);
        if (bio != null)
            args.put(UTENTI_COLUMN_BIO, bio);
        if (imgCod != null)
            args.put(UTENTI_COLUMN_IMG_PR, imgCod);
        if(password!=null)
            args.put(UTENTI_COLUMN_PASSWORD,password);

        return db.update(UTENTI_TABLE_NAME, args, UTENTI_COLUMN_ID + "=" + mail, null) > 0;
    }

}