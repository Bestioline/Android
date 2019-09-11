package DB;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;


import java.io.ByteArrayOutputStream;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDB.db";
    static final int DATABASE_VERSIONE = 6;


    static final String TAB_UTENTI =
            "CREATE TABLE utenti (email text primary key not null, password text not null, nome text ," +
                    "nick text, telefono text, bio text, profilo blob );";

    static final String TAB_RICETTE =
            "CREATE TABLE ricette (id integer primary key autoincrement not null, nome text not null, categoria text not null ," +
                    "descrizione text not null, foto text, ingredienti text not null, ricetta text not null );";

    static  final String TAB_UTENTERICETTA= "CREATE TABLE utenteRicetta (id integer primary key autoincrement not null," +
            " mail text not null, ricetta id not null);";

    DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSIONE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(TAB_UTENTI);
            db.execSQL(TAB_RICETTE);
            db.execSQL(TAB_UTENTERICETTA);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(), "Aggiornamento database dalla versione " + oldVersion + " alla "
                + newVersion + ". I dati esistenti verranno eliminati.");
        db.execSQL("DROP TABLE IF EXISTS utenti");
        db.execSQL("DROP TABLE IF EXISTS ricette");
        db.execSQL("DROP TABLE IF EXISTS utenteRicetta");
        onCreate(db);
    }



}
