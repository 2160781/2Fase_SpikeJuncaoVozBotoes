package com.cursoandroid.splashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Pattern;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class LoginActivity extends AppCompatActivity {

    TextView bienvenidoLabel, continuarLabel, nuevoUsuario, olvidasteContrasena;
    ImageView loginImageView;
    TextInputLayout usuarioTextField, contrasenaTextField;
    MaterialButton inicioSesion;
    TextInputEditText emailEditText, passwordEditText;
    FirebaseAuth mAuth;
    private static final int RECONOCEDOR_VOZ = 7;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        inicializarElementos();
        irAnuevoUsuario();

        inicioSesion.setOnClickListener(v -> {
            validate();
        });

        olvidasteContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Google Sign-In
        signInButton = findViewById(R.id.loginGoogle);
        signInButton.setOnClickListener(v -> sigInWithGoogle());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

    }

    private void sigInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            }catch (ApiException e){
                Toast.makeText(this, "Fallo de Google", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(LoginActivity.this, "Fallo al iniciar sesi??n", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void validate() {
        //Toast.makeText(LoginActivity.this,"Prueba Toast!",Toast.LENGTH_LONG).show();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Correo inv??lido");
        }else{
            emailEditText.setError(null);
        }
        if(password.isEmpty() || password.length() < 8){
            passwordEditText.setError("Se necesitan m??s de 8 caracteres para la contrase??a");
        }else if(!Pattern.compile("[0-9]").matcher(password).find()){
            passwordEditText.setError("Necesitas al menos un n??mero");
        }else{
            passwordEditText.setError(null);
        }
        if(!email.isEmpty() && !password.isEmpty()){
            iniciarSesion(email, password);

        }


    }
    private void iniciarSesion(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                            startActivity(intent);




                            finish();
                        }else{
                            //No funciona el Toast
                            Toast.makeText(LoginActivity.this,"Credenciales equivocadas, prueba de nuevo",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void irAnuevoUsuario() {
        nuevoUsuario.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);

            Pair[] pairs = new Pair[7];
            pairs[0] = new Pair<View, String>(loginImageView, "logoImageTrans");
            pairs[1] = new Pair<View, String>(bienvenidoLabel, "textTrans");
            pairs[2] = new Pair<View, String>(continuarLabel, "iniciaSesionTextTrans");
            pairs[3] = new Pair<View, String>(usuarioTextField, "emailInputTextTrans");
            pairs[4] = new Pair<View, String>(contrasenaTextField, "passwordInputTextTrans");
            pairs[5] = new Pair<View, String>(inicioSesion, "buttonSignInTrans");
            pairs[6] = new Pair<View, String>(nuevoUsuario, "newUserTrans");

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }else{
                startActivity(intent);
                finish();
            }
        });
    }
    public void inicializarElementos(){
        loginImageView = findViewById(R.id.loginImageView);
        bienvenidoLabel = findViewById(R.id.bienvenidoLabel);
        continuarLabel = findViewById(R.id.continuarLabel);
        usuarioTextField = findViewById(R.id.usuarioTextField);
        contrasenaTextField = findViewById(R.id.contrasenaTextField);
        inicioSesion = findViewById(R.id.inicioSesionBtn);
        nuevoUsuario = findViewById(R.id.nuevoUsuario);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        olvidasteContrasena = findViewById(R.id.olvidasteContra);
    }
    @Override
    public boolean dispatchKeyEvent( KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    //TODO
                    Intent siguiente = new Intent(this,UserActivity.class);
                    startActivity(siguiente);

                }
                return true;
            case KeyEvent.KEYCODE_ENTER:
                if (action == KeyEvent.ACTION_DOWN) {
                    //TODO

                    inicioSesion = findViewById(R.id.inicioSesionBtn);


                                            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                                            startActivity(intent);




                                            finish();
                                        }else{
                                            //No funciona el Toast
                                            Toast.makeText(LoginActivity.this,"Credenciales equivocadas, prueba de nuevo",Toast.LENGTH_LONG).show();
                                        }






                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }
}