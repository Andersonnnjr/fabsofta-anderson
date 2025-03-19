package br.univille;

import br.univille.entity.Cidade;
import br.univille.entity.Pessoa;
import br.univille.entity.Pokemon;

public class App {
    public static void main(String[] args) throws Exception {
       //System.out.println("Hello, World!");
       //Instância
        Cidade jlle = new Cidade();
        jlle.setNome("Joinville");
        jlle.setEstado("Santa Catarina");

        Pokemon jigglypuff = new Pokemon("jigglyPuff");
        Pokemon porygon = new Pokemon("porygon");

        Pessoa mariazinha = new Pessoa("Mariazinha");
        Pessoa zezinho = new Pessoa();
        zezinho.setNome("Zezinho");
        zezinho.setCidade(jlle);
        zezinho.getListaPokemon().add(jigglypuff);
        zezinho.getListaPokemon().add(porygon);

        for(var umPokemon : zezinho.getListaPokemon()){
            System.out.println(umPokemon);
        }

        System.out.println(mariazinha);
        System.out.println(zezinho);

    }
}
