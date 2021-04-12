package com.brunaLopes.appchat.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.Fragment;

import com.brunaLopes.appchat.R;
import com.brunaLopes.appchat.dataBase.MensagemDao;
import com.brunaLopes.appchat.dataBase.MensagemDataBase;
import com.brunaLopes.appchat.dataBase.MensagemDb;
import com.brunaLopes.appchat.dataBase.UserDao;
import com.brunaLopes.appchat.dataBase.UserDataBase;
import com.brunaLopes.appchat.dataBase.UserDb;
import com.brunaLopes.appchat.servidor.Application;
import com.brunaLopes.appchat.servidor.ListaMensagem;
import com.brunaLopes.appchat.servidor.Usuario;

import org.json.JSONException;

import java.util.List;
import java.util.Objects;

public class Mensagens extends Fragment {

    List<Mensagem> lista;
    MensagemAdapter mensagemAdapter;
    Usuario usuario = Usuario.getInstance();
    private MensagemDao dao_msg;
    private Context context;

    public EditText mensagem_enviada;

    public Mensagens() {
        super();
    }



    private void saveMsgOnDb(String msg) {
        MensagemDataBase mensagem_db = MensagemDataBase.getInstance(context);
        dao_msg = mensagem_db.getMensagemDAO();
//        dao_msg.save(new MensagemDb(mensagem_db));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mensagens, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((TextView) Objects.requireNonNull(getActivity()).findViewById(R.id.user_to_chat)).setText(Usuario.getInstance().getCurrentContact());
    }


    @Override
    public void onStart() {
        super.onStart();
        final ListView messages_list = Objects.requireNonNull(getActivity()).findViewById(R.id.list_view_messages);
        mensagemAdapter = new MensagemAdapter();
        messages_list.setAdapter(mensagemAdapter);

        Button bt_enviar = Objects.requireNonNull(getActivity()).findViewById(R.id.bt_send_message);
        bt_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mensagem_enviada = Objects.requireNonNull(getActivity()).findViewById(R.id.message_sent);
                final String message = mensagem_enviada.getText().toString();
                if (!message.isEmpty()) {
                    final Thread r = new Thread() {
                        @Override
                        public void run() {
                            try {
                                Application.getInstance().enviarMensagem(usuario.getUserId(), usuario.getCurrentContact(), message);
                                ListaMensagem.setNovaMensagem(usuario.getUserId(), message);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    r.start();
                }
                mensagem_enviada.setText("");
            }
        });
        ListaMensagem.getListaDeMensagem().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(final Observable sender, int propertyId) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        lista = ((ObservableField<List<Mensagem>>) sender).get();
                        mensagemAdapter.notifyDataSetChanged();
                    }
                };
                Objects.requireNonNull(getActivity()).runOnUiThread(r);
            }
        });

        Button bt_voltar = Objects.requireNonNull(getActivity()).findViewById(R.id.bt_voltar);
        bt_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Application.getInstance().setFragmentPosition(1);
            }
        });

    }

    public static class Mensagem {

        String sender, content;

        public Mensagem(String sender, String content) {
            this.sender = sender;
            this.content = content;
        }

    }


    class MensagemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (lista != null) {
                return lista.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            if (lista != null) {
                return lista.get(i);
            }
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup container) {
            String sender, content;
            Mensagem mensagem = lista.get(position);
            sender = mensagem.sender;
            content = mensagem.content;

                if (sender.equals(usuario.getUserId())) {
                    view = getLayoutInflater().inflate(R.layout.right_baloon, container, false);
                } else if (sender.equals(usuario.getCurrentContact())) {
                    view = getLayoutInflater().inflate(R.layout.left_baloon, container, false);
//                    ((TextView) view.findViewById(R.id.name_txt)).setText(sender);
                }
                if (view != null) {
                    ((TextView) view.findViewById(R.id.txt_mensagem)).setText(content);
                }

            return view;
        }
    }

}