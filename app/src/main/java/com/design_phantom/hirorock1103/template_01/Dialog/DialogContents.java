package com.design_phantom.hirorock1103.template_01.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.design_phantom.hirorock1103.template_01.DB.TipsContentsManager;
import com.design_phantom.hirorock1103.template_01.Master.TipsContents;
import com.design_phantom.hirorock1103.template_01.R;

import java.io.File;

public class DialogContents extends AppCompatDialogFragment {

    public final static int RESULT_CAMERA_FROM_FRAGMENT = 9001;
    public final static int RESULT_PICK_IMAGE_FROM_FRAGMENT = 9002;
    public final static int RESULT_PICK_VIDEO_FROM_FRAGMENT = 9003;
    public final static int RESULT_MOVIE_FROM_FRAGMENT = 9004;

    private String type;
    private int id;

    //step
    private TextView step;
    //text
    private TextView text;
    //photo
    private ImageView imageView;
    private byte[] byteImage;
    //movie
    private VideoView videoView;
    private String moviePath;
    //lisitener
    private DialogContentsResultListener listener;

    public void setImage(Bitmap bitmap, byte[] byteImage){
        imageView.setImageBitmap(bitmap);
        this.byteImage = byteImage;
    }

    public void setMovie(String path){
        videoView.setVisibility(View.VISIBLE);
        videoView.requestFocus();
        videoView.setMediaController(new MediaController(getActivity()));
        videoView.setVideoURI(Uri.parse(path));
        videoView.seekTo(1);

        moviePath = path;
    }

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
                if(contents.getId() > 0){
                    LinearLayout layoutok = view.findViewById(R.id.layout_ok);
                    LinearLayout layoutcancl = view.findViewById(R.id.layout_ng);
                    layoutok.setVisibility(View.GONE);
                    layoutcancl.setVisibility(View.GONE);
                }
                step = view.findViewById(R.id.step_title_edit);
                step.setText(contents.getContents());

                break;

            case "text" :
                layout = R.layout.f_item_result_view_text;
                view = LayoutInflater.from(getContext()).inflate(layout, null);
                if(contents.getId() > 0){
                    LinearLayout layoutok = view.findViewById(R.id.layout_ok);
                    LinearLayout layoutcancl = view.findViewById(R.id.layout_ng);
                    layoutok.setVisibility(View.GONE);
                    layoutcancl.setVisibility(View.GONE);
                }
                text = view.findViewById(R.id.text_title_edit);
                text.setText(contents.getContents());

                break;

            case "photo" :
                layout = R.layout.f_item_result_view_photo;
                view = LayoutInflater.from(getContext()).inflate(layout, null);
                if(contents.getId() > 0){
                    LinearLayout layoutok = view.findViewById(R.id.layout_ok);
                    LinearLayout layoutcancl = view.findViewById(R.id.layout_ng);
                    layoutok.setVisibility(View.GONE);
                    layoutcancl.setVisibility(View.GONE);
                }
                imageView = view.findViewById(R.id.image);
                Bitmap img = BitmapFactory.decodeByteArray(contents.getImage(), 0, contents.getImage().length);
                imageView.setImageBitmap(img);

                Button btFind = view.findViewById(R.id.bt_find_photo);
                btFind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //take photo --https://akira-watson.com/android/camera-intent.html
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

                        //image file directory
                        File picDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        String path = picDirectory.getPath();

                        //uri
                        Uri data = Uri.parse(path);
                        photoPickerIntent.setDataAndType(data, "image/*");
                        getActivity().startActivityForResult(photoPickerIntent, RESULT_PICK_IMAGE_FROM_FRAGMENT);

                    }
                });
                Button btTakePhoto = view.findViewById(R.id.bt_take_photo);
                btTakePhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //take photo
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        getActivity().startActivityForResult(intent, RESULT_CAMERA_FROM_FRAGMENT);

                    }
                });

                break;

            case "movie" :
                layout = R.layout.f_item_result_view_movie;
                view = LayoutInflater.from(getContext()).inflate(layout, null);
                if(contents.getId() > 0){
                    LinearLayout layoutok = view.findViewById(R.id.layout_ok);
                    LinearLayout layoutcancl = view.findViewById(R.id.layout_ng);
                    layoutok.setVisibility(View.GONE);
                    layoutcancl.setVisibility(View.GONE);
                }

                videoView = view.findViewById(R.id.video);
                videoView.setVisibility(View.VISIBLE);
                videoView.requestFocus();
                videoView.setMediaController(new MediaController(getActivity()));
                videoView.setVideoURI(Uri.parse(contents.getMoviePath()));
                videoView.seekTo(1);
                //videoView.requestFocus();

                Button btFindFile = view.findViewById(R.id.bt_find_movie);
                btFindFile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent moviePickerIntent = new Intent(Intent.ACTION_PICK);

                        //image file directory
                        File picDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
                        String path = picDirectory.getPath();

                        //uri
                        Uri data = Uri.parse(path);
                        moviePickerIntent.setDataAndType(data, "video/*");
                        getActivity().startActivityForResult(moviePickerIntent, RESULT_PICK_VIDEO_FROM_FRAGMENT);
                    }
                });
                Button btTakeMovie = view.findViewById(R.id.bt_take_movie);
                btTakeMovie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                            getActivity().startActivityForResult(takeVideoIntent,
                                    RESULT_MOVIE_FROM_FRAGMENT);
                        }
                    }
                });


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
                                        if(byteImage != null && byteImage.length > 0){
                                            currentContents.setImage(byteImage);
                                            resultId = contentsManager.updateTipsContents(currentContents);
                                        }

                                        break;

                                    case "movie" :

                                        if(moviePath != null && moviePath.isEmpty() == false){
                                            currentContents.setMoviePath(moviePath);
                                            resultId = contentsManager.updateTipsContents(currentContents);
                                        }

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
