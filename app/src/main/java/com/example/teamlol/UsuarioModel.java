package com.example.teamlol;

public class UsuarioModel {

    private String discord;
    private String nome;

    private UsuarioModel(){}

    public UsuarioModel(String discord, String nome) {
        this.discord = discord;
        this.nome = nome;
    }

    public String getDiscord() {
        return discord;
    }

    public void setDiscord(String discord) {
        this.discord = discord;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
