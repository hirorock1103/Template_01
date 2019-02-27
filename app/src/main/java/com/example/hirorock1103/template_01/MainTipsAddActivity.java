package com.example.hirorock1103.template_01;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.TipsContentsManager;
import com.example.hirorock1103.template_01.DB.TipsManager;
import com.example.hirorock1103.template_01.Dialog.DialogDeleteConfirm;
import com.example.hirorock1103.template_01.Dialog.DialogNextAction;
import com.example.hirorock1103.template_01.Dialog.DialogTips;
import com.example.hirorock1103.template_01.Master.Tips;
import com.example.hirorock1103.template_01.Master.TipsContents;

public class MainTipsAddActivity extends AppCompatActivity
        implements DialogNextAction.DialogNextActionListener, DialogDeleteConfirm.DialogDeleteNoticeListener {

    private EditText howtotitle;
    private LinearLayout btNextAction;
    private LinearLayout bt_ok;
    private LinearLayout resultArea;

    //manager
    private TipsManager tipsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tips_add);

        //set view
        howtotitle = findViewById(R.id.edit_title);
        btNextAction = findViewById(R.id.next_action);
        bt_ok = findViewById(R.id.bt_ok);
        resultArea = findViewById(R.id.result_area);

        //set instance
        tipsManager = new TipsManager(this);

        //set listener
        btNextAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //next action
                /*
                View view = LayoutInflater.from(MainTipsAddActivity.this).inflate(R.layout.f_item_select_view, null);
                setChildView(view);
                area.addView(view);
                */

                if(howtotitle.getText().toString().isEmpty()){

                    Snackbar.make(v, getString(R.string.errorMsg3), Snackbar.LENGTH_SHORT).show();

                }else{

                    Common.hideKeyboard(MainTipsAddActivity.this);

                    DialogNextAction dialogNextAction = new DialogNextAction();
                    dialogNextAction.show(getSupportFragmentManager(), "nextAction");

                }

            }
        });

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public void DialogNextActionResultNotice(String type) {

        Tips tips = new Tips();
        tips.setTipsTitle(howtotitle.getText().toString());
        long resultId = tipsManager.addTips(tips);

        View view = findViewById(android.R.id.content);

        if(resultId > 0){

            howtotitle.setEnabled(false);

            //make button next action invalid
            btNextAction.setEnabled(false);

            Snackbar.make(view, getString(R.string.title4), Snackbar.LENGTH_SHORT).show();
            String msg = type.isEmpty() ? getString(R.string.errorMsg1) : type + getString(R.string.comment2);

            int layoutId = 0;
            switch (type){
                case "step":
                    layoutId = R.layout.f_item_result_view_step;
                    break;
                case "text":
                    break;
                case "photo":
                    break;
                case "movie":
                    break;
            }

            //値ごとのviewを追加する。
            if(layoutId > 0){

                View resultView = LayoutInflater.from(this).inflate(R.layout.f_item_result_view_step, null);
                LinearLayout btOK = resultView.findViewById(R.id.layout_ok);
                final EditText editTitle = resultView.findViewById(R.id.step_title_edit);
                ImageView btDelete = resultView.findViewById(R.id.image_delete);

                //make final
                final String typeStr = type;
                final long tipsId = resultId;

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
                    }
                });

                resultArea.addView(resultView);

            }

        }

    }

    @Override
    public void deleteResultNotice(int order) {
        resultArea.removeAllViews();
    }
}
