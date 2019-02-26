package com.example.hirorock1103.template_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hirorock1103.template_01.Dialog.DialogTips;
import com.example.hirorock1103.template_01.Member.Member;
import com.example.hirorock1103.template_01.DB.MemberManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogTips.DialogTipsNoticeListener {

    private CardView card1;
    private CardView card2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set view
        sertviews();



    }

    private void sertviews(){

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //dilog
                DialogTips dialogTips = new DialogTips();
                dialogTips.show(getSupportFragmentManager(), "dialogTips");

            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainSampleActivity.class);
                startActivity(intent);

            }
        });


    }

    @Override
    public void DialogTipsNoticeResult() {
        //recieved
    }
}
