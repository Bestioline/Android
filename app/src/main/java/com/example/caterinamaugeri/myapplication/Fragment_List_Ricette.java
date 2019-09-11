package com.example.caterinamaugeri.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import DB.TabRicette;
import DB.UtenteRicetta;

public class Fragment_List_Ricette extends Fragment{
    private MyAdapter tutorAdapter;
    private RecyclerView recyclerView;
    private View myView;

    private ArrayList<Ricetta> ricettaList=new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tutorAdapter = new MyAdapter(ricettaList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_ricetta_list,container, false);
        recyclerView = myView.findViewById(R.id.list_ricette);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tutorAdapter);
        return myView;
    }

    public void doSomething(String parms){
        TabRicette tabRicette=new TabRicette(mContext);
        tabRicette.open();
        Cursor cursor=null;

        if(parms.equals("tutti")){
            cursor=tabRicette.ottieniTutteRicette();
            creaList(cursor);
        }
        else if(parms.equals("pref")){
                ArrayList<String> list= creaPreferiti();
                if(list.size()!=0){
                    Log.i("Vuoto?", ""+list.size() );
                    for(String s: list){
                        cursor=tabRicette.ottieniRicetteId(Integer.parseInt(s));
                        String id=cursor.getString(0);
                        String nome=cursor.getString(1);
                        String categoria=cursor.getString(2);
                        String descrizione=cursor.getString(3);
                        String foto=cursor.getString(4);
                        String ingredienti=cursor.getString(5);
                        String ric=cursor.getString(6);
                        Ricetta ricetta=new Ricetta(id,nome,categoria,descrizione,foto,ingredienti,ric);
                        ricettaList.add(ricetta);
                    }
                }

               //BISOGNA RICEVERE UNA RICETTA TRAMITE ID
            }
            else {cursor=tabRicette.ottieniRicetteCategoria(parms); creaList(cursor);}
            tabRicette.close();

    }

    private ArrayList<String> creaPreferiti() {
        //SOSTITUIAMO UN FRAGMENT CON LE RICETTE PREFERITE DELL'UTENTE
        String utente= new Session(mContext).getusename();
        ArrayList<String> list = new ArrayList<>();

        if(utente.equals(null)==false) {
            UtenteRicetta ur = new UtenteRicetta(mContext);
            ur.open();
            Cursor c = ur.ottieniTutteRicette(utente);
            while (c.moveToNext()) {
                list.add(c.getString(2));
            }
            ur.close();
        }
        return list;
    }


    public void creaList(Cursor cursor){
        while(cursor.moveToNext()){
            String id=cursor.getString(0);
            String nome=cursor.getString(1);
            String categoria=cursor.getString(2);
            String descrizione=cursor.getString(3);
            String foto=cursor.getString(4);
            String ingredienti=cursor.getString(5);
            String ric=cursor.getString(6);
            Ricetta ricetta=new Ricetta(id,nome,categoria,descrizione,foto,ingredienti,ric);
            ricettaList.add(ricetta);
    }}

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

}
