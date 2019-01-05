package com.example.hirorock1103.template_01.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hirorock1103.template_01.DB.MemberManager;
import com.example.hirorock1103.template_01.Member.Member;
import com.example.hirorock1103.template_01.Member.MemberList;
import com.example.hirorock1103.template_01.R;

import java.util.List;

public class FragMemberList extends Fragment {

    private RecyclerView recyclerView;
    private MemberList.MemberAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.f_member_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        MemberManager manager = new MemberManager(getContext());
        List<Member> list = manager.getList();
        adapter = new MemberList.MemberAdapter(list, getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        return view;
    }




}
