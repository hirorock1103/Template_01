package com.design_phantom.hirorock1103.template_01.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.design_phantom.hirorock1103.template_01.Common.Common;
import com.design_phantom.hirorock1103.template_01.DB.MemberManager;
import com.design_phantom.hirorock1103.template_01.Member.Member;
import com.design_phantom.hirorock1103.template_01.R;

import java.util.List;

public class FragMemberList extends Fragment {

    private RecyclerView recyclerView;
    private MyAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.f_member_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        MemberManager manager = new MemberManager(getContext());
        List<Member> list = manager.getList();
        adapter = new MyAdapter(list, getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    /*******************
     * Recycler View class
     *******************/
    //ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView detail;
        TextView createdate;
        ConstraintLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            detail = itemView.findViewById(R.id.detail);
            createdate = itemView.findViewById(R.id.createdate);
            layout = itemView.findViewById(R.id.layout);

        }
    }
    //MyAdapter
    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        //Field
        List<Member> list;
        private Context context;

        //set list
        public void setList(List<Member> list){
            this.list = list;
        }

        //Constructor
        public MyAdapter(List<Member> list, Context context){
            this.list = list;
            this.context = context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_row, viewGroup,false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            Member member = list.get(i);

            myViewHolder.title.setText(member.getName());
            myViewHolder.createdate.setText(member.getCreatedate());

            //set context menu
            registerForContextMenu(myViewHolder.layout);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }


    /********************
     * Context menu
     *****************/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.option_menu_1, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.option1:
                //
                Common.toast(getContext(), "option1 is selected");
                break;

            case R.id.option2:
                Common.toast(getContext(), "option2 is selected");
                break;

        }

        return super.onContextItemSelected(item);
    }
}


