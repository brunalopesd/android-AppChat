package com.brunaLopes.appchat.servidor;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Application implements MensagemListener {

    private static final Application instancia = new Application();
    public static Application getInstance() {return instancia;}

    private Comunicador comunicador;
    private int FragmentPosition;

    private Application() {
        comunicador = new Comunicador();
        comunicador.addListener(Interpretador.getInstance());
        Interpretador.getInstance().addObservador(this);
    }

    public void enviarMensagemLogin(String userId) {
        String header = "{ \"login\": { \"user-id\":\"";
        String tail   = "\" } }";
        String mensagem = header + userId + tail;
        //Atualizando o id do Usuario.
        Usuario.getInstance().setUserId(userId);
        comunicador.enfileraMensagem(mensagem);
    }

    @Override
    public void onListaDeUsuariosChegando(List<String> usuarios) {
        Log.d("LISTA", "onListaDeUsuariosChegando: " + usuarios.toString());
        ListaUsuarios.getListaDeUsuarios().set(usuarios);
    }

    @Override
    public void onMensagemChegando(String remetente, String texto) {
        ListaMensagem.setNovaMensagem(remetente, texto);

    }

    @Override
    public void onMensagemDeErroChegando(String motivo) {

    }

    public void enviarMensagem(String sender, String receiver, String content) throws JSONException {
        JSONObject message = new JSONObject();
        JSONObject message_info = new JSONObject();
        message_info.put("sender", sender);
        message_info.put("receiver", receiver);
        message_info.put("content", content);
        message.put("message", message_info);

        comunicador.enfileraMensagem(message.toString());
    }

    public int getFragmentPosition() {
        return FragmentPosition;
    }

    public void setFragmentPosition(int FragmentPosition) {
        this.FragmentPosition = FragmentPosition;
    }

}
