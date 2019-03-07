package com.example.hirorock1103.template_01.Dialog;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.TipsGroupManager;
import com.example.hirorock1103.template_01.Master.TipsGroup;
import com.example.hirorock1103.template_01.R;

public class DialogGroup extends AppCompatDialogFragment {

    private int id;

    //view
    private EditText titleEdit;

    private TipsGroupManager manager;
    private DialogGroupNoticeListener listener;

    public interface DialogGroupNoticeListener{
        public void DialogGroupNoticeResult();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (DialogGroupNoticeListener)context;
        }catch (ClassCastException e){
            Common.log(e.getMessage());
        }catch (Exception e){
            Common.log(e.getMessage());
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.f_group, null);

        titleEdit = view.findViewById(R.id.title_edit);

        manager = new TipsGroupManager(getContext());

        try{
            id = getArguments().getInt("id");
        }catch (Exception e){
            id = 0;
        }

        //
        if(id > 0){
            //edit
            TipsGroup group = manager.getListById(id);
            //set data to View
            titleEdit.setText(group.getGroupName());

        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setView(view)
                .setTitle("placeholder:GroupAdd")
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String error = "";

                        if(titleEdit.getText().toString().isEmpty()){
                            error = getString(R.string.errorMsg1);
                        }

                        if(error.isEmpty()){

                            long resultId = 0;

                            if(id > 0){

                                //edit
                                TipsGroup group = manager.getListById(id);
                                group.setGroupName(titleEdit.getText().toString());

                                resultId = manager.update(group);


                            }else{

                                //new
                                TipsGroup group = new TipsGroup();
                                group.setGroupName(titleEdit.getText().toString());

                                resultId = manager.addGroup(group);


                            }

                            if(resultId > 0){
                                listener.DialogGroupNoticeResult();
                            }else{
                                //fail
                            }

                        }else{
                            //have error
                        }

                    }
                });

        Dialog dialog = builder.create();

        return dialog;


    }
}
