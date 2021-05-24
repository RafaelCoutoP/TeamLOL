package com.example.teamlol;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class TelaPrincipal extends AppCompatActivity {

    private TextView nomeUsuario;
    private TextView emailUsuario;
    private TextView discordUsuario;
    private TextView eloUsuraio;
    private TextView rotaUsuario;
    private Button botaoDeslogar;
    String usuarioID;
    FirebaseFirestore database = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        getSupportActionBar().hide();
        IniciarComponentes();

        botaoDeslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(TelaPrincipal.this, FormularioLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        DocumentReference documentReference = database.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                  if(value != null){
                      nomeUsuario.setText(value.getString("nome"));
                      discordUsuario.setText(value.getString("discord"));
                      eloUsuraio.setText(value.getString("elo"));
                      rotaUsuario.setText(value.getString("rota"));
                      emailUsuario.setText(email);
                  }
            }
        });

    }

    private void IniciarComponentes(){
        nomeUsuario = findViewById(R.id.text_nome_usuario);
        emailUsuario = findViewById(R.id.text_email_usuario);
        discordUsuario = findViewById(R.id.text_discord_usuario);
        eloUsuraio = findViewById(R.id.text_elo_usuario);
        rotaUsuario = findViewById(R.id.text_rota_usuario);
        botaoDeslogar = findViewById(R.id.bt_deslogar);

    }
}