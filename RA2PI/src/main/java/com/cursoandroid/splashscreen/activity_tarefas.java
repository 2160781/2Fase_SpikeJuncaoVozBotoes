package com.cursoandroid.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_tarefas extends AppCompatActivity {

    boolean estadoBoton;
    Button boton;
    TextView cajadetexto;
    ImageView ver_imagen;


    private DadosApp dadosApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dadosApp = new DadosApp();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefas);

        estadoBoton=true;
        boton=findViewById(R.id.Button);
        ver_imagen=findViewById(R.id.imageView3);
        cajadetexto= findViewById(R.id.textView);

        cajadetexto.setText(dadosApp.getTextoPassoReceita());



    }

    //Metodos
    @Override
    public boolean dispatchKeyEvent( KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        int PassosFeitos1 = dadosApp.getPassosFeitos1();
        switch (keyCode) {
            //Seguinte
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (action == KeyEvent.ACTION_DOWN) {


                    dadosApp.avancar();
                    dadosApp.marcarFeito();
                    cajadetexto.setText(dadosApp.getTextoPassoReceita());

                    estadoBoton= false;

                }
                return true;
            //Anterior
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (action == KeyEvent.ACTION_DOWN) {

                    dadosApp.retroceder();
                    cajadetexto.setText(dadosApp.getTextoPassoReceita());

                    estadoBoton= false;

                }

                return true;
            //Marcar passo como "Feito"
            case KeyEvent.KEYCODE_DPAD_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    //Colocar a posição da tarefa "Feito"
                    ver_imagen.setImageResource(R.drawable.certo);
                    PassosFeitos1++;

                }

                return true;
            case KeyEvent.KEYCODE_ENTER:
                if (action == KeyEvent.ACTION_DOWN) {

                    Intent inicio = new Intent(this,
                            MainActivity.class);
                    startActivity(inicio);
                }

                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }




}