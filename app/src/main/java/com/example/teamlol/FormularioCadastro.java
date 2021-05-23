package com.example.teamlol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormularioCadastro extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText text_nome;
    private EditText text_email;
    private EditText text_senha;
    private EditText text_discord;
    private Spinner sp_rota;
    private Spinner sp_elo;
    private Button bt_cadastrar;
    String IDdoUsuario;

    //erro/sucesso
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
                  CadastroDoUsuario(v);
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

    private void CadastroDoUsuario(View v){

        String email = text_email.getText().toString();
        String senha = text_senha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {

                    SalvarDadosDoUsuario();

                    Snackbar snackbar = Snackbar.make(v, menssagens[1], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    String erro;
                    //Bateria de validações
                    try {
                        throw task.getException();
                    //validação senha
                    }catch (FirebaseAuthWeakPasswordException e) {
                        erro = "A senha deve ter no mínimo 6 caracteres";
                    //validação email repetido
                    }catch (FirebaseAuthUserCollisionException e) {
                        erro = "E-mail já está sendo utilizado, favor tentar outro";
                    //validação de formato do e-mail
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erro = "E-mail com formato inválido";
                    // validação para qualquer tipo de erro não esperado
                    }catch (Exception e){
                        erro = "Erro ao cadastrar usuário";
                    }

                    Snackbar snackbar = Snackbar.make(v, erro, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    private void SalvarDadosDoUsuario(){
        String nome = text_nome.getText().toString();
        String discord = text_discord.getText().toString();
        String rota = sp_rota.getSelectedItem().toString();
        String elo = sp_elo.getSelectedItem().toString();

        FirebaseFirestore database = FirebaseFirestore.getInstance();

        Map<String, Object> dadosUsuarios = new HashMap<>();
        dadosUsuarios.put("nome", nome);
        dadosUsuarios.put("discord", discord);
        dadosUsuarios.put("rota", rota);
        dadosUsuarios.put("elo", elo);

        IDdoUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentoReferencia = database.collection("Usuarios").document(IDdoUsuario);
        documentoReferencia.set(dadosUsuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("database", "Dados Salvos com sucesso!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("database_erro", "Não foi possivel salvar os dados" + e.toString());
            }
        });
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