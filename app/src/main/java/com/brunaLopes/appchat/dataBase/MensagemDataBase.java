package com.brunaLopes.appchat.dataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {MensagemDb.class}, version = 2, exportSchema = false)
public abstract class MensagemDataBase extends RoomDatabase {
    public abstract MensagemDao getMensagemDAO();

    private static final String NOME_BANCO_DE_DADOS = "mensagens.db";

    public static MensagemDataBase getInstance(Context context){
        return Room
                .databaseBuilder(context, MensagemDataBase.class, NOME_BANCO_DE_DADOS)
                .allowMainThreadQueries()
                .build();

    }

}
