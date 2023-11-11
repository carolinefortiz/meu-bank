package com.example.meubank_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ListaChavesActivity extends AppCompatActivity {
    private Repository repository;
    private ListView chavesPixListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_chaves);

        repository = new Repository(this);
        chavesPixListView = findViewById(R.id.listaChaves);

        List<String> chavesPix = repository.getChavesPix();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, chavesPix);
        chavesPixListView.setAdapter(adapter);
    }
}
