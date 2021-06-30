package com.cursoandroid.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_tarefas2 extends AppCompatActivity {

    boolean estadoBoton;
    Button boton;
    TextView TFeito;
    boolean estadoFeito;
    TextView cajadetexto;
    ImageView ver_imagen;

    private DadosAppT2 dadosAppT2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dadosAppT2 = new DadosAppT2();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefas2);

        estadoBoton=true;
        boton=findViewById(R.id.Button);
        ver_imagen = findViewById(R.id.imageView);
        cajadetexto= findViewById(R.id.textView5);

        cajadetexto.setText(dadosAppT2.getTextoPassoReceita());
        ver_imagen.setImageResource(R.drawable.errado);


    }

    //Metodos
    @Override
    public boolean dispatchKeyEvent( KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        int PassosFeitos2 = dadosAppT2.getPassosFeitos2();
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (action == KeyEvent.ACTION_DOWN) {

                    dadosAppT2.avancar();
                    dadosAppT2.marcarFeito();
                    cajadetexto.setText(dadosAppT2.getTextoPassoReceita());

                    estadoBoton= false;

                }
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (action == KeyEvent.ACTION_DOWN) {

                    dadosAppT2.retroceder();
                    cajadetexto.setText(dadosAppT2.getTextoPassoReceita());

                    estadoBoton= false;

                }

                return true;
            //Marcar passo como "Feito"
            case KeyEvent.KEYCODE_DPAD_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    //Colocar a posição da tarefa "Feito"
                    ver_imagen.setImageResource(R.drawable.certo);
                    PassosFeitos2++;

                }

                return true;
            case KeyEvent.KEYCODE_ENTER:
                if (action == KeyEvent.ACTION_DOWN) {
                    Intent inicio = new Intent(this,UserActivity.class);
                    startActivity(inicio);
                }

                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }
}
