package com.example.meubank_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class PixActivity extends AppCompatActivity {

    private Conta conta;
    private Repository repository;
    private EditText chavePix;
    private EditText valorPix;
    private boolean isFavorito = false;
    private Switch switchFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pix);

        conta = (Conta) getIntent().getSerializableExtra("conta");
        chavePix = findViewById(R.id.editTextChave);
        valorPix = findViewById(R.id.editTextValorPix);
        repository = new Repository(this);

        switchFavoritos = findViewById(R.id.switchFavoritos);
        switchFavoritos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isFavorito = isChecked;
            }
        });
    }

    public void cadRemPix(View view) {
        Intent intent = new Intent(this, ChavesActivity.class);
        startActivity(intent);
    }

    public void exibirListaChaves(View view) {
        Intent intent = new Intent(this, ListaChavesActivity.class);
        startActivity(intent);
    }

    public void enviarPix(View view) {
        String valorTransfPix = valorPix.getText().toString();
        boolean isFavorito = switchFavoritos.isChecked();
        if (!valorTransfPix.isEmpty()) {
            double valorPix = Double.parseDouble(valorTransfPix);
            double saldoAtual = repository.getSaldo();

            if (valorPix <= saldoAtual) {
                double novoSaldo = saldoAtual - valorPix;

                // para atualizar o saldo no banco de dados
                SQLiteDatabase db = repository.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("saldo", novoSaldo);
                db.update("usuario", values, "id = ?", new String[]{"1"});

                repository.adicionarMovimentacao("Pix: - " + valorTransfPix);

                Toast.makeText(PixActivity.this, "Pix realizado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PixActivity.this, ContaActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(PixActivity.this, "Valor excede o saldo da conta!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(PixActivity.this, "Digite um valor válido!", Toast.LENGTH_SHORT).show();
        }
        if (isFavorito) {
            String chavePixText = chavePix.getText().toString();
            repository.adicionarChaveFavorita(chavePixText);}
        }

    public void favoritos(View view) {
        Intent intent = new Intent(this, FavoritosActivity.class);
        startActivity(intent);
    }
}