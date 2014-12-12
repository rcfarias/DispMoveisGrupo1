package com.rafael.gradeweb;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by Rafael Farias on 04/12/2014.
 */
public class CustomSpinnerAdapterDisciplinas extends ParseQueryAdapter {

    public CustomSpinnerAdapterDisciplinas(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("Disciplina");
                query.whereExists("DID");
                query.whereExists("name");
                query.orderByAscending("DID");
                return query;
            }

        });
    }

/*
    public View getDropDownView(ParseObject object, View view, ViewGroup parent) {
        return getCustomView(object, view, parent);
    }

    public View getView(ParseObject object, View view, ViewGroup parent) {
        return getCustomView(object, view, parent);
    }
*/



    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.custom_item_disciplinas_spinner, null);
        }

        super.getItemView(object, v, parent);

        // Add the title view
        TextView didTextView = (TextView) v.findViewById(R.id.did_text_view);
        didTextView.setText(object.getString("DID"));
        didTextView.setTypeface(didTextView.getTypeface(), Typeface.BOLD);

        TextView nameTextView = (TextView) v.findViewById(R.id.nome_disciplina_text_view);
        nameTextView.setText(object.getString("name"));
        nameTextView.setTypeface(nameTextView.getTypeface(), Typeface.BOLD);

        return v;
    }

}