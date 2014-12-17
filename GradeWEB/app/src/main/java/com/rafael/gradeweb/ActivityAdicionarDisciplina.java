package com.rafael.gradeweb;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Rafael Farias on 28/11/2014.
 */
public class ActivityAdicionarDisciplina extends Activity{

    private ArrayList listaIdsTurmas;
    private String semestre;
    private ListView turmasListView;
    private Spinner disciplinasSpinner;
    private CustomAdapterListaTurmas mAd;
    private CustomSpinnerAdapterDisciplinas mSAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listaIdsTurmas = new ArrayList();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String objectID = (String) extras.getString("EXTRA_OBJECT_ID");

            listaIdsTurmas = extras.getStringArrayList("turmas_ids");
            semestre = extras.getString("semestre");
            //Toast.makeText(this, "semestre = " + semestre, 5000).show();
        }
        //else
        //    Toast.makeText(this, "extras eh NULL", 5000).show();

        final String sem = semestre;

        setContentView(R.layout.layout_activity_adicionar_turma);

        turmasListView = (ListView) findViewById(R.id.turmas_list_view);

        //updateSpinnerAdater();

        disciplinasSpinner = (Spinner) findViewById(R.id.diciplina_spinner);
        mSAd = new CustomSpinnerAdapterDisciplinas(this);
        disciplinasSpinner.setAdapter(mSAd);
        disciplinasSpinner.setSelection(0);
        mSAd.setPaginationEnabled(false);
        //mSAd.loadObjects();

        disciplinasSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ParseObject disc = (ParseObject) adapterView.getSelectedItem();
                Log.d("Spinner", "Item selected = " + disc.getString("DID"));

                mAd = new CustomAdapterListaTurmas(getApplicationContext(), disc, semestre);
                turmasListView.setAdapter(mAd);
                mAd.loadObjects();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("Spinner", "nada selecionado");
            }
        });

        turmasListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                ParseObject turma = (ParseObject) adapter.getItemAtPosition(position);

                adicionarTurma(turma);
/*
                List<ParseObject> listaTurmas = horario.get(0).getList("turmas");
                listaTurmas.add(turma);
                horario.get(0).put("turmas", listaTurmas);
                horario.get(0).saveInBackground();

                GradeWEBApplication.getInstance().setHorario(horario.get(0));

                finish();
*/
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

    public void updateSpinnerAdater() {
        List<ParseObject> turmas = GradeWEBApplication.getInstance().getHorario().getList("turmas");
    }

    private void adicionarTurma(ParseObject turma) {

        Log.d("turma -> " , turma.getParseObject("disciplina").getString("DID"));

        ParseUser u = GradeWEBApplication.getInstance().getUsuario();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Horario");
        query.whereEqualTo("usuario", u);
        query.whereEqualTo("semestre", semestre);
        query.include("turmas");
        query.include("turmas.disciplina");

        List<ParseObject> horario = new ArrayList<ParseObject>();
        try {
            horario = query.find();
            Log.d("Horario ID = " + semestre, "aqui" + horario.get(0).getObjectId());

            List<ParseObject> listaTurmas = horario.get(0).getList("turmas");

            verificarEAdicionarTurma(turma, horario.get(0), listaTurmas);

            finish();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void verificarEAdicionarTurma(ParseObject turma, ParseObject horario, List<ParseObject> listaTurmas) {

        boolean turmaJaAdicionada = false;
        boolean disciplinaJaAdicionada = false;

        for(ParseObject cada : listaTurmas) {
            if(cada.getObjectId().equals(turma.getObjectId()))
                turmaJaAdicionada = true;
            if( cada.getParseObject("disciplina").getString("DID").equals(turma.getParseObject("disciplina").getString("DID"))) {
                disciplinaJaAdicionada = true;
            }
            testaChoqueHorarios(cada, turma);
        }

        if(disciplinaJaAdicionada)
            Toast.makeText(getApplicationContext(), "Disciplina já cadastrada no horário", 5000).show();
        else if(turmaJaAdicionada)
            Toast.makeText(getApplicationContext(), "Turma já cadastrada no horário", 5000).show();
        else {
            listaTurmas.add(turma);
            horario.put("turmas", listaTurmas);
            horario.saveInBackground();

            GradeWEBApplication.getInstance().setHorario(horario);

            Toast.makeText(getApplicationContext(), "Disciplina adicionada com sucesso", 5000).show();
        }
    }

    private boolean testaChoqueHorarios(ParseObject turmaExistente, ParseObject turmaNova) {

        Date today;
        String result;
        SimpleDateFormat formatter;
        SimpleDateFormat formatoDia = new SimpleDateFormat("ccc", new Locale("pt", "BR"));

        formatter = new SimpleDateFormat("ccc HH:mm", new Locale("pt", "BR"));

        //Log.d("testando choque ", "aqui");


        String horario1 = turmaExistente.getString("horarioAulas");
        String horario2 = turmaNova.getString("horarioAulas");

        String horarios1[] = horario1.split("/"); // n x "DDD HH:mm-HH:mm"
        String horarios2[] = horario2.split("/"); // n x "DDD HH:mm-HH:mm"

        List<String[]> diasEHoras1 = new ArrayList<String[]>();
        List<String[]> diasEHoras2 = new ArrayList<String[]>();

        //Log.d("testando choque ", "aqui1");

        for(int k = 0; k < horarios1.length; k++) {

            diasEHoras1.add(horarios1[k].split(" ")); // n x 2  "DDD" +  "HH:mm-HH:mm"

            Log.d("testando choque ", horarios1[k]);
        }

        //Log.d("testando choque ", "aqui2");

        for(int k = 0; k < horarios2.length; k++) {

            diasEHoras2.add(horarios2[k].split(" ")); // n x 2  "DDD" +  "HH:mm-HH:mm"

            Log.d("testando choque ", horarios2[k]);
        }

        //Log.d("testando choque ", "aqui3");

        // n "DDD"  + n x 2  "HH:mm"


        List<String[]> horas1 = new ArrayList<String[]>();
        List<String[]> horas2 = new ArrayList<String[]>();

        for(String[] cada1 : diasEHoras1) {

            horas1.add(cada1[1].split("-"));
        }

        for(String[] cada2 : diasEHoras1) {

            horas2.add(cada2[1].split("-"));
        }

        List<Date> horariosFormatadosExistente = new ArrayList<Date>();
        List<Date> horariosFormatadosNova = new ArrayList<Date>();

        for(String[] cadaDia1 : diasEHoras1) {
            for(String[] cadaHora1 : horas1) {
                for(int k = 0; k < cadaHora1.length; k++) {

                    try {
                        horariosFormatadosExistente.add(formatter.parse(cadaDia1[0] + " " + cadaHora1[k] ) );
                        System.out.println("\n" + formatter.parse(cadaDia1[0] + " " + cadaHora1[k])  + "\n");
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        for(String[] cadaDia2 : diasEHoras2) {
            for(String[] cadaHora2 : horas2) {
                for(int k = 0; k < cadaHora2.length; k++) {

                    try {
                        horariosFormatadosNova.add(formatter.parse(cadaDia2[0] + " " + cadaHora2[k] ) );
                        System.out.println("\n" + formatter.parse(cadaDia2[0] + " " + cadaHora2[k])  + "\n");
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //for(Date cada : horariosFormatadosExistente ) {
        //  for (Date cadaInner : horariosFormatadosNova) {
        for(int j = 0; j < horariosFormatadosExistente.size(); j+=2 ) {
            for(int l = 0; l < horariosFormatadosNova.size(); l+=2 ) {

                if(formatoDia.format(horariosFormatadosExistente.get(j)).equals(formatoDia.format(horariosFormatadosNova.get(l)) ) ) {

                    System.out.println("MESMO DIA!! VOU COMPARAR SE CHOCA \n" + formatter.format(horariosFormatadosExistente.get(j)) + " com " + formatter.format(horariosFormatadosNova.get(l)) );

                    if( horariosFormatadosNova.get(l).after(horariosFormatadosExistente.get(j)) && horariosFormatadosNova.get(l).before(horariosFormatadosExistente.get(j+1))) {

                        System.out.println("DEU choque de horario : " + horariosFormatadosNova.get(l) + " às " + horariosFormatadosNova.get(l+1)
                                           + "\ncom " + horariosFormatadosExistente.get(j) + " às " + horariosFormatadosExistente.get(j+1) + "\n");
                    }

                }
                //if(cada.before(cadaInner))
                //    System.out.println(cada + " before " + cadaInner);
                //else
                //    //System.out.println("Test: DATAS NÃO SAO IGUAIS ");
                //    System.out.println(cadaInner + " before " + cada);
            }
        }


        /*
        for(String[] cada1 : diasEHoras1) {

            for(String[] cada2 : diasEHoras2) {

                if(cada2[0].equals(cada1[0])) {
                    Log.d("testando se dias iguais ", "positivo");

                    String horasMinutos[] = cada2[1].split(":");

                    //DateFormat df = new SimpleDateFormat()

                }
                else {
                    Log.d("testando se dias iguais ", "negativo");
                }
            }
        }
        */
        //Log.d("testando choque ", "aqui4");

        String dias1[] = horario1.split("/");
        String dias2[] = horario2.split("/");

        String diasFormatados = "";
        String fimDeLinha = System.getProperty("line.separator");

        //for (int j = 0; j < dias.length; j++) {
        ///    diasFormatados += dias[j] + fimDeLinha;
        //}



        today = new Date();
        result = formatter.format(today);
        System.out.println("Locale: " + Locale.getDefault().toString());
        System.out.println("Result: " + result);

        Date teste = null;
        Date teste2 = null;

        try {
            teste = formatter.parse("Ter 20:30");
            teste2 = formatter.parse("Ter 21:30");
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        if(teste.before(teste2))
            System.out.println(teste + " before " + teste2);
        else
            //System.out.println("Test: DATAS NÃO SAO IGUAIS ");
            System.out.println(teste2 + " before " + teste
            );


        String test = formatter.format(teste);
        String test2 = formatter.format(teste2);
        System.out.println("Test: " + test + "Test2: " + test2);
        return true;
    }
}
