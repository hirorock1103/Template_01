package com.example.hirorock1103.template_01;

import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.TipsContentsManager;
import com.example.hirorock1103.template_01.DB.TipsGroupManager;
import com.example.hirorock1103.template_01.DB.TipsManager;
import com.example.hirorock1103.template_01.Dialog.DialogContents;
import com.example.hirorock1103.template_01.Dialog.DialogDeleteConfirm;
import com.example.hirorock1103.template_01.Dialog.DialogExpandImage;
import com.example.hirorock1103.template_01.Dialog.DialogNextAction;
import com.example.hirorock1103.template_01.Dialog.DialogSelectGroup;
import com.example.hirorock1103.template_01.Master.Tips;
import com.example.hirorock1103.template_01.Master.TipsContents;
import com.example.hirorock1103.template_01.Master.TipsGroup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainTipsAddActivity extends AppCompatActivity
        implements DialogNextAction.DialogNextActionListener,
        DialogSelectGroup.DialogSelectGroupNoticeListener,
        DialogContents.DialogContentsResultListener,
        DialogDeleteConfirm.DialogDeleteNoticeListener{

    /**
     * どのタイミングでscroll view を自動スクロールさせるか
     * ★select next action を選択したとき　－－　DialogNextActionListenerで結果
     */

    private int tipsId = 0;

    //context menu contents id
    private int selectedContentsId = 0;

    private EditText howtotitle;
    private ImageView update;
    private TextView groupName;
    private LinearLayout btNextAction;
    private LinearLayout bt_ok;
    private LinearLayout resultArea;
    private LinearLayout showArea;

    private ScrollView scroll;

    //for camera
    private byte[] byteImage;
    private String videoPath;

    //each area
    private LinearLayout photoShowArea;
    private ImageView photoImageConfirmView;
    private VideoView videoConfirmView;

    //manager
    private TipsManager tipsManager;

    //static value
    private final static int RESULT_CAMERA = 1001;
    private final static int RESULT_PICK_IMAGE = 1002;
    private final static int PICWIDTH = 900;
    private final static int RESULT_PICK_MOVIE = 2001;
    private final static int CAMERA_REQUEST_CODE_VEDIO = 2002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tips_add);

        //menu
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.title20));

        //
        try{
            tipsId = getIntent().getExtras().getInt("id");
        }catch(Exception e){
            tipsId = 0;
        }

        //set view
        howtotitle = findViewById(R.id.edit_title);
        update = findViewById(R.id.update);
        groupName = findViewById(R.id.group_name);
        btNextAction = findViewById(R.id.next_action);
        bt_ok = findViewById(R.id.bt_ok);
        resultArea = findViewById(R.id.result_area);
        showArea = findViewById(R.id.area);
        scroll = findViewById(R.id.scroll);

        //set instance
        tipsManager = new TipsManager(this);

        //set listener
        btNextAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(howtotitle.getText().toString().isEmpty()){

                    Snackbar.make(v, getString(R.string.errorMsg3), Snackbar.LENGTH_SHORT).show();

                }else{

                    Common.hideKeyboard(MainTipsAddActivity.this);

                    //LAUNCH NEXT ACTION DIALOG
                    DialogNextAction dialogNextAction = new DialogNextAction();
                    dialogNextAction.show(getSupportFragmentManager(), "nextAction");

                }

            }
        });

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //select
                if(howtotitle.getText().toString().isEmpty() == false){

                    if(tipsId == 0){
                        Tips tips = new Tips();
                        tips.setTipsTitle(howtotitle.getText().toString());
                        tipsId = (int)tipsManager.addTips(tips);
                    }
                    //howtotitle.setEnabled(false);

                    DialogSelectGroup dialogGroup = new DialogSelectGroup();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", tipsId);
                    dialogGroup.setArguments(bundle);
                    dialogGroup.show(getSupportFragmentManager(),"dialog");
                }else{
                    Snackbar.make(v, getString(R.string.errorMsg5), Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        //update
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update title
                if(tipsId > 0){
                    Tips tips = tipsManager.getListById(tipsId);
                    tips.setTipsTitle(howtotitle.getText().toString());
                    long resId = tipsManager.updateTips(tips);
                    if(resId > 0){
                        Snackbar.make(v, getString(R.string.comment4), Snackbar.LENGTH_SHORT).show();
                    }
                }else{

                    if(howtotitle.getText().toString().isEmpty() == false){
                        Tips tips = new Tips();
                        tips.setTipsTitle(howtotitle.getText().toString());
                        long resId = tipsManager.addTips(tips);
                        if(resId > 0){
                            tipsId = (int)resId;
                            Snackbar.make(v, getString(R.string.comment6), Snackbar.LENGTH_SHORT).show();
                        }

                    }

                }
            }
        });

        //set data
        groupName.setText("--");

        if(tipsId > 0){
            //set title
            Tips tips = tipsManager.getListById(tipsId);
            howtotitle.setText(tips.getTipsTitle());
            //howtotitle.setEnabled(false);

            //set group
            if(tips.getGroupId() > 0){
                TipsGroupManager groupManager = new TipsGroupManager(this);
                TipsGroup g = groupManager.getListById(tips.getGroupId());
                groupName.setText(g.getGroupName());
            }else{
                groupName.setText("--");
            }

            //set list
            this.reloadContents(tipsId);

        }

        //hide keyboard
        //Common.hideKeyboard(this)

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void DialogNextActionResultNotice(String type) {

        //tips has been already setted
         if(type.isEmpty() == false && tipsId == 0){
            Tips tips = new Tips();
            tips.setTipsTitle(howtotitle.getText().toString());
            tipsId = (int)tipsManager.addTips(tips);
            //View view = findViewById(android.R.id.content);
            //Snackbar.make(view, getString(R.string.title4), Snackbar.LENGTH_SHORT).show();
        }


        if(tipsId > 0){

            //タイトル固定
            //howtotitle.setEnabled(false);

            //make button next action invalid
            btNextAction.setEnabled(false);


            String msg = type.isEmpty() ? getString(R.string.errorMsg1) : type + getString(R.string.comment2);

            int layoutId = 0;
            //各戻り値により登録用レイアウトを切り分ける
            switch (type){
                case "step":
                    layoutId = R.layout.f_item_result_view_step;
                    break;
                case "text":
                    layoutId = R.layout.f_item_result_view_text;
                    break;
                case "photo":
                    layoutId = R.layout.f_item_result_view_photo;
                    break;
                case "movie":
                    layoutId = R.layout.f_item_result_view_movie;
                    break;
            }

            //各レイアウトに対してview（機能）を追加する。
            if(layoutId > 0){

                View resultView = LayoutInflater.from(this).inflate(layoutId, null);

                switch (layoutId){
                    case R.layout.f_item_result_view_step:
                        //STEP登録用
                        setViewForStep(resultView, type, tipsId);
                        break;

                    case R.layout.f_item_result_view_text:
                        //文言登録用
                        setViewForText(resultView, type, tipsId);
                        break;

                    case R.layout.f_item_result_view_photo:
                        //写真登録用
                        setViewForPhoto(resultView, type, tipsId);
                        break;

                    case R.layout.f_item_result_view_movie:
                        //動画登録用
                        setViewForMovie(resultView, type, tipsId);
                        break;

                }

                resultArea.addView(resultView);

                //view の一番したに来るので scroll を自動でbottomまで --- !point
                View lastChild = scroll.getChildAt(scroll.getChildCount() -1 );
                int bottom = lastChild.getBottom();
                scroll.smoothScrollTo(0,bottom);
                ObjectAnimator.ofInt(scroll, "scrollY", bottom).setDuration(1000).start();
            }

        }

        if(type.isEmpty()){

            btNextAction.setEnabled(true);
            //alert
            View view = findViewById(android.R.id.content);
            Snackbar.make(view, getString(R.string.errorMsg1),Snackbar.LENGTH_SHORT ).show();
        }

    }

    private void reloadContents(int tipsId){

        showArea.removeAllViews();

        TipsContentsManager manager = new TipsContentsManager(MainTipsAddActivity.this);

        List<TipsContents> list = manager.getListByTipsId(tipsId);

        if(list.size() > 0){
            for (final TipsContents contents : list){
                View view;
                final int contentsId = contents.getId();
                ConstraintLayout layout;
                switch (contents.getType()){

                    case "step":
                        view = LayoutInflater.from(MainTipsAddActivity.this).inflate(R.layout.item_row_step, null);
                        //set View
                        layout = view.findViewById(R.id.layout_b);
                        TextView stepTitle = view.findViewById(R.id.step_title);
                        stepTitle.setText(contents.getContents());
                        final ImageView delete1 = view.findViewById(R.id.delete);
                        delete1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DialogDeleteConfirm deleteConfirm = new DialogDeleteConfirm();
                                Bundle bundle = new Bundle();
                                bundle.putString("dataType", "tipsContents");
                                bundle.putInt("id", contentsId);
                                deleteConfirm.setArguments(bundle);
                                deleteConfirm.show(getSupportFragmentManager(), "delete");
                            }
                        });
                        layout.setTag(contentsId);
                        registerForContextMenu(layout);
                        showArea.addView(view);
                        //add view showArea
                        break;
                    case "text":
                        //text_contents
                        view = LayoutInflater.from(MainTipsAddActivity.this).inflate(R.layout.item_row_text, null);
                        //add view showArea
                        layout = view.findViewById(R.id.layout_b);
                        TextView textContents = view.findViewById(R.id.text_contents);
                        textContents.setText(contents.getContents());
                        ImageView delete2 = view.findViewById(R.id.delete);
                        delete2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DialogDeleteConfirm deleteConfirm = new DialogDeleteConfirm();
                                Bundle bundle = new Bundle();
                                bundle.putString("dataType", "tipsContents");
                                bundle.putInt("id", contentsId);
                                deleteConfirm.setArguments(bundle);
                                deleteConfirm.show(getSupportFragmentManager(), "delete");
                            }
                        });
                        layout.setTag(contentsId);
                        registerForContextMenu(layout);
                        showArea.addView(view);
                        break;
                    case "photo":

                        view = LayoutInflater.from(MainTipsAddActivity.this).inflate(R.layout.item_row_photo, null);
                        layout = view.findViewById(R.id.layout_b);
                        ImageView image = view.findViewById(R.id.image);
                        ImageView delete3 = view.findViewById(R.id.delete);
                        if(contents.getImage().length > 0){

                            Bitmap bitmap = BitmapFactory.decodeByteArray(contents.getImage(), 0, contents.getImage().length);
                            image.setImageBitmap(bitmap);
                            //set click listener
                            image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //dialog
                                    DialogExpandImage dialogExpandImage = new DialogExpandImage();
                                    Bundle bundle = new Bundle();
                                    bundle.putByteArray("image",contents.getImage());
                                    dialogExpandImage.setArguments(bundle);
                                    dialogExpandImage.show(getSupportFragmentManager(), "dialog");
                                }
                            });

                        }
                        delete3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DialogDeleteConfirm deleteConfirm = new DialogDeleteConfirm();
                                Bundle bundle = new Bundle();
                                bundle.putString("dataType", "tipsContents");
                                bundle.putInt("id", contentsId);
                                deleteConfirm.setArguments(bundle);
                                deleteConfirm.show(getSupportFragmentManager(), "delete");
                            }
                        });
                        layout.setTag(contentsId);
                        registerForContextMenu(layout);
                        showArea.addView(view);
                        break;
                    case "movie":

                        view = LayoutInflater.from(MainTipsAddActivity.this).inflate(R.layout.item_row_movie, null);
                        layout = view.findViewById(R.id.layout_b);
                        VideoView movie = view.findViewById(R.id.movie);
                        if(contents.getMoviePath() != null){
                            try{
                                Common.log("item lsit - movie path:" + contents.getMoviePath());
                                String path = contents.getMoviePath();
                                movie.setMediaController(new MediaController(MainTipsAddActivity.this));
                                movie.setVideoURI(Uri.parse(path));
                                movie.seekTo(1);
                            }catch(Exception e){
                                Common.log(e.getMessage());
                            }

                        }

                        ImageView delete4 = view.findViewById(R.id.delete);
                        delete4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DialogDeleteConfirm deleteConfirm = new DialogDeleteConfirm();
                                Bundle bundle = new Bundle();
                                bundle.putString("dataType", "tipsContents");
                                bundle.putInt("id", contentsId);
                                deleteConfirm.setArguments(bundle);
                                deleteConfirm.show(getSupportFragmentManager(), "delete");
                            }
                        });
                        layout.setTag(contentsId);
                        registerForContextMenu(layout);
                        showArea.addView(view);
                        break;

                }
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        selectedContentsId = Integer.parseInt(v.getTag().toString());
        getMenuInflater().inflate(R.menu.option_menu_2, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        //check mode
        String type = checkType(selectedContentsId);

        switch (item.getItemId()){
            case R.id.option1:
                //edit dialog open
                DialogContents dialogContents = new DialogContents();
                Bundle bundle = new Bundle();
                bundle.putString("type", type);
                bundle.putInt("id", selectedContentsId);
                dialogContents.setArguments(bundle);
                dialogContents.show(getSupportFragmentManager(), "dialogContents");
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private String checkType(int tipsContentsId){
        String type = "";
        TipsContentsManager contentsManager = new TipsContentsManager(MainTipsAddActivity.this);
        TipsContents contents = contentsManager.getListById(tipsContentsId);
        return contents.getType();
    }

    //カメラ用のセッティング
    private void setViewForPhoto(View resultView, String type, final int tipsId){
        //共通
        LinearLayout btOK = resultView.findViewById(R.id.layout_ok);
        LinearLayout btDelete = resultView.findViewById(R.id.layout_ng);
        photoShowArea = resultView.findViewById(R.id.show_area);
        photoImageConfirmView = resultView.findViewById(R.id.image);

        //photo
        Button btFindFile = resultView.findViewById(R.id.bt_find_photo);
        Button btTakePhoto = resultView.findViewById(R.id.bt_take_photo);

        //make final
        final String typeStr = type;

        btTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //find files
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RESULT_CAMERA);
            }
        });

        btFindFile.setOnClickListener(new View.OnClickListener() {
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
                startActivityForResult(photoPickerIntent, RESULT_PICK_IMAGE);

            }
        });

        //OK
        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //photo check
                String error = "";

                try{
                    if(byteImage.length == 0){
                        error = getString(R.string.errorMsg4);
                    }
                }catch(NullPointerException e){
                    error = "placeholder:ファイルが選択されていません";
                }catch(Exception e){
                    error = e.getMessage();
                }

                if(error.isEmpty()){

                    //子タイトルを登録する
                    TipsContentsManager contentsManager = new TipsContentsManager(MainTipsAddActivity.this);
                    TipsContents contents = new TipsContents();
                    contents.setType(typeStr);
                    contents.setTipsId((int)tipsId);
                    contents.setImage(byteImage);

                    long resultId = contentsManager.addTipsContents(contents);
                    if(resultId > 0){
                        //Snackbar.make(v, getString(R.string.add_complete_msg), Snackbar.LENGTH_SHORT).show();
                        resultArea.removeAllViews();
                        btNextAction.setEnabled(true);
                        reloadContents(tipsId);

                        //move to top
                        scroll.smoothScrollTo(0,0);
                        ObjectAnimator.ofInt(scroll, "scrollY", 0).setDuration(1000).start();
                    }

                }else{

                    Snackbar.make(v, error, Snackbar.LENGTH_SHORT).show();

                }
            }
        });


        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDeleteConfirm deleteConfirm = new DialogDeleteConfirm();
                Bundle bundle = new Bundle();
                bundle.putString("dataType", "deleteView");
                deleteConfirm.setArguments(bundle);
                deleteConfirm.show(getSupportFragmentManager(), "delete");

                //move to top
                scroll.smoothScrollTo(0,0);
                ObjectAnimator.ofInt(scroll, "scrollY", 0).setDuration(1000).start();
            }
        });
    }

    //動画用のセッティング
    private void setViewForMovie(View resultView, String type, final int tipsId){
        //共通
        LinearLayout btOK = resultView.findViewById(R.id.layout_ok);
        LinearLayout btDelete = resultView.findViewById(R.id.layout_ng);

        videoConfirmView = resultView.findViewById(R.id.video);
        videoConfirmView.setVisibility(View.GONE);

        //movie
        Button btFindFile = resultView.findViewById(R.id.bt_find_movie);
        Button btTakeMovie = resultView.findViewById(R.id.bt_take_movie);

        //make final
        final String typeStr = type;

        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //movie check
                String error = "";

                if(videoPath == null || videoPath.isEmpty()){
                    error = getString(R.string.errorMsg1);
                }
                if(error.isEmpty()){

                    //子タイトルを登録する
                    TipsContentsManager contentsManager = new TipsContentsManager(MainTipsAddActivity.this);
                    TipsContents contents = new TipsContents();
                    contents.setType(typeStr);
                    contents.setTipsId((int)tipsId);
                    contents.setMoviePath(videoPath);

                    long resultId = contentsManager.addTipsContents(contents);
                    if(resultId > 0){
                        Snackbar.make(v, getString(R.string.add_complete_msg), Snackbar.LENGTH_SHORT).show();
                        resultArea.removeAllViews();
                        btNextAction.setEnabled(true);

                        reloadContents(tipsId);

                        //move to top
                        scroll.smoothScrollTo(0,0);
                        ObjectAnimator.ofInt(scroll, "scrollY", 0).setDuration(1000).start();
                    }

                }
            }
        });


        btFindFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take photo --https://akira-watson.com/android/camera-intent.html
                Intent moviePickerIntent = new Intent(Intent.ACTION_PICK);

                //image file directory
                File picDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
                String path = picDirectory.getPath();

                //uri
                Uri data = Uri.parse(path);
                moviePickerIntent.setDataAndType(data, "video/*");
                startActivityForResult(moviePickerIntent, RESULT_PICK_MOVIE);
            }
        });

        btTakeMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takeVideoIntent,
                            CAMERA_REQUEST_CODE_VEDIO);
                }

            }
        });


        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDeleteConfirm deleteConfirm = new DialogDeleteConfirm();
                Bundle bundle = new Bundle();
                bundle.putString("dataType", "deleteView");
                deleteConfirm.setArguments(bundle);
                deleteConfirm.show(getSupportFragmentManager(), "delete");

                //move to top
                scroll.smoothScrollTo(0,0);
                ObjectAnimator.ofInt(scroll, "scrollY", 0).setDuration(1000).start();
            }
        });
    }

    private void setViewForStep(View resultView, String type, final int tipsId){
        //共通
        LinearLayout btOK = resultView.findViewById(R.id.layout_ok);
        LinearLayout btDelete = resultView.findViewById(R.id.layout_ng);
        final EditText editTitle = resultView.findViewById(R.id.step_title_edit);
        //make final
        final String typeStr = type;

        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String error = "";
                if(editTitle.getText().toString().isEmpty()){
                    error = getString(R.string.errorMsg2);
                }

                if(error.isEmpty()){

                    //子タイトルを登録する
                    TipsContentsManager contentsManager = new TipsContentsManager(MainTipsAddActivity.this);
                    TipsContents contents = new TipsContents();
                    contents.setType(typeStr);
                    contents.setContents(editTitle.getText().toString());
                    contents.setTipsId((int)tipsId);

                    long resultId = contentsManager.addTipsContents(contents);
                    if(resultId > 0){
                        Snackbar.make(v, getString(R.string.add_complete_msg), Snackbar.LENGTH_SHORT).show();
                        resultArea.removeAllViews();
                        btNextAction.setEnabled(true);

                        reloadContents(tipsId);

                        //move to top
                        scroll.smoothScrollTo(0,0);
                        ObjectAnimator.ofInt(scroll, "scrollY", 0).setDuration(1000).start();
                    }

                }
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDeleteConfirm deleteConfirm = new DialogDeleteConfirm();
                Bundle bundle = new Bundle();
                bundle.putString("dataType", "deleteView");
                deleteConfirm.setArguments(bundle);
                deleteConfirm.show(getSupportFragmentManager(), "delete");

                //move to top
                scroll.smoothScrollTo(0,0);
                ObjectAnimator.ofInt(scroll, "scrollY", 0).setDuration(1000).start();
            }
        });
    }

    private void setViewForText(View resultView, String type, final int tipsId){

        //共通
        LinearLayout btOK = resultView.findViewById(R.id.layout_ok);
        LinearLayout btDelete = resultView.findViewById(R.id.layout_ng);
        final EditText editTitle = resultView.findViewById(R.id.text_title_edit);
        //make final
        final String typeStr = type;

        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String error = "";
                if(editTitle.getText().toString().isEmpty()){
                    error = getString(R.string.errorMsg2);
                }

                if(error.isEmpty()){

                    //子タイトルを登録する
                    TipsContentsManager contentsManager = new TipsContentsManager(MainTipsAddActivity.this);
                    TipsContents contents = new TipsContents();
                    contents.setType(typeStr);
                    contents.setContents(editTitle.getText().toString());
                    contents.setTipsId((int)tipsId);

                    long resultId = contentsManager.addTipsContents(contents);
                    if(resultId > 0){
                        Snackbar.make(v, getString(R.string.add_complete_msg), Snackbar.LENGTH_SHORT).show();
                        resultArea.removeAllViews();
                        btNextAction.setEnabled(true);

                        reloadContents(tipsId);

                        //move to top
                        scroll.smoothScrollTo(0,0);
                        ObjectAnimator.ofInt(scroll, "scrollY", 0).setDuration(1000).start();
                    }

                }
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDeleteConfirm deleteConfirm = new DialogDeleteConfirm();
                Bundle bundle = new Bundle();
                bundle.putString("dataType", "deleteView");
                deleteConfirm.setArguments(bundle);
                deleteConfirm.show(getSupportFragmentManager(), "delete");

                //move to top
                scroll.smoothScrollTo(0,0);
                ObjectAnimator.ofInt(scroll, "scrollY", 0).setDuration(1000).start();
            }
        });

    }


    @Override
    public void deleteResultNotice(int order) {
        resultArea.removeAllViews();
        //howtotitle.setEnabled(true);
        btNextAction.setEnabled(true);
        reloadContents(tipsId);

        //View view = findViewById(android.R.id.content);
        //Snackbar.make(view, getString(R.string.comment3), Snackbar.LENGTH_SHORT).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // requestしたコードがRESULT_CAMERAであるか確認する

        if(resultCode == RESULT_OK){


            if (requestCode == RESULT_CAMERA || requestCode == DialogContents.RESULT_CAMERA_FROM_FRAGMENT) {
                try{
                    if( data.getExtras() != null){
                        // dataから画像を取り出す
                        Bitmap bitmap = (Bitmap)data.getExtras().get("data");

                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();

                        //設定された幅になるまで画像を縮小する
                        int p = 1;
                        /*
                        while(width > PICWIDTH){
                            //縮小率を決める
                            p *= 2;
                            width /= p;
                        }
                        */

                        Bitmap resizeImage;
                        if(p > 1){
                            resizeImage = Bitmap.createScaledBitmap(bitmap,(int)(width/p),(int)(height/p),true);
                        }else{
                            resizeImage = bitmap;
                        }


                        //bitmapをblob保存用に変換
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        resizeImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byteImage = stream.toByteArray();
                        stream.close();

                        //find fragment
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag("dialogContents");
                        if(fragment != null){
                            DialogContents frag = (DialogContents)fragment;
                            frag.setImage(bitmap,byteImage);
                        }else{

                            photoImageConfirmView.setImageBitmap(bitmap);

                        }

                        /*
                        photoShowArea.removeAllViews();
                        photoShowArea.addView(image);
                        */

                    }else{
                        //cancel
                        return;
                    }
                }catch(Exception e){
                    Common.log(e.getMessage());
                }
            }else if(requestCode == RESULT_PICK_IMAGE || requestCode == DialogContents.RESULT_PICK_IMAGE_FROM_FRAGMENT){

                Uri imageUri = data.getData();
                InputStream inputStream;
                BitmapFactory.Options imageOptions;

                try {

                    inputStream = getContentResolver().openInputStream(imageUri);

                    //画像サイズ情報
                    imageOptions = new BitmapFactory.Options();
                    imageOptions.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(inputStream,null,imageOptions);
                    inputStream.close();

                    //再度読み込み
                    inputStream = getContentResolver().openInputStream(imageUri);
                    int width = imageOptions.outWidth;

                    int p = 1;
                    while(width > PICWIDTH){
                        //縮小率を決める
                        p *= 2;
                        width /= p;
                    }

                    Bitmap imageBitmap;
                    if(p > 1){
                        //縮小
                        imageOptions = new BitmapFactory.Options();
                        imageOptions.inSampleSize = p;
                        imageBitmap = BitmapFactory.decodeStream(inputStream, null,imageOptions);

                    }else{
                        //等倍
                        imageBitmap = BitmapFactory.decodeStream(inputStream, null,null);
                    }

                    inputStream.close();

                    //bitmapをblob保存用に変換
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byteImage = stream.toByteArray();
                    stream.close();

                    //確認用の画像
                    Bitmap img = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);

                    //Image view
                    /*
                    ImageView imageView = new ImageView(MainTipsAddActivity.this);
                    photoShowArea.removeAllViews();
                    photoShowArea.addView(imageView);
                    */

                    //find fragment
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag("dialogContents");
                    if(fragment != null){
                        DialogContents frag = (DialogContents)fragment;
                        frag.setImage(img,byteImage);
                    }else{
                        photoImageConfirmView.setImageBitmap(img);
                    }

                    //cardview.setVisibility(View.VISIBLE);

                }catch(FileNotFoundException e){
                    e.printStackTrace();
                    Common.log(e.getMessage());
                }catch(IOException e){
                    e.printStackTrace();
                    Common.log(e.getMessage());
                }

            }else if(requestCode == RESULT_PICK_MOVIE || requestCode == DialogContents.RESULT_PICK_VIDEO_FROM_FRAGMENT){

                Uri uri = data.getData();
                //String path = uri.toString();//このパスだとNGになる



                String path = null;
                String scheme = uri.getScheme();
                if ("file".equals(scheme)) {
                    path = uri.getPath();
                } else if("content".equals(scheme)) {
                    ContentResolver contentResolver = this.getContentResolver();
                    Cursor cursor = contentResolver.query(uri, new String[] { MediaStore.MediaColumns.DATA }, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        path = cursor.getString(0);
                        cursor.close();
                    }
                }

                /**
                File file = new File(path);
                Uri newUri = FileProvider.getUriForFile(this,"com.example.hirorock1103.tipsapp.fileprovider",file);
                */

                videoPath = path;

                Fragment fragment = getSupportFragmentManager().findFragmentByTag("dialogContents");
                if(fragment != null){
                    DialogContents frag = (DialogContents)fragment;
                    frag.setMovie(path);
                }else{

                    //play video
                    try{

                        videoConfirmView.setVisibility(View.VISIBLE);
                        videoConfirmView.setVideoURI(uri);
                        videoConfirmView.setMediaController(new MediaController(MainTipsAddActivity.this));
                        videoConfirmView.seekTo(1);

                    }catch (Exception e){
                        Common.log(e.getMessage());
                        videoPath = "";
                    }

                }


            }else if(requestCode == CAMERA_REQUEST_CODE_VEDIO || requestCode == DialogContents.RESULT_MOVIE_FROM_FRAGMENT){
                Common.log("camera");
                //https://stackoverflow.com/questions/10278865/record-save-and-play-a-video-in-android
                Uri uri = data.getData();
                String path = uri.toString();
                videoPath = path;

                Fragment fragment = getSupportFragmentManager().findFragmentByTag("dialogContents");
                if(fragment != null){
                    DialogContents frag = (DialogContents)fragment;
                    frag.setMovie(path);
                }else{

                    try{

                        videoConfirmView.setVisibility(View.VISIBLE);
                        videoConfirmView.setVideoURI(Uri.parse(path.toString()));
                        videoConfirmView.setMediaController(new MediaController(MainTipsAddActivity.this));
                        videoConfirmView.seekTo(1);

                    }catch (Exception e){
                        Common.log(e.getMessage());
                        videoPath = "";
                    }

                }

                //String path = Utils.getRealPathFromURI(videoUri, this);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1010){
            Common.log("1010!1");
        }

    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


    @Override
    public void DialogSelectGroupNoticeResult() {

        View view = findViewById(android.R.id.content);
        Snackbar.make(view, getString(R.string.comment4), Snackbar.LENGTH_SHORT).show();

        //Tips
        TipsGroupManager groupManager = new TipsGroupManager(MainTipsAddActivity.this);
        Tips tips = tipsManager.getListById(tipsId);
        if(tips.getGroupId() > 0){
            TipsGroup group = groupManager.getListById(tips.getGroupId());
            groupName.setText(group.getGroupName());
        }else{
            groupName.setText("--");
        }


    }


    @Override
    public void DialogContentsResultNotice(String type) {
        View view = findViewById(android.R.id.content);

        Snackbar.make(view, "【" + type + "】" + getString(R.string.comment4), Snackbar.LENGTH_SHORT).show();
        this.reloadContents(tipsId);

    }
}
