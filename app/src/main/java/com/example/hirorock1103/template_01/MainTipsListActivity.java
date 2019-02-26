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

import com.example.hirorock1103.template_01.Master.Tips;

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

        Tips tips1 = new Tips();
        tips1.setTipsId(1);
        tips1.setTipsTitle("test1");

        tipsList = new ArrayList<>();
        tipsList.add(tips1);

        adapter = new MyAdapter(tipsList);
        LinearLayoutManager llm = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
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
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        }

        @Override
        public int getItemCount() {
            return tipsList.size();
        }
    }


}
