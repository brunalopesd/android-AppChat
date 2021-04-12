package com.brunaLopes.appchat.servidor;

import androidx.databinding.ObservableField;

import com.brunaLopes.appchat.view.Mensagens;

import java.util.ArrayList;
import java.util.List;

public class ListaMensagem {
    private static final ObservableField<List<Mensagens.Mensagem>> listaDeMensagem = new ObservableField<>();

    public static ObservableField<List<Mensagens.Mensagem>> getListaDeMensagem() {
        return listaDeMensagem;
    }

    public static void setNovaMensagem(String remetente, String texto) {
        List<Mensagens.Mensagem> aux;
        if (getListaDeMensagem().get() == null) {
            aux = new ArrayList<>();
        } else {
            aux = new ArrayList<>(getListaDeMensagem().get());
        }
        Mensagens.Mensagem mensagem = new Mensagens.Mensagem(remetente, texto);
        aux.add(mensagem);
        getListaDeMensagem().set(aux);
    }
}


