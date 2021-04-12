package com.brunaLopes.appchat.dataBase;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface MensagemDao {

    @Insert
    void save(MensagemDb msg);


}
