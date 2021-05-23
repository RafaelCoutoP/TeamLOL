package com.example.teamlol;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class FormularioCadastro extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText text_nome;
    private EditText text_email;
    private EditText text_senha;
    private EditText text_discord;
    private Spinner sp_rota;
    private Spinner sp_elo;
    private Button bt_cadastrar;

    //erros e sucessos
    String[] menssagens = {"Preencha todos os campos", "Cadastro realizado com sucesso"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cadastro);
        getSupportActionBar().hide();
        IniciarComponentes();

        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = text_nome.getText().toString();
                String email = text_email.getText().toString();
                String senha = text_senha.getText().toString();
                String discord = text_discord.getText().toString();
                String rota = sp_rota.getSelectedItem().toString();
                String elo = sp_elo.getSelectedItem().toString();

                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty() || discord.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v, menssagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else {

                }
            }
        });

        //Spinner Rota
        Spinner spinner = findViewById(R.id.edit_spinner_rota);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.rotas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Spinner Elo
        Spinner spinnerElo = findViewById(R.id.edit_spinner_elo);
        ArrayAdapter<CharSequence> adapterElo = ArrayAdapter.createFromResource(this, R.array.elo, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerElo.setAdapter(adapterElo);
        spinnerElo.setOnItemSelectedListener(this);

    }

    private void IniciarComponentes(){
        text_nome = findViewById(R.id.edit_nome);
        text_email = findViewById(R.id.edit_email);
        text_senha = findViewById(R.id.edit_senha);
        text_discord = findViewById(R.id.edit_discord);
        sp_rota = findViewById(R.id.edit_spinner_rota);
        sp_elo = findViewById(R.id.edit_spinner_elo);
        bt_cadastrar = findViewById(R.id.bt_cadastrar);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}