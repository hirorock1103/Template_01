package com.example.hirorock1103.template_01.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hirorock1103.template_01.DB.TipsContentsManager;
import com.example.hirorock1103.template_01.Master.TipsContents;
import com.example.hirorock1103.template_01.R;

public class DialogContents extends AppCompatDialogFragment {

    private String type;
    private int id;

    //step
    private TextView step;
    //text
    private TextView text;
    //photo
    private ImageView imageView;
    //movie

    //lisitener
    private DialogContentsResultListener listener;

    public interface DialogContentsResultListener{
        public void DialogContentsResultNotice(String type);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (DialogContentsResultListener)context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        try{
            type = getArguments().getString("type");
            id = getArguments().getInt("id");
        }catch (Exception e){
            id = 0;
            type = "";
        }

        if(id == 0){
            View view = getActivity().findViewById(android.R.id.content);
            Snackbar.make(view, getString(R.string.errorMsg6), Snackbar.LENGTH_SHORT).show();
        }

        int layout = 0;
        View view;

        //contents information
        TipsContentsManager contentsManager = new TipsContentsManager(getContext());
        final TipsContents contents = contentsManager.getListById(id);

        switch (type){

            case "step" :
                layout = R.layout.f_item_result_view_step;
                view = LayoutInflater.from(getContext()).inflate(layout, null);
                step = view.findViewById(R.id.step_title_edit);
                step.setText(contents.getContents());

                break;

            case "text" :
                layout = R.layout.f_item_result_view_text;
                view = LayoutInflater.from(getContext()).inflate(layout, null);
                text = view.findViewById(R.id.text_title_edit);
                text.setText(contents.getContents());

                break;

            case "photo" :
                layout = R.layout.f_item_result_view_photo;
                view = LayoutInflater.from(getContext()).inflate(layout, null);
                imageView = view.findViewById(R.id.image);
                Bitmap img = BitmapFactory.decodeByteArray(contents.getImage(), 0, contents.getImage().length);
                imageView.setImageBitmap(img);
                break;

            case "movie" :
                layout = R.layout.f_item_result_view_movie;
                view = LayoutInflater.from(getContext()).inflate(layout, null);
                break;

             default:
                view = LayoutInflater.from(getContext()).inflate(layout, null);
                break;

        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setView(view)
                .setTitle(getString(R.string.title21))
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

                            if(id > 0){

                                //update
                                long resultId = 0;

                                TipsContentsManager contentsManager = new TipsContentsManager(getContext());
                                TipsContents currentContents = contentsManager.getListById(id);

                                //contents upload
                                switch (type){

                                    case "step" :

                                        currentContents.setContents(step.getText().toString());
                                        resultId = contentsManager.updateTipsContents(currentContents);

                                        break;

                                    case "text" :

                                        currentContents.setContents(text.getText().toString());
                                        resultId = contentsManager.updateTipsContents(currentContents);

                                        break;

                                    case "photo" :

                                        //currentContents.setImage();

                                        break;

                                    case "movie" :

                                        break;

                                    default:
                                        break;

                                }


                                if(resultId > 0){
                                    listener.DialogContentsResultNotice(type);
                                }

                            }

                        }

                    }
                });

        Dialog dialog = builder.create();
        return dialog;
    }
}
