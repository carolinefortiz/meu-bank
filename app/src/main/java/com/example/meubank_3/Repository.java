package com.example.meubank_3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Repository extends SQLiteOpenHelper {

    private static final String NOME_DB = "conta";
    private static final int VERSION = 1;


    public Repository(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
        getWritableDatabase();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()){
            db.execSQL("PRAGMA foreing_keys=ON");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlUser = "create TABLE usuario (\n" +
                "    id int not null,\n" +
                "    nome text,\n" +
                "    agencia text,\n" +
                "    conta text,\n" +
                "    senha text,\n" +
                "    saldo double,\n" +
                "    PRIMARY KEY (id) \n" +
                "    );";
        sqLiteDatabase.execSQL(sqlUser);

        String sqlAdd = "INSERT INTO `usuario`(`id`, `nome`, `agencia`, 'conta', `senha`, `saldo`) VALUES ('1','Caroline','1234','123456','000000', '500')";
        sqLiteDatabase.execSQL(sqlAdd);

        String sqlExtrato = "create TABLE extrato (\n" +
                "\tid_fk int,\n" +
                "    log text,\n" +
                "    FOREIGN KEY (id_fk)\n" +
                "    REFERENCES usuario (id));";
        sqLiteDatabase.execSQL(sqlExtrato);

        String sqlChaves = "CREATE TABLE chaves (\n" +
                "\tid_fk int,\n" +
                "    chave text,\n" +
                "    usos int,\n" +
                "    FOREIGN KEY (id_fk)\n" +
                "    REFERENCES usuario (id));";
        sqLiteDatabase.execSQL(sqlChaves);

        String sqlFavoritos = "CREATE TABLE favoritos (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "chavePix TEXT NOT NULL\n" +
                ");";
        sqLiteDatabase.execSQL(sqlFavoritos);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sqlUser = "DROP TABLE IF EXISTS usuario";
        String sqlExtrato = "DROP TABLE IF EXISTS extrato";
        String sqlChaves = "DROP TABLE IF EXISTS chaves";
        String sqlFavoritos = "DROP TABLE IF EXISTS favoritos";
        sqLiteDatabase.execSQL(sqlUser);
        sqLiteDatabase.execSQL(sqlExtrato);
        sqLiteDatabase.execSQL(sqlChaves);
        sqLiteDatabase.execSQL(sqlFavoritos);
        onCreate(sqLiteDatabase);

    }

    public String getAgencia(){
        String agencia ;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT agencia FROM usuario WHERE 1", null);
        cursor.moveToFirst();
        agencia = cursor.getString(0);
        cursor.close();
        return  agencia;
    }

    public String getConta(){
        String conta ;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT conta FROM usuario WHERE 1", null);
        cursor.moveToFirst();
        conta = cursor.getString(0);
        cursor.close();
        return  conta;
    }

    public String getSenha(){
        String senha ;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT senha FROM usuario WHERE 1", null);
        cursor.moveToFirst();
        senha = cursor.getString(0);
        cursor.close();
        return  senha;
    }

    public ArrayList<String> getExtrato(){
        ArrayList<String> extrato = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT log FROM extrato", null);

        if(cursor.moveToFirst()){
            do{
                String log = cursor.getString(0);
                extrato.add(log);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return extrato;
    }

    public void adicionarMovimentacao(String descricao) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_fk", 1);
        values.put("log", descricao);
        db.insert("extrato", null, values);
    }

    public double getSaldo() {
        double saldo = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT saldo FROM usuario WHERE 1", null);
        if (cursor.moveToFirst()) {
            saldo = cursor.getDouble(0);
        }
        cursor.close();
        return saldo;
    }

    public void adicionarChavePixCpf(String chavePixCpf) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("chave", chavePixCpf);
        values.put("usos", 0);
        db.insert("chaves", null, values);
    }

    public void adicionarChavePixTelefone(String chavePixTelefone) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("chave", chavePixTelefone);
        values.put("usos", 0);
        db.insert("chaves", null, values);
    }

    public List<String> getChavesPix() {
        List<String> chavesPix = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT chave FROM chaves", null);
        if (cursor.moveToFirst()) {
            do {
                String chave = cursor.getString(0);
                chavesPix.add(chave);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return chavesPix;
    }

    public void adicionarChaveFavorita(String chavePix) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("chavePix", chavePix);
        db.insert("favoritos", null, values);
    }

    public List<String> listarChavesFavoritas() {
        List<String> favoritos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT chavePix FROM favoritos", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String chavePix = cursor.getString(cursor.getColumnIndex("chavePix"));
                favoritos.add(chavePix);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return favoritos;
    }

    public boolean chaveExiste(String chave) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {"chave"};
        String selection = "chave = ?";
        String[] selectionArgs = {chave};

        Cursor cursor = db.query("chaves", columns, selection, selectionArgs, null, null, null);

        boolean existe = cursor.moveToFirst();

        cursor.close();
        db.close();

        return existe;
    }

    public void removerChave(String chave) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "chave = ?";
        String[] whereArgs = {chave};

        db.delete("chaves", whereClause, whereArgs);

        db.close();
    }



}
