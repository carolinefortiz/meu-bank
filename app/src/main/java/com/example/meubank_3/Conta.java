package com.example.meubank_3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

public class Conta implements Serializable {

    private String numeroConta;
    private double saldo;
    private List<Transacoes> extrato;

    public Conta(String numeroConta) {
        this.numeroConta = numeroConta;
        this.saldo = 0.0;
        this.extrato = new ArrayList<>();
    }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            adicionarTransacao("Depósito", valor);
        }
    }

    public boolean retirar(double valor) {
        if (saldo >= valor && valor > 0) {
            saldo -= valor;
            adicionarTransacao("Retirada", valor);
            return true;
        }
        return false;
    }

    public List<Transacoes> getExtrato() {
        return extrato;
    }

    private void adicionarTransacao(String tipo, double valor) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String data = sdf.format(new Date());
        Transacoes transacao = new Transacoes(tipo, valor, data);
        extrato.add(transacao);
    }
}


