package br.univille.entity;

import java.util.ArrayList;

public class Pessoa {
    //variavel, atributo
    private long id;
    private String nome; 
    private String endereco;

    private ArrayList<Pokemon> listaPokemon = new ArrayList<Pokemon>();


    public ArrayList<Pokemon> getListaPokemon() {
        return listaPokemon;
    }


    public void setListaPokemon(ArrayList<Pokemon> listaPokemon) {
        this.listaPokemon = listaPokemon;
    }
    
    
    
    private Cidade cidade;
    
    

    public Cidade getCidade() {
        return cidade;
    }


    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getEndereco() {
        return endereco;
    }


    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }


    //Construtor (mesmo nome da classe, não tem)
    public Pessoa(String nome){
        //this referencia a classe
         this.nome = nome;
    }


    //Dois metodos com assinatura semelhante = polimorfismo
    public Pessoa(){

    }


    // metodo
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    
    //Sobreescrita de metodo(overwrite)
    @Override
    public String toString(){

        return getNome();
    }


   

}
