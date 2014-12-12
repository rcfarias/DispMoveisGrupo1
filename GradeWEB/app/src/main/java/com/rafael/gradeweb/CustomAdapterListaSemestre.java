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

/**
 * Created by Rafael on 11/29/2014.
 */
public class CustomAdapterListaSemestre extends ParseQueryAdapter {
    public CustomAdapterListaSemestre(Context context) {
        // Use the QueryFactory to construct a PQA that will only show
        // Todos marked as high-pri
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("Horario");
                query.whereEqualTo("usuario", GradeWEBApplication.getInstance().getUsuario());
                query.include("turmas");
                query.include("turmas.disciplina");
                query.orderByAscending("semestre");
                return query;
            }
        });
    }

    public CustomAdapterListaSemestre(Context context, final String className, final String attribute1) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("Horario");
                query.whereEqualTo("usuario", GradeWEBApplication.getInstance().getUsuario());
                query.include("turmas");
                query.orderByAscending("semestre");
                return query;
            }
        });
    }


    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.custom_list_item, null);
        }

        super.getItemView(object, v, parent);

        // Add the title view
        TextView titleTextView = (TextView) v.findViewById(R.id.text1);
        titleTextView.setText(object.getString("semestre"));

        // Add a reminder of how long this item has been outstanding
        TextView timestampView = (TextView) v.findViewById(R.id.timestamp);
        timestampView.setText(object.getUpdatedAt().toString());

        return v;
    }

}
