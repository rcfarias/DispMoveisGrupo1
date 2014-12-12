package com.rafael.gradeweb;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rafael on 11/23/2014.
 */
public class FragmentSemesterList extends Fragment {

    private GradeWEBApplication mApp;

    private String[] discStrList;

    private ArrayList nomeTodasDisciplinas;
    private ArrayList<String> codigoTodasDisciplinas;

    private ListView essaListView;

    public FragmentSemesterList(){

    }

    @Override
    public View onCreateView( LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {

        mApp = GradeWEBApplication.getInstance();
        //ParseUser usuario = mApp.getUsuario();


        View viewRaiz = inflater.inflate(R.layout.semester_main_list_fragment, container, false);


        essaListView = (ListView) viewRaiz.findViewById(R.id.semestre_list_view);


        // uncomment to get it Working again
        //discStrList = getResources().getStringArray(R.array.disc_listView);
        //ArrayAdapter<String> meuAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_expandable_list_item_1, discStrList);
        //essaListView.setAdapter(meuAdapter);


        nomeTodasDisciplinas = new ArrayList();
        codigoTodasDisciplinas = new ArrayList<String>();


        //populateListView();

        //create list of items
        //String[] myItems = { "2009-1", "2009-2", "2010-1", "2010-2", "2011-1", "2011-2",
        //        "2012-2", "2013-1", "2013-2", "2014-1", "2014-2"};

        // build adapter
       // ArrayAdapter<String> myAdapter;
       // myAdapter = new ArrayAdapter<String>(
       //         getActivity(),
       //         R.layout.semester_item,
       //         myItems);

        //configure the listView
        //listView = (ListView) view.findViewById(R.id.semestre_list_view);
        //listView.setAdapter(myAdapter);

        //ArrayList<String> discplinas = new ArrayList<String>();

        //discplinas.add("ENGG55");
        //discplinas.add("ENGG54");
        //discplinas.add("ENGG56");
        //discplinas.add("MATA58");
        //discplinas.add("ENGG01");




        ParseQuery<ParseObject> query = ParseQuery.getQuery("Disciplina");  //("Horario");

        query.whereExists("DID");

        query.selectKeys(Arrays.asList("DID", "name"));

        //ArrayList<ParseObject> todasAsDiciplinas =

        /*
         query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e == null) {
                    //success
                    /*
                    while(parseObjects.iterator().hasNext()){
                        nomeTodasDisciplinas.add(parseObjects.get("name"));
                        codigoTodasDisciplinas.add();
                    }
                    ///

                    int j = 0;
                    for(j = 0; j < parseObjects.size(); j++) {
                        ParseObject cada = parseObjects.get(j);
                        nomeTodasDisciplinas.add((String) cada.get("DID"));
                        codigoTodasDisciplinas.add( (String) cada.get("name") );
                    }


                    ArrayAdapter<ArrayList> meuAdapter = new ArrayAdapter<ArrayList>(this.getActivity(), android.R.layout.simple_expandable_list_item_1, nomeTodasDisciplinas);

                    essaListView.setAdapter(meuAdapter);

                }
                else {
                    //failure
                }
            }
        });*/

        List<ParseObject> parseObjects = null;
        try {
            parseObjects = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int j = 0;
        for(j = 0; j < parseObjects.size(); j++) {
            ParseObject cada = parseObjects.get(j);
            nomeTodasDisciplinas.add((String) cada.get("DID"));
            codigoTodasDisciplinas.add( (String) cada.get("name") );
        }


        ArrayAdapter<ArrayList> meuAdapter = new ArrayAdapter<ArrayList>(this.getActivity(), android.R.layout.simple_expandable_list_item_1, nomeTodasDisciplinas);

        essaListView.setAdapter(meuAdapter);




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