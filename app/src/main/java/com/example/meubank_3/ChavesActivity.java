package com.example.meubank_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChavesActivity extends AppCompatActivity {

    private Repository repository;
    private EditText editTextTelefone;
    private EditText editTextCpf;
    private EditText editTextChaveRemover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaves);

        repository = new Repository(this);
        editTextTelefone = findViewById(R.id.editTextTelefone);
        editTextCpf = findViewById(R.id.editTextCpf);
        editTextChaveRemover = findViewById(R.id.editTextChaveRemover);
    }

    public void cadastrarCpf(View view) {
            String chavePixCpf = editTextCpf.getText().toString();

            if (validarCpf(chavePixCpf)) {
            editTextCpf.setText("");

            Toast.makeText(this, "Chave PIX cadastrada com sucesso", Toast.LENGTH_LONG).show();
            repository.adicionarChavePixCpf(chavePixCpf);
            Intent intent = new Intent(this, ListaChavesActivity.class);
            startActivity(intent);
            } else {
            Toast.makeText(this, "Chave PIX inválida. Verifique o numero digitado.", Toast.LENGTH_SHORT).show();
            }
        }



    public boolean validarCpf(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(cpf.charAt(i));
            sum += digit * (10 - i);
        }
        int firstVerifier = 11 - (sum % 11);

        if (firstVerifier == 10 || firstVerifier == 11) {
            firstVerifier = 0;
        }
        if (firstVerifier != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }
        sum = 0;
        for (int i = 0; i < 10; i++) {
            int digit = Character.getNumericValue(cpf.charAt(i));
            sum += digit * (11 - i);
        }
        int secondVerifier = 11 - (sum % 11);

        if (secondVerifier == 10 || secondVerifier == 11) {
            secondVerifier = 0;
        }
        return secondVerifier == Character.getNumericValue(cpf.charAt(10));
    }

    public boolean validarCelular(String numeroCelular) {
        numeroCelular = numeroCelular.replaceAll("[^0-9]", "");
        return numeroCelular.length() == 11;
    }


    public void cadastrarTelefone(View view) {
            String chavePixTelefone = editTextTelefone.getText().toString();

            if (validarCelular(chavePixTelefone)) {
                editTextTelefone.setText("");

                Toast.makeText(this, "Chave PIX cadastrada com sucesso", Toast.LENGTH_LONG).show();
                repository.adicionarChavePixTelefone(chavePixTelefone);
                Intent intent = new Intent(this, ListaChavesActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Chave PIX inválida. Verifique o numero digitado.", Toast.LENGTH_SHORT).show();
            }
        }

    public void remover(View view) {
        String chaveParaRemover = editTextChaveRemover.getText().toString();

        // Verifique se a chave existe no banco de dados
        if (repository.chaveExiste(chaveParaRemover)) {
            // Remova a chave do banco de dados
            repository.removerChave(chaveParaRemover);

            // Limpe o EditText
            editTextChaveRemover.setText("");

            // Exiba uma mensagem de sucesso
            Toast.makeText(this, "Chave PIX removida com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            // Exiba uma mensagem de erro, pois a chave não foi encontrada
            Toast.makeText(this, "Chave PIX não encontrada", Toast.LENGTH_SHORT).show();
        }
    }
}