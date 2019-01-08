package com.example.hirorock1103.template_01.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hirorock1103.template_01.FifthActivity;
import com.example.hirorock1103.template_01.ForthActivity;
import com.example.hirorock1103.template_01.MainActivity;
import com.example.hirorock1103.template_01.R;
import com.example.hirorock1103.template_01.SecondActivity;
import com.example.hirorock1103.template_01.ThirdActivity;

public class FragTest extends Fragment {

    private Button bt_1;
    private Button bt_2;
    private Button bt_3;
    private Button bt_4;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.f_sample, container,false);

        bt_1 = view.findViewById(R.id.bt_1);
        bt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SecondActivity.class);
                startActivity(intent);
            }
        });

        bt_2 = view.findViewById(R.id.bt_2);
        bt_2.setText("Sample");
        bt_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThirdActivity.class);
                startActivity(intent);
            }
        });

        bt_3 = view.findViewById(R.id.bt_3);
        bt_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForthActivity.class);
                startActivity(intent);
            }
        });

        bt_4 = view.findViewById(R.id.bt_4);
        bt_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FifthActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


}
