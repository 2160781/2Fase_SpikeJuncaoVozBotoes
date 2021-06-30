package com.cursoandroid.splashscreen;

import java.util.ArrayList;
import java.util.List;


public class DadosApp extends MainActivity {

    private List<Tarefas> listaPassos;
    private int posicao;
    private int PassosFeitos1 = 0;



    // singleton
    public DadosApp() {

        listaPassos = new ArrayList<>();
        listaPassos.add(new Tarefas("1. Passo --> Preparação de ingredientes"));
        listaPassos.add(new Tarefas("2. Passo --> Mistura de ingredientes"));
        listaPassos.add(new Tarefas("3. Passo --> Coloque o bolo no forno"));
        listaPassos.add(new Tarefas("4. Passo --> Finalizar o bolo"));
        posicao = 1;

        }





    public String getTextoPassoReceita() {

        return listaPassos.get(posicao - 1).getTexto();
    }

    public void avancar() {
        if (posicao < listaPassos.size()){
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
        return listaPassos.size();
    }

    public void marcarFeito() {
        listaPassos.get(posicao - 1).setFeito(true);
    }

    public int getPassosFeitos1()  {
        return PassosFeitos1;
    }

}
