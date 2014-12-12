package com.rafael.gradeweb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael Farias on 01/12/2014.
 */
public class FragmentoSelecionarTurma extends DialogFragment {
    private List<ParseObject> listaTurmas;
    private ParseObject horario;
    private ArrayList<String> listaItemTurmas;

    public FragmentoSelecionarTurma() {//List<ParseObject> turmas) {//ParseObject horario ) {
        /*
        listaTurmas = turmas;

        for(ParseObject turma : listaTurmas) {
            ParseObject disciplina = turma.getParseObject("disciplina");
            listaItemTurmas.add(disciplina.getString("DID") + disciplina.getString("name") +
                    System.getProperty("line.separator") + " Turma: " + turma.getString("codigoTurma")+
                    System.getProperty("line.separator") + turma.getString("horarioAulas"));
        }
        */
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        ArrayAdapter ad = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1 ,listaItemTurmas);

        ParseQueryAdapter mAd = new ParseQueryAdapter(getActivity(),"Turma");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Selecione uma turma")
                .setAdapter(mAd, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //ParseUser usuario = GradeWEBApplication.getInstance().getUsuario();
                        //listaTurmas.add()
                        //usuario.put("");
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}


/*

public class FireMissilesDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_fire_missiles)
               .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // FIRE ZE MISSILES!
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
 */