package com.rafael.gradeweb;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FragmentoListaDisciplinasSemestreSelecionado extends Fragment{

    private final String fimDeLinha = System.getProperty("line.separator");

    private GradeWEBApplication myApp;

    //private ListView disciplinasListView;
    private TextView disciplinasTextView;
    private Button adicionarDisciplinaButton;
    private LayoutInflater linflater;

    private ParseObject horarioObject;

    public FragmentoListaDisciplinasSemestreSelecionado() { }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myApp = GradeWEBApplication.getInstance();
        this.linflater = inflater;
        String horarioOjectId = (String) getArguments().getString("OBJECT_ID");

        ParseQuery<ParseObject> horarioQuery = ParseQuery.getQuery("Horario");
        horarioQuery.include("turmas");
        horarioQuery.include("turmas.disciplina");
        //horarioQuery.whereEqualTo("objectId", (String) getArguments().getString("OBJECT_ID"))
        horarioQuery.fromLocalDatastore();

        horarioObject = null;
        try {
            horarioObject = (ParseObject) horarioQuery.get(horarioOjectId);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("FragmentoListaDisciplinasSemestreSelecionado", "ERROR!! exception happened 1");
        }

        View viewRaiz = inflater.inflate(R.layout.layout_fragmento_lista_disciplinas_semestre_selecionado, container, false);

        //disciplinasListView = (ListView) viewRaiz.findViewById(R.id.disciplinas_semestres_list_view);
        disciplinasTextView = (TextView) viewRaiz.findViewById(R.id.disciplinas_semestres_text_view);
        adicionarDisciplinaButton = (Button)  viewRaiz.findViewById(R.id.adicionar_disciplina_button);

        List<ParseObject> listaDeTurmasObjects = horarioObject.getList("turmas");

        List<ParseObject> listaDisciplinasObjects = new ArrayList<ParseObject>();
        //ArrayList detalhesTurma = new ArrayList();
        String detalhesTurmas = "";

        for( ParseObject cadaTurma : listaDeTurmasObjects) {
            listaDisciplinasObjects.add(cadaTurma.getParseObject("disciplina"));

            //detalhesTurma.add(cadaTurma.getParseObject("disciplina").getString("DID") + " " + cadaTurma.getParseObject("disciplina").getString("name") +
            //                    fimDeLinha + "Turma: " + cadaTurma.getString("codigoTurma") + " / Professor: " + cadaTurma.getString("professor"));

            detalhesTurmas += (cadaTurma.getParseObject("disciplina").getString("DID") + " " + cadaTurma.getParseObject("disciplina").getString("name") +
                    fimDeLinha + "Turma: " + cadaTurma.getString("codigoTurma") + " / Professor: " + cadaTurma.getString("professor") + fimDeLinha + fimDeLinha);
        }


        disciplinasTextView.setText(detalhesTurmas);
        //disciplinasTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);

        //----------------------------------------------------------------------------------------------------

        /*
        ArrayAdapter<ArrayList> meuAdapter = new ArrayAdapter<ArrayList>(this.getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                detalhesTurma);

        disciplinasListView.setAdapter(meuAdapter);

        disciplinasListView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        */

        //-----------------------------------------------------------------------------------------------------

        adicionarDisciplinaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String horarioOjectId = (String) getArguments().getString("OBJECT_ID");
                Intent intent = new Intent(getActivity(), ActivityAdicionarDisciplina.class);
                intent.putExtra("EXTRA_OBJECT_ID", (String) horarioOjectId);
                startActivity(intent);
                Toast.makeText(getActivity(), "objectId = " + (String) horarioOjectId, 5000).show();
            }
        });

        return  viewRaiz;
    }

}
