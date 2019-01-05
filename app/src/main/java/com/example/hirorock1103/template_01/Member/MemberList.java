package com.example.hirorock1103.template_01.Member;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hirorock1103.template_01.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MemberList {

    public static class MemberViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView detail;
        TextView createdate;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            detail = itemView.findViewById(R.id.detail);
            createdate = itemView.findViewById(R.id.createdate);

        }
    }

    public static class MemberAdapter extends RecyclerView.Adapter<MemberViewHolder>{

        List<Member> list;
        private Context context;

        public void setList(List<Member> list){
            this.list = list;
        }


        public MemberAdapter(List<Member> list, Context context){
            this.list = list;
            this.context = context;
        }



        @NonNull
        @Override
        public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_row, viewGroup,false);

            MemberViewHolder holder = new MemberViewHolder(view);

            return holder;

        }

        @Override
        public void onBindViewHolder(@NonNull MemberViewHolder memberViewHolder, int i) {

            Member member = list.get(i);

            memberViewHolder.title.setText(member.getName());
            memberViewHolder.createdate.setText(member.getCreatedate());

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}
