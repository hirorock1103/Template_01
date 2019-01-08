package com.example.hirorock1103.template_01;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hirorock1103.template_01.Fragments.FragTest;
import com.example.hirorock1103.template_01.Fragments.FragTop;
import com.example.hirorock1103.template_01.Member.Member;
import com.example.hirorock1103.template_01.DB.MemberManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        if(fragments.size() == 0){
            //Fragment fragment = new FragTest();
            Fragment fragment = new FragTop();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.layout, fragment);
            transaction.commit();
        }


    }
}
