package com.rafael.gradeweb;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class FragmentoListaSemestreUsuario extends Fragment {

    private GradeWEBApplication myApp;

    private ListView semestresListView;
    private TextView debugTextView;

    public FragmentoListaSemestreUsuario() { }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myApp = GradeWEBApplication.getInstance();

        View viewRaiz = inflater.inflate(R.layout.layout_fragmento_lista_semesteres, container, false);

        semestresListView = (ListView) viewRaiz.findViewById(R.id.semestres_list_view);
        //debug only
        debugTextView = (TextView) viewRaiz.findViewById(R.id.debug_text_view);

        ParseQuery<ParseObject> turmasQuery = ParseQuery.getQuery("Horario");
        turmasQuery.whereEqualTo("usuario", myApp.getUsuario());
        turmasQuery.whereEqualTo("semestre", getArguments().getString("SEMESTRE"));
        turmasQuery.include("turmas");
        turmasQuery.include("Turma.disciplina");

        List<ParseObject> turmasObjects = null;
        try {
            turmasObjects = turmasQuery.find();
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("FragmentSemestre", "ERROR!! exception happened 1");
        }

        List<ParseObject> listaDeTurmasObjects = turmasObjects.get(0).getList("turmas.");
        //
        debugTextView.setText("Professor :" + (String) listaDeTurmasObjects.get(0).getString("professor"));

        List<ParseObject> listaDisciplinasObjects = null;

        /*

        for(int k =0; k < listaDeTurmasObjects.size(); k++){
            ParseQuery<ParseObject> disciplinasQuery = ParseQuery.getQuery("Disciplina");
            disciplinasQuery.whereEqualTo("objectID",(ParseObject) listaDeTurmasObjects.get(k));

            try {
                listaDisciplinasObjects.add(disciplinasQuery.find());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        */


        //-----------------------------------------------------------------------------------------------------
        ArrayList listaIds = new ArrayList();
        for(int l = 0; l < listaDeTurmasObjects.size(); l ++) {
            listaIds.add(listaDeTurmasObjects.get(l).getObjectId());
        }


        ArrayList listaDisciplinas = new ArrayList();

        for(int j = 0; j < listaDeTurmasObjects.size(); j++) {
            ParseObject cada = listaDeTurmasObjects.get(j);
            listaDisciplinas.add(cada.getString("DID") + " - " + cada.getString("name") + " Turma" +cada.getString("codigoTurma")+ "/" + cada.getString("professor"));
        }


        ArrayAdapter<ArrayList> meuAdapter = new ArrayAdapter<ArrayList>(this.getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                listaDisciplinas);

        semestresListView.setAdapter(meuAdapter);


        return  viewRaiz;
    }

}
