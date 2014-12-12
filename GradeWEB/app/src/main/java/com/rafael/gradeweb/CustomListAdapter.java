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
public class CustomListAdapter extends ParseQueryAdapter {
    public CustomListAdapter(Context context) {
        // Use the QueryFactory to construct a PQA that will only show
        // Todos marked as high-pri
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("Todo");
                query.whereEqualTo("highPri", true);
                return query;
            }
        });
    }

    public CustomListAdapter(Context context, final String className, final String attribute1) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            final String classe = className;
            final String at1 = attribute1;

            GradeWEBApplication m = GradeWEBApplication.getInstance();

            public ParseQuery create() {
                ParseQuery query = new ParseQuery("Horario");
                query.whereEqualTo("usuario", m.getUsuario());
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


/*
        // Add and download the image
        ParseImageView todoImage = (ParseImageView) v.findViewById(R.id.icon);
        ParseFile imageFile = object.getParseFile("image");
        if (imageFile != null) {
            todoImage.setParseFile(imageFile);
            todoImage.loadInBackground();
        }
*/
        // Add the title view
        TextView titleTextView = (TextView) v.findViewById(R.id.text1);
        titleTextView.setText(object.getString("semestre"));

        // Add a reminder of how long this item has been outstanding
        TextView timestampView = (TextView) v.findViewById(R.id.timestamp);
        timestampView.setText(object.getUpdatedAt().toString());

        return v;
    }

}
