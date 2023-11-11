package com.example.meubank_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RetiradaActivity extends AppCompatActivity {

    private Conta conta;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retirada);
        conta = (Conta) getIntent().getSerializableExtra("conta");

        repository = new Repository(this);

        Button retiradaButton = findViewById(R.id.efetuarRetirada);
        EditText valorRetiradaEditText = findViewById(R.id.editTextValorRetirada);

        retiradaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valorRetiradaStr = valorRetiradaEditText.getText().toString();
                if (!valorRetiradaStr.isEmpty()) {
                    double valorRetirada = Double.parseDouble(valorRetiradaStr);
                    double saldoAtual = repository.getSaldo();

                    if (valorRetirada <= saldoAtual) {
                        double novoSaldo = saldoAtual - valorRetirada;

                        // para atualizar o saldo no banco de dados
                        SQLiteDatabase db = repository.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("saldo", novoSaldo);
                        db.update("usuario", values, "id = ?", new String[]{"1"});

                        repository.adicionarMovimentacao("Retirada: - " + valorRetiradaStr);

                        Toast.makeText(RetiradaActivity.this, "Saque realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RetiradaActivity.this, ContaActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RetiradaActivity.this, "Valor excede o saldo da conta!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RetiradaActivity.this, "Digite um valor válido!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}