package com.cursoandroid.splashscreen;

import java.util.ArrayList;
import java.util.List;


public class DadosAppT2 extends MainActivity{

    private List<Tarefas2> listaPassos2;
    private int posicao;
    private int PassosFeitos2 = 0;


    public DadosAppT2() {


        listaPassos2 = new ArrayList<>();
        listaPassos2.add(new Tarefas2("1. Passo --> Compra de terreno"));
        listaPassos2.add(new Tarefas2("2. Passo --> Preparação de terreno"));
        listaPassos2.add(new Tarefas2("3. Passo --> Plantar trigo"));
        listaPassos2.add(new Tarefas2("4. Passo --> Obter colheita"));
        posicao = 1;
    }





    public String getTextoPassoReceita() {

        return listaPassos2.get(posicao - 1).getTexto();
    }

    public void avancar() {
        if (posicao < listaPassos2.size()){
            posicao++;
        }
    }

    public void retroceder() {
        if (posicao > 1)
            posicao--;
    }

    public int getPosicao()  {
        return posicao;
    }

    public int getSizeListaPassos()  {
        return listaPassos2.size();
    }


    public void marcarFeito() {
        listaPassos2.get(posicao - 1).setFeito(true);
    }

    public int getPassosFeitos2()  {
        return PassosFeitos2;
    }

}
