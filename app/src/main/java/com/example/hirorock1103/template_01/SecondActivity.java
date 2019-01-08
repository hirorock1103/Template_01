package com.example.hirorock1103.template_01;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.Fragments.FragMemberList;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //set backbutton to actionbar
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Fragment fragment = new FragMemberList();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, fragment);
        transaction.commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case android.R.id.home:
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
