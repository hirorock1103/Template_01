package com.example.hirorock1103.template_01;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.DB.TipsContentsManager;
import com.example.hirorock1103.template_01.DB.TipsGroupManager;
import com.example.hirorock1103.template_01.DB.TipsManager;
import com.example.hirorock1103.template_01.Dialog.DialogDeleteConfirm;
import com.example.hirorock1103.template_01.Dialog.DialogTips;
import com.example.hirorock1103.template_01.Master.Tips;
import com.example.hirorock1103.template_01.Master.TipsContents;
import com.example.hirorock1103.template_01.Master.TipsGroup;

import java.util.List;

public class MainTipsListActivity extends AppCompatActivity
        implements DialogDeleteConfirm.DialogDeleteNoticeListener,DialogTips.DialogTipsNoticeListener {

    private int groupId = 0;

    private List<Tips> tipsList;
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    private int selectedItemid;

    //float button
    private FloatingActionButton fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list_template01);

        String groupName = "";
        try{
            groupId = getIntent().getExtras().getInt("groupId");
            if(groupId > 0){
                TipsGroupManager tipsGroupManager = new TipsGroupManager(this);
                TipsGroup group = tipsGroupManager.getListById(groupId);
                groupName = group.getGroupName();
            }
        }catch (Exception e){
            Common.log(e.getMessage());
            groupId = 0;
        }

        //menu
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle(groupName != null && groupName.isEmpty() == false ? getString(R.string.title19) + "(" + groupName + ")" : getString(R.string.title19));

        fb = findViewById(R.id.fb);
        recyclerView = findViewById(R.id.recycler_view);

        setListener();

        TipsManager manager = new TipsManager(this);
        tipsList = manager.getList(groupId);

        adapter = new MyAdapter(tipsList);
        LinearLayoutManager llm = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);


    }

    private void setListener(){
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                DialogTips dialogTips = new DialogTips();
                dialogTips.show(getSupportFragmentManager(), "dialog");
                */
                Intent intent = new Intent(MainTipsListActivity.this, MainTipsAddActivity.class);
                startActivity(intent);
            }
        });
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

    @Override
    public void deleteResultNotice(int order) {
        TipsManager manager = new TipsManager(this);
        tipsList = manager.getList(groupId);
        adapter.setList(tipsList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void DialogTipsNoticeResult() {
        //result from tips
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView detail;
        private TextView createdate;
        private ImageView btMore;
        private TextView stepCount;
        private TextView textCount;
        private TextView photoCount;
        private TextView videoCount;
        private ConstraintLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            createdate = itemView.findViewById(R.id.createdate);
            detail = itemView.findViewById(R.id.detail);
            btMore = itemView.findViewById(R.id.more);
            stepCount = itemView.findViewById(R.id.step_count);
            textCount = itemView.findViewById(R.id.text_count);
            videoCount = itemView.findViewById(R.id.video_count);
            photoCount = itemView.findViewById(R.id.photo_count);
            layout = itemView.findViewById(R.id.layout);
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
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {

            final Tips tips = tipsList.get(i);
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

            holder.createdate.setText(tips.getCreatedate());

            if(tips.getGroupId() > 0){
                TipsGroupManager groupManager = new TipsGroupManager(MainTipsListActivity.this);
                TipsGroup group = groupManager.getListById(tips.getGroupId());
                holder.detail.setText(group.getGroupName() == null ? "group:取得失敗" : "group:" + group.getGroupName());
            }else{
                holder.detail.setText("group:--");
            }

            holder.btMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.showContextMenu();
                }
            });
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainTipsListActivity.this, MainTipsAddActivity.class);
                    intent.putExtra("id", tips.getTipsId());
                    startActivity(intent);
                }
            });
            holder.layout.setTag(String.valueOf(tips.getTipsId()));
            registerForContextMenu(holder.layout);

        }

        @Override
        public int getItemCount() {
            return tipsList.size();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        selectedItemid = Integer.parseInt(v.getTag().toString());
        getMenuInflater().inflate(R.menu.option_menu_1, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.option1:

                Intent intent = new Intent(MainTipsListActivity.this, MainTipsAddActivity.class);
                intent.putExtra("id", selectedItemid);
                startActivity(intent);

                return true;

            case R.id.option2:

                DialogDeleteConfirm deleteConfirm = new DialogDeleteConfirm();
                Bundle bundle = new Bundle();
                bundle.putString("dataType", "tips");
                bundle.putInt("id", selectedItemid);
                deleteConfirm.setArguments(bundle);
                deleteConfirm.show(getSupportFragmentManager(), "dialog");

                return true;

        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TipsManager manager = new TipsManager(this);
        tipsList = manager.getList(groupId);
        adapter.setList(tipsList);
        adapter.notifyDataSetChanged();

    }
}
