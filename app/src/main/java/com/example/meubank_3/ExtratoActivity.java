package com.example.meubank_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class ExtratoActivity extends AppCompatActivity {
    private Repository repository;
    private ListView extratoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato);

        repository = new Repository(this);
        extratoListView = findViewById(R.id.idListExtrato);

        ArrayList<String> extrato = repository.getExtrato();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, extrato);
        extratoListView.setAdapter(adapter);
    }
}
