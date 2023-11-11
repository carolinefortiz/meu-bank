package com.example.meubank_3;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class FavoritosActivity extends AppCompatActivity {
    private ListView favoritos;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        repository = new Repository(this);

        favoritos = findViewById(R.id.listaFavoritos);

        // Carregue a lista de chaves favoritas do banco de dados
        List<String> chavesFavoritas = repository.listarChavesFavoritas();

        // Crie um ArrayAdapter para preencher a ListView com as chaves favoritas
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chavesFavoritas);

        // Associe o adaptador à ListView
        favoritos.setAdapter(adapter); // Corrigido o nome da variável
    }
}
