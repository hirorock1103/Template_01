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
import android.widget.TextView;

import com.example.hirorock1103.template_01.DB.TipsContentsManager;
import com.example.hirorock1103.template_01.DB.TipsGroupManager;
import com.example.hirorock1103.template_01.DB.TipsManager;
import com.example.hirorock1103.template_01.R;

public class DialogDeleteConfirm extends AppCompatDialogFragment {

    private String dataType;
    private int id;
    private int order;
    private TextView detail;
    private String detailContents;

    //listener
    private DialogDeleteNoticeListener listener;


    public interface DialogDeleteNoticeListener{
        public void deleteResultNotice(int order);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            this.listener = (DialogDeleteNoticeListener)context;
        }catch (Exception e){
            View v = getActivity().findViewById(android.R.id.content);
            Snackbar.make(v, "DialogDeleteNoticeListener is not implemented!", Snackbar.LENGTH_LONG).show();
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //get data
        try{
            dataType = getArguments().getString("dataType", null);
        }catch(Exception e) {
            View v = getActivity().findViewById(android.R.id.content);
            Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }

        //get data
        try{
            id = getArguments().getInt("id", 0);
        }catch(Exception e) {
            View v = getActivity().findViewById(android.R.id.content);
            Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }

        //get data
        try{
            order = getArguments().getInt("order", 0);
        }catch(Exception e) {
            View v = getActivity().findViewById(android.R.id.content);
            Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }

        //get data
        try{
            detailContents = getArguments().getString("detail", null);
        }catch(Exception e) {
            View v = getActivity().findViewById(android.R.id.content);
            Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }

        //


        View view = LayoutInflater.from(getContext()).inflate(R.layout.f_source, null);

        detail = view.findViewById(R.id.detail);
        if(detailContents != null){
            detail.setText(detailContents);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setView(view)
                .setTitle(getString(R.string.comment1))
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                })
                .setPositiveButton("PLACEHOLDER:OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //delete実行する
                        if(dataType != null && id > 0 || dataType.equals("deleteView")){

                            int resultid = 0;

                            switch (dataType){

                                case "deleteView" :
                                    //処理
                                    resultid = 1;

                                    break;

                                case "tipsContents" :
                                    //処理
                                    TipsContentsManager manager = new TipsContentsManager(getContext());
                                    manager.delete(id);
                                    resultid = 1;

                                    break;

                                case "tips":
                                    TipsContentsManager contentsManager = new TipsContentsManager(getContext());
                                    TipsManager tipsManager = new TipsManager(getContext());
                                    tipsManager.delete(id);
                                    resultid = 1;

                                    break;

                                case "tipsGroup":

                                    TipsGroupManager groupManager = new TipsGroupManager(getContext());
                                    groupManager.delete(id);
                                    resultid = 1;

                                    break;

                            }

                            if(resultid > 0){
                                //notice result to activity
                                listener.deleteResultNotice(order);
                            }else{
                                View v = getActivity().findViewById(android.R.id.content);
                                Snackbar.make(v, "PLACEHOLDER:Fail", Snackbar.LENGTH_SHORT).show();
                            }

                        }else{

                            View v = getActivity().findViewById(android.R.id.content);
                            Snackbar.make(v, "PLACEHOLDER:Fail", Snackbar.LENGTH_SHORT).show();

                        }
                    }
                });

        Dialog dialog = builder.create();

        return dialog;



    }
}
