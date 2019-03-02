package com.example.hirorock1103.template_01.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.TipsGroupManager;
import com.example.hirorock1103.template_01.DB.TipsManager;
import com.example.hirorock1103.template_01.Master.Tips;
import com.example.hirorock1103.template_01.Master.TipsGroup;
import com.example.hirorock1103.template_01.R;

import java.util.ArrayList;
import java.util.List;

public class DialogSelectGroup extends AppCompatDialogFragment {

    private int tipsId;
    private int settedGroupId;

    //view
    private Spinner spinner;

    private TipsGroupManager manager;
    private TipsManager tipsManager;
    private DialogSelectGroupNoticeListener listener;

    public interface DialogSelectGroupNoticeListener{
        public void DialogSelectGroupNoticeResult();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            listener = (DialogSelectGroupNoticeListener)context;
        }catch (ClassCastException e){
            Common.log(e.getMessage());
        }catch (Exception e){
            Common.log(e.getMessage());
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.f_select_group, null);

        spinner = view.findViewById(R.id.spinner);

        manager = new TipsGroupManager(getContext());
        tipsManager = new TipsManager(getContext());

        try{
            tipsId = getArguments().getInt("id");
        }catch (Exception e){
            tipsId = 0;
        }

        Tips tips = tipsManager.getListById(tipsId);
        settedGroupId = tips.getGroupId();

        //if id has value, default set
        List<TipsGroup> list = manager.getList();

        //adapter for spinner
        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("-- select --");
        int targetId = 1;
        int i = 1;
        for (TipsGroup group : list){
            spinnerArray.add(group.getGroupName());
            if(settedGroupId == group.getId()){
                targetId = i;
            }
            i++;
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(spinnerArrayAdapter);

        if(settedGroupId > 0){
            spinner.setSelection(targetId);
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

                        if(error.isEmpty()){

                            long resultId = 0;

                            String selectName = spinner.getSelectedItem().toString();
                            int groupId = 0;
                            List<TipsGroup> list = manager.getList();
                            for (TipsGroup group : list){
                                if(selectName.equals(group.getGroupName())){
                                    groupId = group.getId();
                                }
                            }

                            //
                            Tips tips = tipsManager.getListById(tipsId);
                            tips.setGroupId(groupId);
                            resultId = tipsManager.updateTips(tips);

                            if(resultId > 0){
                                listener.DialogSelectGroupNoticeResult();
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