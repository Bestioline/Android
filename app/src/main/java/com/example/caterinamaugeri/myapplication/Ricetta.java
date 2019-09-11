package com.example.caterinamaugeri.myapplication;

public class Ricetta {

    private String id, nome, categoria, descrizione, foto, ingredienti, ricetta;
    private  String o;

    public Ricetta(String id, String nome, String categoria, String descrizione, String foto, String ingredienti, String ricetta){
        this.id=id;
        this.nome=nome;
        this.categoria=categoria;
        this.descrizione=descrizione;
        this.foto=foto;
        this.ingredienti=ingredienti;
        this.ricetta=ricetta;
    }

    public String getId(){return id;}

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getCategoria(){return categoria;}

    public String getFoto() {
        return foto;
    }

    public String getRicetta() {
        return ricetta;
    }

    public String getIngredienti() {
        return ingredienti;
    }
}
