package com.rafael.gradeweb;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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
    private Spinner listaDisciplinasSpinner;
    private CustomSpinnerAdapterDisciplinas mSAd;


    private final String disciplinaLabel = "Disciplina";

    public FragmentoDetalhesDisciplina() {

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        final View viewRaiz = inflater.inflate(R.layout.layout_fragmento_detalhes_disciplina, container, false);

        listaDisciplinasSpinner = (Spinner) viewRaiz.findViewById(R.id.lista_disciplina_spinner);

        mSAd = new CustomSpinnerAdapterDisciplinas(getActivity());
        listaDisciplinasSpinner.setAdapter(mSAd);
        listaDisciplinasSpinner.setSelection(0);
        mSAd.setPaginationEnabled(false);

       listaDisciplinasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ParseObject disc = (ParseObject) adapterView.getSelectedItem();
                final String fimDeLinha = System.getProperty("line.separator");

                Log.d("Spinner", "Item selected = " + disc.getString("DID"));

                detalhesTextView = (TextView) viewRaiz.findViewById(R.id.detalhesTextView);

                detalhesTextView.setText(disc.getString("DID") + " " + disc.getString("name") +
                                         fimDeLinha + fimDeLinha + disc.getString("description"));

                ScrollView sc = (ScrollView) viewRaiz.findViewById(R.id.scrollView);

                sc.pageScroll(View.FOCUS_UP);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("Spinner", "nada selecionado");
            }
        });

        return viewRaiz;
    }

}
