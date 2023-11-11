package com.example.meubank_3;

public class Transacoes {
    private String tipo;
    private double valor;
    private String data;

    public Transacoes(String tipo, double valor, String data) {
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public String getData() {
        return data;
    }
}

