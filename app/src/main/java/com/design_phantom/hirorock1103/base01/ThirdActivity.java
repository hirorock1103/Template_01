package com.design_phantom.hirorock1103.base01;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.design_phantom.hirorock1103.base01.Dialog.DialogSourceView;

public class ThirdActivity extends AppCompatActivity {

    private Button sample;
    private LinearLayout layoutBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.design_phantom.hirorock1103.base01.R.layout.activity_third);

        //set backbutton to action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        sample = findViewById(com.design_phantom.hirorock1103.base01.R.id.sample);
        sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //oncreate dialog
                createDialog();
            }
        });

        layoutBt = findViewById(com.design_phantom.hirorock1103.base01.R.id.sample5);
        layoutBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //
    public void createDialog(){
        DialogSourceView dialog = new DialogSourceView();
        dialog.show(getSupportFragmentManager(), "SourceCodeDialog");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
