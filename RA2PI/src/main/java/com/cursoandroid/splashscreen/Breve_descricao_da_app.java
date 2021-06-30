package com.cursoandroid.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;

public class Breve_descricao_da_app extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breve_descricao_da_app);
    }



    @Override
    public boolean dispatchKeyEvent( KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {

            case KeyEvent.KEYCODE_ENTER:
                if (action == KeyEvent.ACTION_DOWN) {

                    Intent anterior = new Intent(this,
                            UserActivity.class);
                    startActivity(anterior);
                }
                return true;

            default:
                return super.dispatchKeyEvent(event);
        }

    }
}