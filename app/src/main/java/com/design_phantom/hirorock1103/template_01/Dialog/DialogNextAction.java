package com.design_phantom.hirorock1103.template_01.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.design_phantom.hirorock1103.template_01.R;

public class DialogNextAction extends AppCompatDialogFragment {

    private RadioGroup radioGroup;

    private DialogNextActionListener listener;

    public interface DialogNextActionListener{
        public void DialogNextActionResultNotice(String type);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (DialogNextActionListener)context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.f_item_select_view, null);

        radioGroup = view.findViewById(R.id.type_select_group);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setView(view)
                .setTitle(getString(R.string.dialogNextAction))
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String type = "";
                        //ok
                        switch (radioGroup.getCheckedRadioButtonId()){
                            case R.id.bt1:
                                type = "step";
                                break;
                            case R.id.bt2:
                                type = "text";
                                break;
                            case R.id.bt3:
                                type = "photo";
                                break;
                            case R.id.bt4:
                                type = "movie";
                                break;
                            default:
                                break;

                        }

                        listener.DialogNextActionResultNotice(type);

                    }
                });

        Dialog dialog = builder.create();

        return dialog;

    }
}
