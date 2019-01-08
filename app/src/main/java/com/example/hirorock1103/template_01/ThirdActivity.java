package com.example.hirorock1103.template_01;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.hirorock1103.template_01.Dialog.DialogSourceView;

public class ThirdActivity extends AppCompatActivity {

    private Button sample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //set backbutton to action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        sample = findViewById(R.id.sample);
        sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //oncreate dialog
                createDialog();
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
