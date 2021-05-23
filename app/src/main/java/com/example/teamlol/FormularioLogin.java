package com.example.teamlol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FormularioLogin extends AppCompatActivity {

    private TextView text_cadastro;
    private EditText text_email;
    private EditText text_senha;
    private Button btn_logar;
    private ProgressBar progressBar;

    //erro
    String[] menssagens = {"Preencha todos os campos"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_login);
        getSupportActionBar().hide();
        IniciarComponentes();

        text_cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormularioLogin.this, FormularioCadastro.class);
                startActivity(intent);
            }
        });

        btn_logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = text_email.getText().toString();
                String senha = text_senha.getText().toString();
                if (email.isEmpty() || senha.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v, menssagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    AutenticandoUsuarioLogado(v);
                }
            }
        });
    }

    private void AutenticandoUsuarioLogado(View v){
        String email = text_email.getText().toString();
        String senha = text_senha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            IntencaoParaTelaPrincipal();
                        }
                    }, 4000);
                }else{
                    String erro;

                    try {
                        throw task.getException();
                    }catch (Exception e){
                        erro = "Erro ao tentar logar usuário";
                    }
                    Snackbar snackbar = Snackbar.make(v, erro, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser userAtual = FirebaseAuth.getInstance().getCurrentUser();

        if(userAtual != null){
            IntencaoParaTelaPrincipal();
        }
    }

    private void IntencaoParaTelaPrincipal(){
        Intent intent = new Intent(FormularioLogin.this, TelaPrincipal.class);
        startActivity(intent);
        finish();
    }

    private void IniciarComponentes(){
        text_cadastro = findViewById(R.id.text_cadastro);
        text_email = findViewById(R.id.edit_email);
        text_senha = findViewById(R.id.edit_senha);
        btn_logar = findViewById(R.id.bt_entrar);
        progressBar = findViewById(R.id.id_progressao);
    }
}