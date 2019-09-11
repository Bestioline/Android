package com.example.caterinamaugeri.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Ricetta> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nome;
        public TextView descrizione;
        public ImageView img;
        public CardView card;
        public String id,nom, ricetta, info,descr,foto;
        public boolean espandi=false;

        public ViewHolder(View v)
        {
            super(v);
            nome= (TextView) v.findViewById(R.id.nome_ricetta);
            nome.setClickable(false);
            descrizione=(TextView) v.findViewById(R.id.text_descrizione);
            descrizione.setClickable(false);
            img=(ImageView) v.findViewById(R.id.image_ricetta);
            img.setClickable(false);
            card=(CardView) v.findViewById(R.id.card_ricetta);


            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) descrizione.getLayoutParams();


            Button button=(Button) v.findViewById(R.id.button_espandi);
            button.setBackgroundResource(R.drawable.expand_more);
            button.setOnClickListener((view)->{
                if(espandi==false){
                    params.height = 400;
                    descrizione.setLayoutParams(params);
                    button.setBackgroundResource(R.drawable.expand_less);
                    espandi=true;
                }else{
                    params.height = 100;
                    descrizione.setLayoutParams(params);
                    button.setBackgroundResource(R.drawable.expand_more);
                    espandi=false;
                }
            });


            card.setOnClickListener((view)->{
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                bundle.putString("nome",nom);
                bundle.putString("descr",descr);
                bundle.putString("foto",foto);
                bundle.putString("info",info);
                bundle.putString("ricetta",ricetta);
                FragmentRicetta ricettaFragment = new FragmentRicetta();
                ricettaFragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                ricettaFragment.onAttach(v.getContext());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frag_list, ricettaFragment).addToBackStack("list").commit();
            });
        }

    }



    public MyAdapter(List<Ricetta> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ricetta_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Log.d("myTag", mDataset.toString());
        if(getItemCount()!=0){
            Ricetta tmp=mDataset.get(position);
            holder.id=tmp.getId(); holder.descr=tmp.getDescrizione();
            holder.nom=tmp.getNome(); holder.foto=tmp.getFoto();
            holder.info=tmp.getIngredienti(); holder.ricetta=tmp.getRicetta();
            holder.nome.setText(tmp.getNome());
            holder.descrizione.setText(tmp.getDescrizione());
            String immagine= tmp.getFoto();
       //METODO CREATO IN UTILITY
        byte[] immag = Base64.decode(immagine, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(immag, 0, immag.length);
        holder.img.setImageBitmap(bitmap);}

    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
