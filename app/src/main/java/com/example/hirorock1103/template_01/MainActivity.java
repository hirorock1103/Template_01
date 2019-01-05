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
