package com.cursoandroid.splashscreen;

public class Tarefas2 {

    private String texto;
    private Boolean feito;

    Tarefas2(String texto) {
        this.texto = texto;
        this.feito = false;
    }



    public String getTexto() {
        return texto;
    }

    public void setFeito(Boolean feito) {
        this.feito = feito;
    }
}
