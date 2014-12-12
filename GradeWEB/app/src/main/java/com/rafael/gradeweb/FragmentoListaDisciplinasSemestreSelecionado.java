package com.rafael.gradeweb;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FragmentoListaDisciplinasSemestreSelecionado extends Fragment{

    private final String fimDeLinha = System.getProperty("line.separator");

    private Context context;

    private Button adicionarDisciplinaButton;

    private ParseObject horarioObject;
    private ArrayList listaIdsTurmas;

    //private CustomAdapterListaDisciplinas mAdapter;
    private ListView listaDisciplinas;

    public void setHorarioObject(ParseObject horario) {

        this.horarioObject = horario;
    }

    public FragmentoListaDisciplinasSemestreSelecionado() {}

    public void resetAdapter(ArrayList listaIdsTurmas) {
        CustomAdapterListaDisciplinas mAdapter = new CustomAdapterListaDisciplinas(context, listaIdsTurmas);
        listaDisciplinas.setAdapter(mAdapter);
        mAdapter.loadObjects();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String semestre = (String) getArguments().getString("semestre");

        listaIdsTurmas = getArguments().getStringArrayList("turmas_ids");

        View viewRaiz = inflater.inflate(R.layout.layout_fragmento_lista_disciplinas_semestre_selecionado, container, false);

        listaDisciplinas = (ListView) viewRaiz.findViewById(R.id.disciplinas_semestres_list_view);

        //mAdapter = new CustomAdapterListaDisciplinas(context, listaIdsTurmas);
        //listaDisciplinas.setAdapter(mAdapter);
        //mAdapter.loadObjects();

        updateAdapter();

        listaDisciplinas.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                final ParseObject turma = (ParseObject) adapterView.getItemAtPosition(position);

                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("Isso removerá esta turma do seu horário")
                        .setTitle("Excluir a turma selecionada?");

                // Add the buttons
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        ParseObject horario = GradeWEBApplication.getInstance().getHorario();
                        List<ParseObject> listaTurmas = horario.getList("turmas");
                        int index = -1;

                        for( int j = 0; j < listaTurmas.size(); j++) {
                            Log.d("Buscando a turma", "turma.getObjectId() = " + turma.getObjectId() + " e listaTurmas.get(j).getObjectId() = " + listaTurmas.get(j).getObjectId());

                            if(listaTurmas.get(j).getObjectId().equals(turma.getObjectId())) {
                                Log.d("buscando ", " atribui index");
                                index = j;
                            }

                        }

                        Log.d("Buscando a turma", "Index = " + index);

                        if(index != -1) {
                            listaTurmas.remove(index);

                            horario.put("turmas", listaTurmas);

                            try {
                                horario.save();

                                Toast.makeText(getActivity().getApplicationContext(), "Disciplina removida do horário", 5000).show();

                                GradeWEBApplication.getInstance().setHorario(horario);
                                updateAdapter();

                            }
                            catch (ParseException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity().getApplicationContext(), "Não foi possível salvar o horário", 5000).show();
                            }
                        }
                        else {
                            Toast.makeText(getActivity().getApplicationContext(), "Não foi possível excluir! Disciplina não está presente no horário!!", 5000).show();
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

        /*
        listaDisciplinas.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                //Toast.makeText(getActivity(), "You clicked" + (String) semestresList.get(position), 5000).show();

                fragment = new FragmentoListaDisciplinasSemestreSelecionado();
                Bundle args = new Bundle();

                ParseObject horario = (ParseObject) adapter.getItemAtPosition(position);

                List<ParseObject> listaTurmas = horario.getList("turmas");
                ArrayList listaIdsTurmas = new ArrayList();

                for(ParseObject cadaTurma : listaTurmas) {
                    listaIdsTurmas.add(cadaTurma.getObjectId());
                }

                Toast.makeText(getActivity(), "semestre selecionado: " + (String) horario.getString("semestre"), 5000).show();

                args.putString("semestre", horario.getString("semestre"));
                args.putStringArrayList("turmas_ids", listaIdsTurmas);

                fragment.setArguments(args);
                frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        });
        */

        adicionarDisciplinaButton = (Button)  viewRaiz.findViewById(R.id.adicionar_disciplina_button);

        adicionarDisciplinaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity().getApplicationContext(), ActivityAdicionarDisciplina.class);
                //intent.putStringArrayListExtra("turmas_ids", getArguments().getStringArrayList("turmas_ids"));

                Bundle args = new Bundle();
                ArrayList l = getArguments().getStringArrayList("turmas_ids");

                //for(int i = 0; i < l.size() ; i++ ) {
                //    Toast.makeText(getActivity(), "turmaId: " + (String) l.get(i), 5000).show();
                //}

                args.putStringArrayList("turmas_ids", l);
                args.putString("semestre", getArguments().getString("semestre"));
                intent.putExtras(args);
                startActivity(intent, args);

            }
        });

        return  viewRaiz;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAdapter();
    }

    void updateAdapter() {
        List<ParseObject> turmas = GradeWEBApplication.getInstance().getHorario().getList("turmas");

        for(int j = 0; j < turmas.size(); j++) {
            listaIdsTurmas.add(turmas.get(j).getObjectId());
        }

        CustomAdapterListaDisciplinas mAdapter = new CustomAdapterListaDisciplinas(context, listaIdsTurmas);
        listaDisciplinas.setAdapter(mAdapter);
        mAdapter.loadObjects();
    }
}
