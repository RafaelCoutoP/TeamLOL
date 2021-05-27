package com.example.teamlol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ListandoUsuarios extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mFirestoreList;

    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listando_usuarios);
        getSupportActionBar().hide();

        firebaseFirestore = FirebaseFirestore.getInstance();
        IniciarComponentes();


        Query query = firebaseFirestore.collection("Usuarios");

        FirestoreRecyclerOptions<UsuarioModel> options = new FirestoreRecyclerOptions.Builder<UsuarioModel>()
                .setQuery(query, UsuarioModel.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<UsuarioModel, UserViewHolder>(options) {
            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
                return new UserViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull UsuarioModel model) {
                holder.list_name.setText(model.getNome());
                holder.list_discord.setText(model.getDiscord());
            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);

    }

    private class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView list_name;
        private TextView list_discord;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            list_name = itemView.findViewById(R.id.list_nome);
            list_discord = itemView.findViewById(R.id.list_discord);

        }
    };

    private void IniciarComponentes(){
        mFirestoreList = findViewById(R.id.firestore_list);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}