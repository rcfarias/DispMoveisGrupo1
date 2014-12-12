package com.rafael.gradeweb;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FragmentoListaSemestresUsuario extends Fragment {

    private ListView listaSemestresListView;

    private Context context;
    private FragmentManager frgManager;
    private FragmentoListaDisciplinasSemestreSelecionado fragment;

    public FragmentoListaSemestresUsuario() { }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_fragmento_lista_semesteres, container, false);

        listaSemestresListView = (ListView) rootView.findViewById(R.id.semestres_list_view);

        CustomListAdapter myAdapter = new CustomListAdapter(context);

        listaSemestresListView.setAdapter(myAdapter);
        myAdapter.loadObjects();

        listaSemestresListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                //Toast.makeText(getActivity(), "You clicked" + (String) semestresList.get(position), 5000).show();

                fragment = new FragmentoListaDisciplinasSemestreSelecionado();
                Bundle args = new Bundle();

                ParseObject horario = (ParseObject) adapter.getItemAtPosition(position);

                GradeWEBApplication.getInstance().setHorario(horario);

                List<ParseObject> listaTurmas = horario.getList("turmas");
                ArrayList listaIdsTurmas = new ArrayList();

                for(ParseObject cadaTurma : listaTurmas) {
                    listaIdsTurmas.add(cadaTurma.getObjectId());
                }

                Toast.makeText(getActivity(), "semestre selecionado: " + (String) horario.getString("semestre"), 5000).show();

                args.putString("semestre", horario.getString("semestre"));
                args.putStringArrayList("turmas_ids", listaIdsTurmas);


                fragment.setHorarioObject(horario);
                fragment.setArguments(args);
                frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        });

        return rootView;
    } // end of onCreateView

}
