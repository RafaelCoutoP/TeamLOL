package com.example.teamlol;

public class UsuarioModel {

    private String nome;
    private String discord;
    private String elo;
    private String rota;

    private UsuarioModel(){}

    public UsuarioModel(String nome, String discord, String elo, String rota) {
        this.nome = nome;
        this.discord = discord;
        this.elo = elo;
        this.rota = rota;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDiscord() {
        return discord;
    }

    public void setDiscord(String discord) {
        this.discord = discord;
    }

    public String getElo() {
        return elo;
    }

    public void setElo(String elo) {
        this.elo = elo;
    }

    public String getRota() {
        return rota;
    }

    public void setRota(String rota) {
        this.rota = rota;
    }
}
