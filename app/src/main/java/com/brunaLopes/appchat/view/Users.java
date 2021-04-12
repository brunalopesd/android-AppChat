package com.brunaLopes.appchat.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.Fragment;

import com.brunaLopes.appchat.R;
import com.brunaLopes.appchat.servidor.Application;
import com.brunaLopes.appchat.servidor.ListaUsuarios;
import com.brunaLopes.appchat.servidor.Usuario;

import java.util.List;
import java.util.Objects;

public class Users extends Fragment {


    public static final String TITULO_APP_BAR = "Contatos";
    List<String> lista;
    UsersAdapter usersAdapter;

    public Users() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(TITULO_APP_BAR);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.users_online, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        final ListView lista_users = Objects.requireNonNull(getActivity()).findViewById(R.id.users_online_list);
        usersAdapter = new UsersAdapter();
        lista_users.setAdapter(usersAdapter);

        lista_users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Application.getInstance().setFragmentPosition(2);
                Usuario.getInstance().setCurrentContact(usersAdapter.getItem(position).toString());
            }
        });

        // lista de users
        ListaUsuarios.getListaDeUsuarios().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(final Observable sender, int propertyId) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        lista = ((ObservableField<List<String>>) sender).get();
                        usersAdapter.notifyDataSetChanged();
                    }
                };
                Objects.requireNonNull(getActivity()).runOnUiThread(r);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    class UsersAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (lista != null) return lista.size();
            return 0;
        }

        @Override
        public Object getItem(int i) {
            if (lista != null) return lista.get(i);
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup container) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.user_item, container, false);
            }
            if (lista != null) {
                String contato = lista.get(position);

                ((TextView) view.findViewById(R.id.txt_user_name)).setText(contato);
            }

            return view;
        }
    }
}
