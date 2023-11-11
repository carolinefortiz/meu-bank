package com.example.meubank_3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText agenciaEditText;
    private EditText contaEditText;
    private EditText senhaEditText;
    private Button entrarButton;

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repository = new Repository(this);

        agenciaEditText = findViewById(R.id.editTextAgencia);
        contaEditText = findViewById(R.id.editTextConta);
        senhaEditText = findViewById(R.id.editTextTextSenha);
        entrarButton = findViewById(R.id.buttonEntrar);
    }

    public void entrar(View view) {
        if (view == entrarButton) {
            String agencia = agenciaEditText.getText().toString();
            String conta = contaEditText.getText().toString();
            String senha = senhaEditText.getText().toString();

            if (validarAgencia(agencia) && validarConta(conta) && validarSenha(senha)) {
                Intent intent = new Intent(this, ContaActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Dados inválidos. Por favor, verifique os campos.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean validarAgencia(String agencia) {
        return agencia.length() == 4 && agencia.equals(repository.getAgencia());
    }

    private boolean validarConta(String conta) {
        return conta.length() == 6 && conta.equals(repository.getConta());
    }

    private boolean validarSenha(String senha) {
        return senha.length() == 6 && senha.equals(repository.getSenha());
    }

}
