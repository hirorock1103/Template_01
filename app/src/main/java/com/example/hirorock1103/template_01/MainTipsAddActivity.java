package com.example.hirorock1103.template_01;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hirorock1103.template_01.Dialog.DialogDeleteConfirm;

public class MainTipsAddActivity extends AppCompatActivity implements DialogDeleteConfirm.DialogDeleteNoticeListener {

    private LinearLayout next_action;
    private LinearLayout bt_ok;
    private LinearLayout area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tips_add);

        next_action = findViewById(R.id.next_action);
        bt_ok = findViewById(R.id.bt_ok);
        area = findViewById(R.id.area);

        next_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //next action
                View view = LayoutInflater.from(MainTipsAddActivity.this).inflate(R.layout.f_item_select_view, null);
                setChildView(view);
                area.addView(view);
            }
        });

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }

    @Override
    public void deleteResultNotice(int order) {
        area.removeAllViews();
        View view = findViewById(android.R.id.content);
        Snackbar.make(view, "PLACEHOLDER:削除", Snackbar.LENGTH_SHORT).show();
    }

    private View setChildView(View view){
        ImageView deleteImage = view.findViewById(R.id.image_delete);
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDeleteConfirm dialogDeleteConfirm = new DialogDeleteConfirm();
                Bundle bundle = new Bundle();
                bundle.putString("dataType", "deleteView");
                dialogDeleteConfirm.setArguments(bundle);
                dialogDeleteConfirm.show(getSupportFragmentManager(), "delete");
            }
        });
        return view;
    }
}
