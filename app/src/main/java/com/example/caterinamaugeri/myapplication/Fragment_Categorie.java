package com.example.caterinamaugeri.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Categorie extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_categorie, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        ((TextView) view.findViewById(R.id.text_tutti_dolci)).setOnClickListener(null);
        ((TextView) view.findViewById(R.id.text_tutti_dolci)).setFocusable(false);
        CardView card_all= (CardView) view.findViewById(R.id.card_tutti);
        card_all.setClickable(true);
        card_all.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                choose("tutti");
            }}));



        ((TextView) view.findViewById(R.id.text_torta)).setOnClickListener(null);
        ((TextView) view.findViewById(R.id.text_torta)).setFocusable(false);
        CardView card_torte= (CardView) view.findViewById(R.id.card_torte);
        card_torte.setClickable(true);
        card_torte.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                choose("torte");
            }}));


        ((TextView) view.findViewById(R.id.text_ciamb)).setOnClickListener(null);
        ((TextView) view.findViewById(R.id.text_ciamb)).setFocusable(false);
        CardView card_ciamb= (CardView) view.findViewById(R.id.card_ciamb);
        card_ciamb.setClickable(true);
        card_ciamb.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                choose("ciambelloni");
            }}));

        ((TextView) view.findViewById(R.id.text_crost)).setOnClickListener(null);
        ((TextView) view.findViewById(R.id.text_crost)).setFocusable(false);
        CardView card_crostate= (CardView) view.findViewById(R.id.card_crostata);
        card_crostate.setClickable(true);
        card_crostate.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                choose("crostate");
            }}));



        ((TextView) view.findViewById(R.id.text_chees)).setOnClickListener(null);
        ((TextView) view.findViewById(R.id.text_chees)).setFocusable(false);
        CardView card_chess= (CardView) view.findViewById(R.id.card);
        card_chess.setClickable(true);
        card_chess.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                choose("cheesecake");

            }}));

        ((TextView) view.findViewById(R.id.text_mousse)).setOnClickListener(null);
        ((TextView) view.findViewById(R.id.text_mousse)).setFocusable(false);
        CardView card_mousse= (CardView) view.findViewById(R.id.card_mousse);
        card_mousse.setClickable(true);
        card_mousse.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                choose("mousse");
            }}));





        ((TextView) view.findViewById(R.id.text_bisc)).setOnClickListener(null);
        ((TextView) view.findViewById(R.id.text_bisc)).setFocusable(false);
        CardView card_bisc= (CardView) view.findViewById(R.id.card_bisc);
        card_bisc.setClickable(true);
        card_bisc.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                choose("biscotti");
            }}));
    }




   public void choose(String s){
       Fragment_List_Ricette  fragment=new Fragment_List_Ricette();
       fragment.onAttach(getContext());
       fragment.doSomething(s);
       FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
       fragmentTransaction.replace(R.id.frag_list,fragment);
       fragmentTransaction.addToBackStack(null);
       fragmentTransaction.commit();
    }//METODO CHE VA INSERITO PER RICHIAMARE IL FRAGMENT

}
