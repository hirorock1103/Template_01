package com.example.hirorock1103.template_01;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hirorock1103.template_01.DB.TipsGroupManager;
import com.example.hirorock1103.template_01.Dialog.DialogGroup;
import com.example.hirorock1103.template_01.Master.JoinedData;

import java.util.List;

public class MainGroupListActivity extends AppCompatActivity implements DialogGroup.DialogGroupNoticeListener {

    //data set
    private List<JoinedData.GroupCount> groupList;

    //view
    private RecyclerView recyclerView;
    private FloatingActionButton fb;

    //adapter
    private MyAdapter adapter;

    //manager
    private TipsGroupManager groupManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list_template01);

        //menu
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.title18));

        //set view
        recyclerView = findViewById(R.id.recycler_view);
        fb = findViewById(R.id.fb);

        //set listener
        setListener();

        //set data
        groupManager = new TipsGroupManager(this);
        groupList = groupManager.getGroupCountList();

        //use for test
        //setTestData();

        adapter = new MyAdapter(groupList);
        GridLayoutManager llm = new GridLayoutManager(this, 2);
        llm.setSmoothScrollbarEnabled(true);
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

    private void setTestData(){

        JoinedData.GroupCount count1 = new JoinedData.GroupCount();
        count1.setGroupName("AndroidTips");
        count1.setCount(13);

        JoinedData.GroupCount count2 = new JoinedData.GroupCount();
        count2.setGroupName("HTMLTips");
        count2.setCount(88);

        groupList.add(count1);
        groupList.add(count2);

    }


    private void setListener(){
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialog
                DialogGroup dialogGroup = new DialogGroup();
                dialogGroup.show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    @Override
    public void DialogGroupNoticeResult() {
        //reload view
        reloadView();
    }

    private void reloadView(){

        groupList = groupManager.getGroupCountList();
        adapter.setGroupList(groupList);
        adapter.notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView count;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            count = itemView.findViewById(R.id.tips_count);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        //list
        List<JoinedData.GroupCount> groupList;

        public MyAdapter(List<JoinedData.GroupCount> groupList){
            this.groupList = groupList;
        }

        public void setGroupList(List<JoinedData.GroupCount> groupList){
            this.groupList = groupList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(MainGroupListActivity.this).inflate(R.layout.item_row_group, viewGroup, false);
            return new MyViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

            JoinedData.GroupCount groupCount = groupList.get(i);

            holder.title.setText(groupCount.getGroupName());
            holder.count.setText(String.valueOf(groupCount.getCount()));

        }

        @Override
        public int getItemCount() {
            return groupList.size();
        }
    }



}
