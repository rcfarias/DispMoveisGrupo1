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

    public FragmentoSelecionarTurma() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        ArrayList listaIdsTurmas = getArguments().getStringArrayList("turmas_ids");

        CustomAdapterListaTurmas ad = new CustomAdapterListaTurmas(getActivity().getApplicationContext(), listaIdsTurmas);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Selecione uma turma")
                .setAdapter(ad, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //ParseUser usuario = GradeWEBApplication.getInstance().getUsuario();
                        //listaTurmas.add()
                        //usuario.put("");
                    }
                });
                ad.loadObjects();
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public void  setHorario(ParseObject horario) {
        this.horario = horario;
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