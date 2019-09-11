package com.example.caterinamaugeri.myapplication;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

import DB.TabRicette;
import DB.TabUtenti;
import DB.UtenteRicetta;
import de.hdodenhof.circleimageview.CircleImageView;
import io.github.yavski.fabspeeddial.FabSpeedDial;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private Session session;
    private TabUtenti tabUtenti = new TabUtenti(this);
    private TextView tx;
    private CircleImageView ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment_Categorie fragment = new Fragment_Categorie();
        fragmentTransaction.add(R.id.frag_list, fragment);
        fragmentTransaction.commit();

        session = new Session(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hview=navigationView.getHeaderView(0);
        tx= (TextView)hview.findViewById(R.id.mailmenu);

        ft=(CircleImageView) hview.findViewById(R.id.imgmenu);
        if(session.getusename()!="") {

            tabUtenti.open();
            Cursor r = tabUtenti.ottieniUtente("'" + session.getusename() + "'");
            if(r.getString(2)!=null)
                tx.setText(r.getString(2));
            else tx.setText(session.getusename().substring(0,session.getusename().indexOf("@")));

            if (r.getString(5) != null) {
                byte[] bytefoto = UtilityImage.getProfilo(r.getString(5));
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytefoto, 0, bytefoto.length);
                ft.setImageBitmap(bitmap);
            }
            tabUtenti.close();
        }

        ft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (session.getusename() != "") {
                    Intent i = new Intent(MainActivity.this, UserProfile.class);
                    i.putExtra("arg", session.getusename());
                    startActivity(i);
                } else {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void activityLog(View view) {
        //per fare il logout automatico
        session.setusename("");

        if (session.getusename() != "") {
            Intent i = new Intent(MainActivity.this, UserProfile.class);
            i.putExtra("arg", session.getusename());
            startActivity(i);
        } else {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if (id == R.id.nav_pref) {

            Fragment_List_Ricette  fragment=new Fragment_List_Ricette();
            fragment.onAttach(this.getApplicationContext());
            fragment.doSomething("pref");
            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frag_list,fragment);
            fragmentTransaction.addToBackStack("pref");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_home) {
           //Fragment_Categorie fragment= new Fragment_Categorie();
           //fragment.onAttach(this.getApplicationContext());
           //FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
           //fragmentTransaction.replace(R.id.frag_list,fragment);
           //fragmentTransaction.commit();

           //RIAGGIORNIAMO
           Intent intent= new Intent(this, MainActivity.class);
           startActivity(intent);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
