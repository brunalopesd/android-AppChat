package com.brunaLopes.appchat.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.brunaLopes.appchat.R;
import com.brunaLopes.appchat.dataBase.UserDao;
import com.brunaLopes.appchat.dataBase.UserDataBase;
import com.brunaLopes.appchat.dataBase.UserDb;
import com.brunaLopes.appchat.servidor.Application;


import java.util.Objects;

public class Login extends Fragment {

    public static final String TITULO_APP_BAR = "Login";
    private UserDao dao;
    public EditText user_name;
    public Context context;


    public Login() {
    }

    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(TITULO_APP_BAR);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button bt_entrar = Objects.requireNonNull(getActivity()).findViewById(R.id.bt_entrar);
        user_name = getActivity().findViewById(R.id.nome_usuario);
        bt_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = user_name.getText().toString();
                checkUser(usuario);
                saveUserOnDB(usuario);
                conectToServer(usuario);
            }
        });
    }

    private void conectToServer(String user) {
        Application.getInstance().enviarMensagemLogin(user);
        Application.getInstance().setFragmentPosition(1);
        showMessage("CONEXAO FEITA");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login, container, false);
    }

    private void saveUserOnDB(String usuario) {
        UserDataBase user_db = UserDataBase.getInstance(getContext());
        dao = user_db.getUserDAO();
        dao.save(new UserDb(usuario));
    }

    private void showMessage(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
        return;
    }

    private boolean userFoundOnDataBase(String user) {
        UserDataBase user_db = UserDataBase.getInstance(context);
        dao = user_db.getUserDAO();
        return dao.findByUserName(user);
    }


    private void checkUser(String usuario) {
        if (usuario.equals("")) {
            showMessage("Username nao pode ficar em branco");
        }
//        else if (userFoundOnDataBase(usuario)) {
//            showMessage("Usuario já existe, acessando contatos.....");
//        }
        else {
            saveUserOnDB(usuario);
            showMessage("Usuario registrado com sucesso! Acessando contatos...");
        }

    }
}
