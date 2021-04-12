package com.brunaLopes.appchat.servidor;

import java.util.List;

public interface MensagemListener {

    void onListaDeUsuariosChegando(List<String> usuarios);
    void onMensagemChegando(String remetente, String texto);
    void onMensagemDeErroChegando(String motivo);

}