package com.example.meubank_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ContaActivity extends AppCompatActivity {
    private Conta conta;

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta);
        conta = new Conta("1234");
    }

    public void realizarDeposito(View view) {
        Intent intent = new Intent(this, DepositoActivity.class);
        startActivity(intent);
    }

    public void pix(View view) {
        Intent intent = new Intent(this, PixActivity.class);
        startActivity(intent);
    }

    public void exibirExtrato(View view) {
        Intent intent = new Intent(this, ExtratoActivity.class);
        startActivity(intent);
    }

    public void realizarRetirada(View view) {
        Intent intent = new Intent(this, RetiradaActivity.class);
        startActivity(intent);
    }

    public void saldo(View view) {
        repository = new Repository(this);
        double saldo = repository.getSaldo();
        TextView textViewSaldo = findViewById(R.id.textViewSaldo);
        textViewSaldo.setText("Saldo: R$ " + saldo);
        Toast.makeText(this, "Saldo: R$ " + saldo, Toast.LENGTH_SHORT).show();
    }
}