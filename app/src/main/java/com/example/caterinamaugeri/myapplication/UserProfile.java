package com.example.caterinamaugeri.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import DB.TabUtenti;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {


    private String email, nome, nick, bio, pass, tel;
    private byte[] imageProf;

    private final int SELECT_PICTURE = 100;
    private CircleImageView img;
    private Uri imageUri;
    private String imagePath;
    private int rotazione;
    private  boolean imagine_mod=false;

    private boolean modifica_abilitata=false;
    private TabUtenti db;
    private Session session;

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        /*
        -----Prelevo dati dal database locale-----
         */
        db = new TabUtenti(this);

        session= new Session(this);

        String passedArg = getIntent().getExtras().getString("arg");
        email = passedArg;

        db.open();
        Cursor c = db.ottieniUtente("'" + email + "'");
        pass=c.getString(0);
        nome = c.getString(1);
        if(nome==null){
            nome=email.substring(0,email.indexOf("@"));
        }
        nick = c.getString(2);
        if(nick==null){
            nick=email.substring(0,email.indexOf("@"));
        }
        tel = c.getString(3);
        bio = c.getString(4);
        if(c.getString(5)!=null) imageProf=UtilityImage.getProfilo(c.getString(5));

        db.close();


        /*
        -----SETTO I DATI----
         */

        EditText nomeUtente=(EditText)findViewById(R.id.nomeCompleto);
        nomeUtente.setText(nome);

        EditText password=(EditText) findViewById(R.id.password);
        password.setText(pass);

        EditText telefono=(EditText) findViewById(R.id.telefono);
        if(tel!=null) telefono.setText(tel);

        EditText mail=(EditText) findViewById(R.id.mail);
        mail.setText(email);

        EditText biografia=(EditText) findViewById(R.id.bio);
        if(bio!=null) biografia.setText(bio);

        TextView nickname= (TextView) findViewById(R.id.nick);
        nickname.setText(nick);

        img= (CircleImageView) findViewById(R.id.imgProfilo);
        if(imageProf !=null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageProf , 0, imageProf.length);
            img.setImageBitmap(bitmap);
        }

        EditText vecchia_password=(EditText) findViewById(R.id.vecchia_password);
        EditText nuova_password=(EditText) findViewById(R.id.nuova_password);

        nomeUtente.setEnabled(false);
        biografia.setEnabled(false);
        telefono.setEnabled(false);
        mail.setEnabled(false);
        password.setEnabled(false);

       // vecchia_password.setVisibility(View.GONE);
        //nuova_password.setVisibility(View.GONE);
         /*

         -----ABILITIAMO LA MODIFICA DELLE FOTO----
         */


        FloatingActionButton modificaFoto=(FloatingActionButton) findViewById(R.id.modificaFoto);

        modificaFoto.setOnClickListener((view) -> {
            ActivityCompat.requestPermissions(UserProfile.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, SELECT_PICTURE);
        });

        /*
        -----AZIONO I BUTTON DI MODIFICA-----
         */
        FloatingActionButton modificaProfilo= (FloatingActionButton) findViewById(R.id.modificaProfilo);

        modificaProfilo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            public void onClick(View v) {


                if(modifica_abilitata==false) {

                    modificaFoto.setVisibility(View.GONE);
                    vecchia_password.setVisibility(View.VISIBLE);
                    nuova_password.setVisibility(View.VISIBLE);

                    modificaProfilo.setImageResource(R.drawable.modifica_abilitata);

                    nomeUtente.setEnabled(true);
                    biografia.setEnabled(true);
                    telefono.setEnabled(true);

                    nome = nomeUtente.getText().toString();
                    bio = biografia.getText().toString();
                    tel = telefono.getText().toString();

                    modifica_abilitata=true;
                }else {

                    modificaProfilo.setImageResource(R.drawable.modifica);


                    //DA MODIFICARE GRAFICA CAMBIO PASSWORD
                    if(!vecchia_password.getText().toString().equals("Inserisci qui la tua vecchia password!")|| vecchia_password.getText()!=null){
                        if(vecchia_password.getText().toString().equals(pass) && (!nuova_password.getText().toString().equals("Inserisci qui la tua nuova password!") ||nuova_password.getText()!=null )){
                            pass=nuova_password.getText().toString();
                            password.setText(pass);
                        } else{
                            CharSequence text = "Modifica password non avvenuta!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast.makeText(getApplicationContext(),text, duration).show();
                        }
                    }
                    nuova_password.setText("");
                    vecchia_password.setText("");

                    nick = nomeUtente.getText().toString();
                    nickname.setText(nick);
                    nomeUtente.setEnabled(false);
                    biografia.setEnabled(false);
                    telefono.setEnabled(false);
                    modificaFoto.setVisibility(View.VISIBLE);
                    nuova_password.setVisibility(View.GONE);
                    vecchia_password.setVisibility(View.GONE);

                    String imageString=null;
                    if(imageProf!=null) imageString = Base64.encodeToString(imageProf,Base64.NO_WRAP);

                    db.open();
                    db.aggiornaUtente("'" + email + "'",password.getText().toString(), nomeUtente.getText().toString(), telefono.getText().toString() , nomeUtente.getText().toString(), biografia.getText().toString(),imageString);
                    db.close();

                    modifica_abilitata=false;
                }
        }});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.impostazione_menu,menu);
        return true;
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

       switch(item.getItemId()) {
           case R.id.elimina_account:
               CustomDialogClass cdd=new CustomDialogClass(this,false,session);
               cdd.show();
               break;
           case R.id.esci:
               CustomDialogClass cdd2=new CustomDialogClass(this,true,session);
               cdd2.show();
               break;
           default:
               return super.onOptionsItemSelected(item);
       }
       return true;

   }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_PICTURE) {
            if (resultCode == RESULT_OK) {
                imageUri = data.getData();
                img.setImageURI(imageUri);

                //operazioni volte a girare l'immagine nel caso in cui abbia una orientazione

                Uri targetUri = data.getData();
                if (data.toString().contains("content:")) {
                    imagePath = getRealPathFromURI(targetUri);
                } else if (data.toString().contains("file:")) {
                    imagePath = targetUri.getPath();
                } else {
                    imagePath = null;
                }

                try {
                    //operazioni per prendere la rotaione dell'immagine
                    ExifInterface exifInterface = new ExifInterface(imagePath);
                    int rotation = Integer.parseInt(exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION));
                    int rotationInDegrees = exifToDegrees(rotation);
                    img.setRotation(rotationInDegrees);

                    //salvo l'orientazione in modo che prima di salvare l'immagine nel db la giro
                    rotazione =rotationInDegrees;
                    Bitmap bi =((BitmapDrawable)img.getDrawable()).getBitmap();
                    String imageString= UtilityImage.BitmapToString(bi,rotazione,img);
                    db.open();
                    db.aggiornaUtente("'" + email + "'", pass, nome, tel, nick,bio ,imageString);
                    db.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }
    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null,
                    null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }



}

