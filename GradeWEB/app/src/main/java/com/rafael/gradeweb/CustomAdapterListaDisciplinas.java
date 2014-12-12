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

        TextView horaioTextView = (TextView) v.findViewById(R.id.campo_dias);
        String horString = object.getString("horarioAulas");
        horaioTextView.setText(horString);

        TextView professorTextView = (TextView) v.findViewById(R.id.campo_professor);
        String pString = object.getString("professor");
        professorTextView.setText(pString);


        // Add a reminder of how long this item has been outstanding
        //TextView timestampView = (TextView) v.findViewById(R.id.timestamp);
        //timestampView.setText(object.getUpdatedAt().toString());

        return v;
    }

}
