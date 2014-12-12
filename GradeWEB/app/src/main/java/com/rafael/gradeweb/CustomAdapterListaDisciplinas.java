package com.rafael.gradeweb;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

public class CustomAdapterListaDisciplinas extends ParseQueryAdapter {
    private ParseObject horario;

    public CustomAdapterListaDisciplinas(Context context, String className, final String DID) {

        super(context,new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery innerQuery = new ParseQuery("Disciplina");
                innerQuery.whereEqualTo("DID", DID);
                ParseQuery query = new ParseQuery("Turma");
                query.whereMatchesQuery("disciplina", innerQuery);
                query.include("disciplina");
                return query;
            }
        });
    }

    public CustomAdapterListaDisciplinas(Context context, ParseObject horario) {

        super(context,new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery innerQuery = new ParseQuery("Disciplina");
                innerQuery.whereEqualTo("DID", DID);
                ParseQuery query = new ParseQuery("Turma");
                query.whereMatchesQuery("disciplina", innerQuery);
                query.include("disciplina");
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

        // Add the title view
        TextView didTextView = (TextView) v.findViewById(R.id.campo_did);
        didTextView.setText(object.getParseObject("disciplina").getString("DID"));

        TextView nameTextView = (TextView) v.findViewById(R.id.campo_nome_disciplina);
        nameTextView.setText(object.getParseObject("disciplina").getString("name"));

        TextView codigoTurmaTextView = (TextView) v.findViewById(R.id.campo_codigo_turma);
        codigoTurmaTextView.setText(object.getString("codigoTurma"));

        TextView horarioTextView = (TextView) v.findViewById(R.id.campo_dias_horario);
        horarioTextView.setText(object.getString("horarioAulas"));

        TextView professorTextView = (TextView) v.findViewById(R.id.campo_professor);
        professorTextView.setText(object.getString("professor"));


        // Add a reminder of how long this item has been outstanding
        //TextView timestampView = (TextView) v.findViewById(R.id.timestamp);
        //timestampView.setText(object.getUpdatedAt().toString());

        return v;
    }

}
