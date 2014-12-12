package com.rafael.gradeweb;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FragmentoDetalhesDisciplina extends Fragment {

    private TextView detalhesTextView;
    private ScrollView minhaScroolView;
    private ListView disciplinasListView;


    private final String disciplinaLabel = "Disciplina";

    public FragmentoDetalhesDisciplina() {

    }

    @Override
    public View onCreateView( LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstance) {

        final View viewRaiz = inflater.inflate(R.layout.layout_fragmento_detalhes_disciplina, container, false);

        disciplinasListView = (ListView) viewRaiz.findViewById(R.id.listViewDisciplinas);

        ParseQuery<ParseObject> listaDisciplinasQuery = ParseQuery.getQuery("Disciplina");
        listaDisciplinasQuery.selectKeys(Arrays.asList("DID", "name"));
        listaDisciplinasQuery.orderByAscending("DID");
        listaDisciplinasQuery.fromLocalDatastore();

        List<ParseObject> listaDisciplinasObjects = null;
        try {
            listaDisciplinasObjects = listaDisciplinasQuery.find();
        }
        catch ( ParseException e) {
            e.printStackTrace();
        }

        final ArrayList listaDisciplinasNomes = new ArrayList();

        for(int j = 0; j < listaDisciplinasObjects.size(); j++) {
            listaDisciplinasNomes.add(listaDisciplinasObjects.get(j).getString("DID") + " - " + listaDisciplinasObjects.get(j).getString("name"));
        }

        ArrayAdapter<ArrayList> adapter = new ArrayAdapter<ArrayList>(this.getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                listaDisciplinasNomes);

        disciplinasListView.setAdapter(adapter);

        disciplinasListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                detalhesTextView = (TextView) viewRaiz.findViewById(R.id.detalhesTextView);

                detalhesTextView.setText((String) listaDisciplinasNomes.get(position));


                String nome = (String) listaDisciplinasNomes.get(position);
                String[] parts = nome.split(" - ");

                ParseQuery<ParseObject> disciplinaQuery = ParseQuery.getQuery("Disciplina");
                disciplinaQuery.whereEqualTo("DID",(String) parts[0]);
                disciplinaQuery.include("Unidade");
                disciplinaQuery.selectKeys(Arrays.asList("description"));
                disciplinaQuery.fromLocalDatastore();

                //detalhesTextView.setText((String)  parts[0]);

                List<ParseObject> disciplinaDescription = null;
                try {
                    disciplinaDescription = disciplinaQuery.find();
                } catch (ParseException e) {
                    e.printStackTrace();
                    detalhesTextView.setText("Deu EXCEPTION");
                }

                ParseObject obj = disciplinaDescription.get(0);

                ParseObject uni = obj.getParseObject("Unidade");

                final String fimDeLinha = System.getProperty("line.separator");

                detalhesTextView.setText(nome + fimDeLinha + uni.getString("name") + fimDeLinha + fimDeLinha  + (String) obj.getString("description"));
                detalhesTextView.setTextSize((float) 20.5);
                //detalhesTextView.setText((String) disciplinaDescription.get(0).getString("descriprion"));


            }
        });

        return viewRaiz;
    }

}
