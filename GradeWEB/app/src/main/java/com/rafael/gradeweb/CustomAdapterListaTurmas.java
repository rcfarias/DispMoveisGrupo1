package com.rafael.gradeweb;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;

/**
 * Created by Rafael Farias on 03/12/2014.
 */
public class CustomAdapterListaTurmas extends ParseQueryAdapter {

    public CustomAdapterListaTurmas(Context context, final ArrayList listaDisciplas) {

        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("Turma");
                query.whereNotContainedIn("objectId", listaDisciplas);
                query.include("disciplina");
                //query.orderByDescending("disciplina.DID");
                //query.addAscendingOrder("codigoTurma");
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
        didTextView.setTypeface(didTextView.getTypeface(), Typeface.BOLD);

        TextView nameTextView = (TextView) v.findViewById(R.id.campo_nome_disciplina);
        nameTextView.setText(object.getParseObject("disciplina").getString("name"));
        nameTextView.setTypeface(nameTextView.getTypeface(), Typeface.BOLD);

        TextView codigoTurmaTextView = (TextView) v.findViewById(R.id.campo_codigo_turma);
        codigoTurmaTextView.setText(object.getString("codigoTurma"));

        TextView horarioTextView = (TextView) v.findViewById(R.id.campo_dias_horario);
        horarioTextView.setText(object.getString("horarioAulas"));

        TextView professorTextView = (TextView) v.findViewById(R.id.campo_professor);
        professorTextView.setText(object.getString("professor"));

        //Log.d("preenchendo listView", object.getParseObject("disciplina").getString("DID"));

        // Add a reminder of how long this item has been outstanding
        //TextView timestampView = (TextView) v.findViewById(R.id.timestamp);
        //timestampView.setText(object.getUpdatedAt().toString());

        return v;
    }
}
