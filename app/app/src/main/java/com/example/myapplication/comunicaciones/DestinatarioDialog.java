package com.example.myapplication.comunicaciones;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DestinatarioDialog extends DialogFragment {

    private DestinatarioDialog() {
        Toast.makeText(getActivity().getApplicationContext(), "Iniciado", Toast.LENGTH_SHORT).show();
        llenarArray();
    }

    public static DestinatarioDialog newInstance() {
        return new DestinatarioDialog();
    }
    private String[] destinatarios = new String[]{"Tutor", "Equipo informática", "PAS", "Dirección"};
    private boolean[] isCheck;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
        //String title = getArguments().getString("title");
        String title = "Destinatario";
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMultiChoiceItems(destinatarios, isCheck, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                isCheck[which] = isChecked;
            }
        });
        alertDialogBuilder.setPositiveButton("Confirmar",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Confirmar", Toast.LENGTH_SHORT).show();
                for (int i=0; i< isCheck.length; i++) {
                    System.out.println(isCheck[i]);
                }
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        return alertDialogBuilder.create();
    }

    private void llenarArray() {
        if (isCheck == null) {
            isCheck = new boolean[destinatarios.length];
            for (int i=0; i < destinatarios.length; i++) {
                isCheck[i] = false;
            }
        }
    }
}

