package com.rafael.gradeweb;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class FragmentoListaSemestresUsuario extends Fragment {

    private ListView listaSemestresListView;
    private Button adicionarButton;

    private Context context;
    private FragmentManager frgManager;
    private FragmentoListaDisciplinasSemestreSelecionado fragment;

    public FragmentoListaSemestresUsuario() { }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_fragmento_lista_semesteres, container, false);

        listaSemestresListView = (ListView) rootView.findViewById(R.id.semestres_list_view);

        adicionarButton = (Button) rootView.findViewById(R.id.adicionar_semestre_button);

        updateAdapter();

        listaSemestresListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                //Toast.makeText(getActivity(), "You clicked" + (String) semestresList.get(position), 5000).show();

                fragment = new FragmentoListaDisciplinasSemestreSelecionado();
                Bundle args = new Bundle();

                ParseObject horario = (ParseObject) adapter.getItemAtPosition(position);

                List<ParseObject> listaDeTurmas = horario.getList("turmas");
                ArrayList listaIdsTurmas = new ArrayList();

                ordenaTurmasPorDID(listaDeTurmas);

                horario.put("turmas", listaDeTurmas);
                horario.saveInBackground();

                GradeWEBApplication.getInstance().setHorario(horario);

                Log.d("Ordenado ?", "veremos");
                for(ParseObject cadaTurma : listaDeTurmas) {
                    Log.d("Ordenado ?", cadaTurma.getParseObject("disciplina").getString("DID"));
                    listaIdsTurmas.add(cadaTurma.getObjectId());
                }

                Toast.makeText(getActivity(), "semestre selecionado: " + (String) horario.getString("semestre"), 5000).show();

                args.putString("semestre", horario.getString("semestre"));
                args.putStringArrayList("turmas_ids", listaIdsTurmas);


                fragment.setHorarioObject(horario);
                fragment.setArguments(args);
                frgManager = getFragmentManager();

                frgManager.beginTransaction()
                          //.add(fragment, "Detalha disciplinas do horario")
                          .replace(R.id.content_frame, fragment)
                          .addToBackStack("teste")
                          .commit();
                         //.replace(R.id.content_frame, fragment).commit();
            }
        });
/*
        listaSemestresListView.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                final ParseObject horario = (ParseObject) adapterView.getItemAtPosition(position);

                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("Isso removerá este horário")
                        .setTitle("Excluir o semestre selecionado?");

                // Add the buttons
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button

                        try {
                            horario.delete();
                            Toast.makeText(getActivity().getApplicationContext(), "Horário removido com sucesso", 5000).show();
                        } catch (ParseException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity().getApplicationContext(), "Não foi possível excluir! Tente novamente mais tarde!!", 5000).show();
                        }
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
            }
        });
*/

        adicionarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // Set an EditText view to get user input
                final EditText input = new EditText(getActivity());


                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("Informe o semestre a ser adicionado: (ex.: 2014.2)")
                        .setTitle("Adicionar semestre");

                builder.setView(input);

                // Add the buttons
                builder.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button

                        String semestre = input.getText().toString();
                        // Do something with value!

                        System.out.println("SEMESTRE INSERIDO = " + semestre + "XC");

                        //if(semestre.matches("20%02d.%01d")) { // true)


                            Log.d("positiveButton", "semestre informado = " + semestre);

                            ParseQuery semestreQuery = new ParseQuery("Semestre");
                            semestreQuery.whereEqualTo("codigoSemestre", semestre);

                            try {
                                ParseObject semestreObject = semestreQuery.getFirst();

                                if (semestreObject != null) {

                                    Log.d("positiveButton", "objeto semestre foi diferente de null");

                                    if (semestreObject.getBoolean("isDisponivel")) {

                                        Log.d("positiveButton", "semestre isDisponivel!!");

                                        ParseQuery horarioQuery = new ParseQuery("Horario");
                                        horarioQuery.whereEqualTo("usuario", GradeWEBApplication.getInstance().getUsuario());
                                        horarioQuery.whereEqualTo("semestre", semestre);

                                        try {
                                            ParseObject horarioObject = horarioQuery.getFirst();

                                            Log.d("positiveButton", "horarioObject NÃo foi null... NÃO posso criar o horario");

                                            Toast.makeText(getActivity().getApplicationContext(), "Não foi possível criar horário. Horário já cadastrado para o semestre especificado!", 5000).show();
                                        } catch (ParseException e) {

                                            Log.d("positiveButton", "horarioObject foi null, posso criar o horario");

                                            ParseObject novoHorario = new ParseObject("Horario");
                                            novoHorario.put("usuario", GradeWEBApplication.getInstance().getUsuario());
                                            novoHorario.put("semestre", semestre);
                                            novoHorario.put("turmas", new ArrayList());
                                            novoHorario.save();

                                            GradeWEBApplication.getInstance().setHorario(novoHorario);
                                            updateAdapter();
                                        }
                                    } else {

                                        Log.d("positiveButton", "semestre !isDisponivel");

                                        Toast.makeText(getActivity().getApplicationContext(), "Não foi possível criar horário. Semestre não está disponível!", 5000).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Não foi possível criar horário. Não há registros desse semestre!", 5000).show();
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity().getApplicationContext(), "Não foi possível criar horário. Tente novamente mais tarde!", 5000).show();
                            }

                        //}
                        //else
                            //Toast.makeText(getActivity().getApplicationContext(), "Não foi possível criar horário. Formato de semestre inválido!", 5000).show();


                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        return rootView;
    } // end of onCreateView


    public void ordenaTurmasPorDID(List<ParseObject> turmas) {
        if (!turmas.isEmpty()) {
            Collections.sort(turmas, new Comparator<ParseObject>() {
                @Override
                public int compare(ParseObject t1, ParseObject t2) {
                    //You should ensure that list doesn't contain null values!
                    Log.d("Comparando", t1.getParseObject("disciplina").getString("DID") + " com " +t2.getParseObject("disciplina").getString("DID"));

                    return t1.getParseObject("disciplina").getString("DID").compareTo(t2.getParseObject("disciplina").getString("DID"));
                }
            });
        }
    }

    public void updateAdapter() {
        CustomAdapterListaSemestre myAdapter = new CustomAdapterListaSemestre(context);
        listaSemestresListView.setAdapter(myAdapter);
        myAdapter.loadObjects();
    }
}
