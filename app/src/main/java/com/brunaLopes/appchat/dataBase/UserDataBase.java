package com.brunaLopes.appchat.dataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UserDb.class}, version = 2, exportSchema = false)
public abstract class UserDataBase extends RoomDatabase {
    public abstract UserDao getUserDAO();

    private static final String NOME_BANCO_DE_DADOS = "users.db";

    public static UserDataBase getInstance(Context context){
        return Room
                .databaseBuilder(context, UserDataBase.class, NOME_BANCO_DE_DADOS)
                .allowMainThreadQueries()
                .build();

    }

}
