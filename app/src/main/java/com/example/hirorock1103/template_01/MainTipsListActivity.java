package com.example.hirorock1103.template_01;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
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


        //menu
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.title19));

        recyclerView = findViewById(R.id.recycler_view);

        TipsManager manager = new TipsManager(this);
        tipsList = manager.getList();

        adapter = new MyAdapter(tipsList);
        LinearLayoutManager llm = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView detail;
        private TextView stepCount;
        private TextView textCount;
        private TextView photoCount;
        private TextView videoCount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            detail = itemView.findViewById(R.id.detail);
            stepCount = itemView.findViewById(R.id.step_count);
            textCount = itemView.findViewById(R.id.text_count);
            videoCount = itemView.findViewById(R.id.video_count);
            photoCount = itemView.findViewById(R.id.photo_count);
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

            int stepCountNum = 0;
            int textCountNum = 0;
            int photoCountNum = 0;
            int videoCountNum = 0;

            if(contentsList.size() > 0){
                for (TipsContents contents : contentsList){
                    //builder.append(contents.getType() + "(" + contents.getContents() +")\n");

                    switch (contents.getType()){
                        case "step":
                            stepCountNum++;
                            break;
                        case "text":
                            textCountNum++;
                            break;
                        case "photo":
                            photoCountNum++;
                            break;
                        case "movie":
                            videoCountNum++;
                            break;

                    }

                }
            }

            holder.detail.setText("--");
            holder.stepCount.setText(String.valueOf(stepCountNum));
            if(stepCountNum == 0){
                holder.stepCount.setTextColor(Color.GRAY);
            }else{
                holder.stepCount.setTextColor(Color.RED);
            }
            holder.textCount.setText(String.valueOf(textCountNum));
            if(textCountNum == 0){
                holder.textCount.setTextColor(Color.GRAY);
            }else{
                holder.textCount.setTextColor(Color.RED);
            }
            holder.photoCount.setText(String.valueOf(photoCountNum));
            if(photoCountNum == 0){
                holder.photoCount.setTextColor(Color.GRAY);
            }else{
                holder.photoCount.setTextColor(Color.RED);
            }
            holder.videoCount.setText(String.valueOf(videoCountNum));
            if(videoCountNum == 0){
                holder.videoCount.setTextColor(Color.GRAY);
            }else{
                holder.videoCount.setTextColor(Color.RED);
            }





        }

        @Override
        public int getItemCount() {
            return tipsList.size();
        }
    }


}
