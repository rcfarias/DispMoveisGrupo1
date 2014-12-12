package com.rafael.gradeweb;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomAdapterListaDisciplinas extends ParseQueryAdapter {
    private ParseObject horario;

    public CustomAdapterListaDisciplinas(Context context,  final ArrayList listaIDS) {

        super(context,new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("Turma");
                query.whereContainedIn("objectId", listaIDS);
                query.include("disciplina");
                query.orderByAscending("disciplina");
                return query;
            }
        });
    }

    public CustomAdapterListaDisciplinas(Context context,  final List listaTurmas, ParseObject horario) {

        super(context,new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("Turma");
                query.whereContainedIn("objectId", listaTurmas);
                query.include("disciplina");
                query.orderByAscending("DID");
                return query;
            }
        });
    }

    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.custom_item_lista_disciplina, null);
        }

        super.getItemView(object, v, parent);

        //ParseObject disciplina = object.getParseObject("disciplina");

        // Add the title view
        TextView didTextView = (TextView) v.findViewById(R.id.campo_did);
        didTextView.setText(object.getParseObject("disciplina").getString("DID"));
        didTextView.setTypeface(didTextView.getTypeface(), Typeface.BOLD);

        TextView nameTextView = (TextView) v.findViewById(R.id.campo_nome_disciplina);
        nameTextView.setText(object.getParseObject("disciplina").getString("name"));
        nameTextView.setTypeface(nameTextView.getTypeface(), Typeface.BOLD);

        TextView codigoTurmaTextView = (TextView) v.findViewById(R.id.campo_codigo_turma);
        codigoTurmaTextView.setText(object.getString("codigoTurma"));

        String horario = object.getString("horarioAulas");

        String dias[] = horario.split("/");

        String diasFormatados = "";
        String fimDeLinha = System.getProperty("line.separator");

        for (int j = 0; j < dias.length; j++) {
            diasFormatados += dias[j] + fimDeLinha;
        }

        TextView horarioTextView = (TextView) v.findViewById(R.id.campo_dias_horario);
        horarioTextView.setText(diasFormatados);

        TextView professorTextView = (TextView) v.findViewById(R.id.campo_professor);
        professorTextView.setText(object.getString("professor"));

        return v;
    }


}
