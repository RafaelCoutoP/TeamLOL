package com.example.teamlol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FormularioLogin extends AppCompatActivity {

    private TextView text_cadastro;

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
    }

    private void IniciarComponentes(){
        text_cadastro = findViewById(R.id.text_cadastro);
    }
}