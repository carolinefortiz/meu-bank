package com.example.meubank_3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DepositoActivity extends AppCompatActivity {
    private Conta conta;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito);
        conta = (Conta) getIntent().getSerializableExtra("conta");

        repository = new Repository(this);

        Button depositar = findViewById(R.id.depositar);
        EditText editTextValorDeposito = findViewById(R.id.editTextValorDeposito);

        depositar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valorDepositoStr = editTextValorDeposito.getText().toString();
                if (!valorDepositoStr.isEmpty()) {
                    double valorDeposito = Double.parseDouble(valorDepositoStr);

                    double saldoAtual = repository.getSaldo();

                    double novoSaldo = saldoAtual + valorDeposito;

                    SQLiteDatabase db = repository.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("saldo", novoSaldo);
                    db.update("usuario", values, "id = ?", new String[]{"1"});

                    repository.adicionarMovimentacao("Depósito: +" + valorDepositoStr);

                    Toast.makeText(DepositoActivity.this, "Depósito realizado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DepositoActivity.this, ContaActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DepositoActivity.this, "Digite um valor válido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
