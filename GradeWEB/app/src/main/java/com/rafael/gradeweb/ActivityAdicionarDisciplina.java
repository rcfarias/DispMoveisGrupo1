package com.rafael.gradeweb;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael Farias on 28/11/2014.
 */
public class ActivityAdicionarDisciplina extends Activity{

    private ListView turmasListView;
    private Button okButton;
    private GradeWEBApplication myApplication;
    private String objectID;


    private ParseObject turmaSelecionada;
    private ParseObject horarioObject;
    private List<ParseObject> listaDisciplinasNaoMatriculado;

    private ParseObject getHorarioObject() {
        return horarioObject;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            objectID = (String) extras.getString("EXTRA_OBJECT_ID");
            Toast.makeText(this, "objectId = " + (String) objectID, 5000).show();
        }
        else
            Toast.makeText(this, "extras eh NULL", 5000).show();

        myApplication = GradeWEBApplication.getInstance();

        setContentView(R.layout.layout_activity_selecionar_discplina);
        turmasListView = (ListView) findViewById(R.id.selecionar_turma_list_view);
        okButton = (Button) findViewById(R.id.ok_button);

        ParseQuery<ParseObject> turmasQuery = ParseQuery.getQuery("Horario");
        turmasQuery.include("turmas");
        //turmasQuery.fromLocalDatastore();

        horarioObject = null;
        try {
            horarioObject = turmasQuery.get(objectID);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<ParseObject> listaTurmas = horarioObject.getList("turmas");

        ParseQuery<ParseObject> queryTurmas = ParseQuery.getQuery("Turma");
        queryTurmas.whereExists("semestre");
        queryTurmas.whereNotContainedIn("objectId", listaTurmas);
        //queryTurmas.fromLocalDatastore();
        queryTurmas.include("disciplina");

        listaDisciplinasNaoMatriculado = null;

        try {
            listaDisciplinasNaoMatriculado = queryTurmas.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String fimDeLinha = System.getProperty("line.separator");
        final ArrayList listaNomesDisciplinasNaoMatriculado = new ArrayList();
        for(ParseObject turma: listaDisciplinasNaoMatriculado) {
            ParseObject disciplina = turma.getParseObject("disciplina");
            listaNomesDisciplinasNaoMatriculado.add(disciplina.getString("DID") + " " + disciplina.getString("name"));// + fimDeLinha +
                   // turma.getString("codigoTurma") + "/ Professor" + turma.getString("professor") );
        }

        ArrayAdapter<ArrayList> meuAdapter = new ArrayAdapter<ArrayList>(this, android.R.layout.simple_expandable_list_item_1, listaNomesDisciplinasNaoMatriculado);

        turmasListView.setAdapter(meuAdapter);

        turmasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ParseObject disciplina = listaDisciplinasNaoMatriculado.get(i).getParseObject("disciplina");
                String teste = disciplina.getString("DID");
                Toast.makeText(adapterView.getContext(), "teste = " + teste, 5000).show();

                turmaSelecionada = (ParseObject) listaDisciplinasNaoMatriculado.get(i);
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ParseObject> listaTurmas= getHorarioObject().getList("turmas");

                listaTurmas.add(turmaSelecionada);

                horarioObject.put("turmas", listaTurmas);
                try {
                    horarioObject.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                finish();
            }
        });

        //turmasListView.setEnabled(false);

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        //Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        //Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        //Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //Log.d(TAG, "onDestroy()");
    }
}
