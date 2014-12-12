package com.rafael.gradeweb;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael Farias on 28/11/2014.
 */
public class ActivityAdicionarDisciplina extends Activity{

    private ArrayList listaIdsTurmas;
    private String semestre;
    private ListView turmasListView;
    private CustomAdapterListaTurmas mAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listaIdsTurmas = new ArrayList();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String objectID = (String) extras.getString("EXTRA_OBJECT_ID");

            listaIdsTurmas = extras.getStringArrayList("turmas_ids");
            semestre = extras.getString("semestre");
            Toast.makeText(this, "semestre = " + semestre, 5000).show();
        }
        //else
        //    Toast.makeText(this, "extras eh NULL", 5000).show();

        setContentView(R.layout.layout_activity_adicionar_turma);

        turmasListView = (ListView) findViewById(R.id.turmas_list_view);

        mAd = new CustomAdapterListaTurmas(this, listaIdsTurmas);

        turmasListView.setAdapter(mAd);
        mAd.loadObjects();

        turmasListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                ParseObject turma = (ParseObject) adapter.getItemAtPosition(position);

                Log.d("turma -> " , turma.getParseObject("disciplina").getString("DID"));

                ParseUser u = GradeWEBApplication.getInstance().getUsuario();

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Horario");
                query.whereEqualTo("usuario", u);
                query.whereEqualTo("semestre", semestre);

                List<ParseObject> horario = new ArrayList<ParseObject>();
                try {
                    horario = query.find();
                    Log.d("Horario ID = " + semestre, "aqui" + horario.get(0).getObjectId());

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                List<ParseObject> listaTurmas = horario.get(0).getList("turmas");
                listaTurmas.add(turma);
                horario.get(0).put("turmas", listaTurmas);
                horario.get(0).saveInBackground();

                GradeWEBApplication.getInstance().setHorario(horario.get(0));

                finish();
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        //Log.d(TAG, "onRestart()");

    }

    @Override
    protected void onResume(){
        super.onResume();
        //Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        //Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        //Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //Log.d(TAG, "onDestroy()");
    }
}
