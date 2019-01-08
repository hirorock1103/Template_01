package com.example.hirorock1103.template_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hirorock1103.template_01.Member.Member;
import com.example.hirorock1103.template_01.DB.MemberManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button bt_1;
    private Button bt_2;
    private Button bt_3;
    private Button bt_4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_1 = findViewById(R.id.bt_1);
        bt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        bt_2 = findViewById(R.id.bt_2);
        bt_2.setText("Sample");
        bt_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });

        bt_3 = findViewById(R.id.bt_3);
        bt_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForthActivity.class);
                startActivity(intent);
            }
        });

        bt_4 = findViewById(R.id.bt_4);
        bt_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FifthActivity.class);
                startActivity(intent);
            }
        });


        MemberManager manager = new MemberManager(this);

        Member member = new Member();
        member.setName("johnny");
        member.setAge(23);

        manager.addMember(member);

        List<Member> list = manager.getList();

        for (Member m : list){
            Log.i("INFO","m:" + m.getName());
        }


    }
}
