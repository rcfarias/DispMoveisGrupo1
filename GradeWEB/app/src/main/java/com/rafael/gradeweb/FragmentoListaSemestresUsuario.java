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

    private GradeWEBApplication myApp;

    private ListView listaSemestresListView;

    //private String[] listaSemestresString ;

    private Context context;

    private List semestresList;

    private FragmentManager frgManager;
    private Fragment fragment;

    public FragmentoListaSemestresUsuario() { }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        myApp = GradeWEBApplication.getInstance();
/*
        ParseQuery<ParseObject> horarioQuery = ParseQuery.getQuery("Horario");
        horarioQuery.selectKeys(Arrays.asList("semestre", "objectId"));
        horarioQuery.include("turmas");
        horarioQuery.include("turmas.disciplina");
        horarioQuery.orderByAscending("semestre");
        horarioQuery.fromLocalDatastore();

        List<ParseObject> listaHorarioObjects = null;
        try {
            listaHorarioObjects = horarioQuery.find();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        final List<ParseObject> listaHorarioObjectsFinal = listaHorarioObjects;

        semestresList = new ArrayList();

        for(ParseObject cada : listaHorarioObjects) {

            semestresList.add((String) cada.get("semestre"));
        }
*/
        View rootView = inflater.inflate(R.layout.layout_fragmento_lista_semesteres, container, false);

        listaSemestresListView = (ListView) rootView.findViewById(R.id.semestres_list_view);

        CustomListAdapter myAdapter = new CustomListAdapter(context, "Horario", "Svreg6Wq04");//myApp.getUsuario().getObjectId());



        listaSemestresListView.setAdapter(myAdapter);
        myAdapter.loadObjects();

/*
        ArrayAdapter<List> semestreAdapter = new ArrayAdapter<List>(this.getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                semestresList);
        listaSemestresListView.setAdapter(semestreAdapter);
*/
        listaSemestresListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), "You clicked" + (String) semestresList.get(position), 5000).show();

                fragment = new FragmentoListaDisciplinasSemestreSelecionado();
                Bundle args = new Bundle();
                /*
                String horarioOjectId = listaHorarioObjectsFinal.get(position).getObjectId();

                args.putString("SEMESTRE",(String) semestresList.get(position));
                args.putString("OBJECT_ID", horarioOjectId);

                //Toast.makeText(getActivity(), "You clicked " + (String) semestresList.get(position) + " id == " + horarioOjectId, 5000).show();
                */
                fragment.setArguments(args);
                frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();


            }
        });



        return rootView;
    } // end of onCreateView

}
