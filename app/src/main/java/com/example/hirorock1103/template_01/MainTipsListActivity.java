package com.example.hirorock1103.template_01;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hirorock1103.template_01.DB.TipsContentsManager;
import com.example.hirorock1103.template_01.DB.TipsManager;
import com.example.hirorock1103.template_01.Master.Tips;
import com.example.hirorock1103.template_01.Master.TipsContents;

import java.util.ArrayList;
import java.util.List;

public class MainTipsListActivity extends AppCompatActivity {

    private List<Tips> tipsList;
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list_template01);

        recyclerView = findViewById(R.id.recycler_view);

        TipsManager manager = new TipsManager(this);
        tipsList = manager.getList();

        adapter = new MyAdapter(tipsList);
        LinearLayoutManager llm = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView detail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            detail = itemView.findViewById(R.id.detail);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private List<Tips> tipsList;

        public MyAdapter(List<Tips> tipsList){
            this.tipsList = tipsList;
        }

        public void setList(List<Tips> tipsList){
            this.tipsList = tipsList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(MainTipsListActivity.this).inflate(R.layout.item_row, viewGroup,false);
            return new MyViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

            Tips tips = tipsList.get(i);
            holder.title.setText(tips.getTipsTitle());

            //has detail
            TipsContentsManager manager = new TipsContentsManager(MainTipsListActivity.this);
            List<TipsContents> contentsList = manager.getListByTipsId(tips.getTipsId());

            if(contentsList.size() > 0){
                StringBuilder builder = new StringBuilder();
                for (TipsContents contents : contentsList){
                    builder.append(contents.getType() + "(" + contents.getContents() +")\n");
                }
                holder.detail.setText(builder.toString());
            }else{
                holder.detail.setText(getString(R.string.errorMsg2));
            }





        }

        @Override
        public int getItemCount() {
            return tipsList.size();
        }
    }


}
