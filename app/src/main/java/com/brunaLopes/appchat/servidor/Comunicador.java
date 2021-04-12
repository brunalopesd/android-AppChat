package com.brunaLopes.appchat.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Comunicador {
    private Socket socket;

    private Timer timer = new Timer();
    private final TimerTask taskComunicacao;
    private final ArrayList<ComunicadorListener> listaDeObservadores = new ArrayList<>(1);
    private final ArrayList<String> filaDeMensagens = new ArrayList(1);

    public Comunicador() {
        taskComunicacao = new TimerTask() {
            @Override
            public void run() {

                if (filaDeMensagens.size() > 0) {
                    falaComOServidor(filaDeMensagens.get(0));
                    filaDeMensagens.remove(0);
                } else {
                    String msg = buildMensagemDePing();
                    if (msg != null)
                        falaComOServidor(msg);
                }
            }
        };

        timer.scheduleAtFixedRate(taskComunicacao, 0, 2000);
    }

    public void addListener(ComunicadorListener observador) {
        listaDeObservadores.add(observador);
    }

    public void removeListener(ComunicadorListener observador) {
        listaDeObservadores.remove(observador);
    }

    public synchronized void enfileraMensagem(String mensagem) {
        filaDeMensagens.add(mensagem);
    }

    private String buildMensagemDePing() {
        String header = "{ \"ping\": { \"user-id\":\"";
        String tail = "\" } }";

        if (Usuario.getInstance().getUserId() != null)
            return header + Usuario.getInstance().getUserId() + tail;
        else
            return null;
    }

    private void falaComOServidor(String mensagem) {
        try {
            //abrindo o socket com o servidor.
            socket = new Socket("192.168.25.49", 1408);
            //
            PrintStream ps = new PrintStream(socket.getOutputStream());
            ps.println(mensagem);
            //

            String feedback = new String();
            System.out.println("antes de ler.");
            //Reading back
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner reader = new Scanner(br);
                feedback = br.readLine();
                //
                reader.close();
                socket.close();
                socket = null;
                //Chamar um observador/listener
                for (ComunicadorListener observador : listaDeObservadores) {
                    observador.onMenssagemChegandoDoServidor(feedback);
                }

            } catch (IOException ex) {
                Logger.getLogger(Comunicador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException e) {
            System.out.println("Erro ao enviar msg: " + e.getMessage());
        }
    }
}
