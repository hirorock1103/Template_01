package com.example.hirorock1103.template_01.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.R;

public class DialogTips extends AppCompatDialogFragment {

    private int id;
    private String dialogTitle;

    private DialogTipsNoticeListener listener;

    public interface DialogTipsNoticeListener{
        public void DialogTipsNoticeResult();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (DialogTipsNoticeListener)context;
        }catch (Exception e){
            Common.log(e.getMessage());
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.f_source, null);

        try{
            id = getArguments().getInt("id");
        }catch(Exception e){
            id = 0;
        }

        if(id > 0){
            //mode edit
            dialogTitle = getString(R.string.dialogTipsTitleEdit);

        }else{

            dialogTitle = getString(R.string.dialogTipsTitleAdd);

        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setView(view)
                .setTitle(dialogTitle)
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        View view = getActivity().findViewById(android.R.id.content);
                        Snackbar.make(view, getString(R.string.add_complete_msg), Snackbar.LENGTH_SHORT).show();
                        listener.DialogTipsNoticeResult();

                    }
                });

        Dialog dialog = builder.create();

        return dialog;

    }
}
