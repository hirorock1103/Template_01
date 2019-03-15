package com.example.hirorock1103.template_01.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.hirorock1103.template_01.R;

public class DialogSourceView extends AppCompatDialogFragment {

    private TextView code;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.f_source, null);

        code = view.findViewById(R.id.code);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setView(view)
                .setTitle("Title")
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        Dialog dialog = builder.create();

        return dialog;

    }
}
