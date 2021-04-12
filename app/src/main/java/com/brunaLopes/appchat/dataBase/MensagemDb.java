package com.brunaLopes.appchat.dataBase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MensagemDb {

    @PrimaryKey
    @NonNull
    private String mensagem;

    public MensagemDb(String mensagem) {
        this.mensagem = mensagem;
    }


    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String username) {
        this.mensagem = mensagem;
    }



}
