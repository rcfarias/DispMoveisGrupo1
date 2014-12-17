package com.rafael.gradeweb;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class FragmentSemesterList extends Fragment {

    private GradeWEBApplication mApp;

    private String[] discStrList;

    private ArrayList nomeTodasDisciplinas;
    private ArrayList<String> codigoTodasDisciplinas;

    private ListView semestresListView;

    public FragmentSemesterList(){

    }

    @Override
    public View onCreateView( LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {

        mApp = GradeWEBApplication.getInstance();
        //ParseUser usuario = mApp.getUsuario();

        View viewRaiz = inflater.inflate(R.layout.layout_fragmento_lista_semesteres, container, false);

        semestresListView = (ListView) viewRaiz.findViewById(R.id.semestres_list_view);


        ParseQuery<ParseObject> turmaQuery = ParseQuery.getQuery("Horario");
        turmaQuery.whereEqualTo("usuario", mApp.getUsuario());
        turmaQuery.whereEqualTo("semestre", getArguments().getString("SEMESTRE"));
        turmaQuery.include("turmas");
        turmaQuery.include("disciplina");

        List<ParseObject> disciplinaObjects = null;
        try {
            disciplinaObjects = turmaQuery.find();
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("FragmentSemestre", "ERROR!! exception happened 1");
        }

        /*
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Turma");
        ParseObject turma1 = null;
        try {
            turma1 = query1.get("142K4locPn");
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("FragmentSemestre", "ERROR!! exception happened 2");
        }

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Turma");
        ParseObject turma2 = null;
        try {
            turma2 = query2.get("Um6iniFQ9M");
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("FragmentSemestre", "ERROR!! exception happened 3");
        }

        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("Turma");
        ParseObject turma3 = null;
        try {
            turma3 = query3.get("2o4KREbhTQ");
        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("FragmentSemestre", "ERROR!! exception happened 4");
        }

        disciplinaObjects.get(0).put("turmas", Arrays.asList( turma1, turma2, turma3));
        //disciplinaObjects.get(0).put("turmas", turma2);
        //disciplinaObjects.get(0).put("turmas", turma3);
        disciplinaObjects.get(0).saveInBackground();
        */

        List<ParseObject> listaDeTurmas = disciplinaObjects.get(0).getList("turmas");

        //ParseQuery<ParseObject> queryListaTurmaDisciplina = ParseQuery.getQuery("Turma");

        //queryListaTurmaDisciplina.include("disciplina");


        ArrayList listaIds = new ArrayList();
        for(int l = 0; l < listaDeTurmas.size(); l ++) {
            listaIds.add(listaDeTurmas.get(l).getObjectId());
        }


        ArrayList listaDisciplinas = new ArrayList();

        for(int j = 0; j < listaDeTurmas.size(); j++) {
            ParseObject cada = listaDeTurmas.get(j);
            listaDisciplinas.add(cada.getString("DID") + " - " + cada.getString("name") + " Turma" +cada.getString("codigoTurma")+ "/" + cada.getString("professor"));
        }


        ArrayAdapter<ArrayList> meuAdapter = new ArrayAdapter<ArrayList>(this.getActivity(),
                                                                         android.R.layout.simple_expandable_list_item_1,
                                                                         listaDisciplinas);

        semestresListView.setAdapter(meuAdapter);




        /*

        ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");
        query.selectKeys(Arrays.asList("playerName", "score"));;
        List<ParseObject> results = query.find();

         */

        //query.g

        /*
        query.getInBackground("gTd01Nx6lR", new GetCallback<ParseObject>() {
            public void done(ParseObject myObject, ParseException e) {
                if (e == null) {
                    // object will be your game score
                    ArrayList asDisc = new ArrayList();
                    asDisc = (ArrayList) myObject.get("Discplinas");

                    asDisc.add("MATA62");
                    asDisc.add("MATC89");
                    asDisc.add("ECO151");

                    myObject.put("Discplinas", asDisc);
                    myObject.saveInBackground();
                }
                else {
                    // something went wrong
                    Log.d("ParseQuery callBack()", "an exception just happened ... SHIT");
                }
            }
        });
        */

        /*
        ParseQuery<ParseObject> tquery = ParseQuery.getQuery("Turma");

        tquery.whereEqualTo("semestre", "2014-2");

        ParseObject turma = null;
        try {
            turma = tquery.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ParseObject horario = new ParseObject("Horario");

        horario.put("usuario", (ParseUser) mApp.getUsuario());
        horario.put("semestre", "2014-2");
        horario.put("turmas", Arrays.asList(turma));

        horario.saveInBackground();
        */
        //ParseObject semestre = new ParseObject("Horario");
        //semestre.put("semestre", getArguments().getString("SEMESTRE"));
        //semestre.put("usuario", "Teste");
        //semestre.put("Discplinas", discplinas);
        //gameScore.put("cheatMode", false);
        //semestre.saveInBackground();

        return viewRaiz;
    }

    private void populateListView() {


    }

}


/*
*
@Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
        "Linux", "OS/2" };
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
        android.R.layout.simple_list_item_1, values);
    setListAdapter(adapter);
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    // do something with the data
  }



*
*
* */