package com.example.hirorock1103.template_01.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.Master.JoinedData;
import com.example.hirorock1103.template_01.Master.Tips;
import com.example.hirorock1103.template_01.Master.TipsGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TipsGroupManager extends MyDbHelper {
    public TipsGroupManager(Context context) {
        super(context);
    }

    //groupのカウントを取得
    public JoinedData.GroupCount getGroupCount(int groupId){

        JoinedData.GroupCount groupCount = new JoinedData.GroupCount();

        String query = "SELECT count(*) as count, " +
                TABLE_GROUP + "." + GROUP_COLUMN_NAME + " as Groupname " +
                " FROM " + TABLE_TIPS +
                " INNER JOIN " + TABLE_GROUP + " ON " + TABLE_TIPS + "." + TIPS_COLUMN_GROUP_ID + " = " + TABLE_GROUP + "." + GROUP_COLUMN_ID +
                " WHERE " + TABLE_GROUP + "." + GROUP_COLUMN_ID + " = " + groupId +
                " GROUP BY " + TABLE_GROUP + "." + GROUP_COLUMN_ID;

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){

            groupCount.setCount(c.getInt(c.getColumnIndex("count")));
            groupCount.setGroupName(c.getString(c.getColumnIndex("Groupname")));
            c.moveToNext();
        }

        return groupCount;
    }

    public List<JoinedData.GroupCount> getGroupCountList(){

        List<JoinedData.GroupCount> list = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();

        List<TipsGroup> groupList = this.getList();

        String query = "";
        for(TipsGroup group : groupList){

            JoinedData.GroupCount groupCount = new JoinedData.GroupCount();

            query = "SELECT count(*) as count, " +
                    TABLE_GROUP + "." + GROUP_COLUMN_NAME + " as Groupname " +
                    " FROM " + TABLE_TIPS +
                    " INNER JOIN " + TABLE_GROUP + " ON " + TABLE_TIPS + "." + TIPS_COLUMN_GROUP_ID + " = " + TABLE_GROUP + "." + GROUP_COLUMN_ID +
                    " WHERE " + TABLE_GROUP + "." + GROUP_COLUMN_ID + " = " + group.getId() +
                    " GROUP BY " + TABLE_GROUP + "." + GROUP_COLUMN_ID;

            Cursor c = db.rawQuery(query, null);
            c.moveToFirst();

            if(c.getCount() > 0){
                while(!c.isAfterLast()){

                    groupCount.setCount(c.getInt(c.getColumnIndex("count")));
                    groupCount.setGroupName(c.getString(c.getColumnIndex("Groupname")));
                    list.add(groupCount);
                    c.moveToNext();
                }
            }else{
                groupCount.setGroupName(group.getGroupName());
                groupCount.setCount(0);
                list.add(groupCount);
            }


        }

        return list;
    }


    public List<TipsGroup> getList(){

        List<TipsGroup> list = new ArrayList<>();

        String query ="SELECT * FROM " + TABLE_GROUP + " ORDER BY " + GROUP_COLUMN_ID + " ASC";

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            TipsGroup group = new TipsGroup();

            group.setId(c.getInt(c.getColumnIndex(GROUP_COLUMN_ID)));
            group.setGroupName(c.getString(c.getColumnIndex(GROUP_COLUMN_NAME)));

            list.add(group);
            c.moveToNext();

        }


        return list;


    }

    public TipsGroup getListById(int id){

        TipsGroup group = new TipsGroup();

        String query ="SELECT * FROM " + TABLE_GROUP + " WHERE " + GROUP_COLUMN_ID + " = " + id;

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            group.setId(c.getInt(c.getColumnIndex(GROUP_COLUMN_ID)));
            group.setGroupName(c.getString(c.getColumnIndex(GROUP_COLUMN_NAME)));
            c.moveToNext();

        }

        return group;

    }


    //add
    public long addGroup(TipsGroup group){

        long resultId = 0;

        ContentValues values= new ContentValues();
        values.put(GROUP_COLUMN_NAME, group.getGroupName());

        SQLiteDatabase db = getWritableDatabase();
        resultId = db.insert(TABLE_GROUP, null, values);

        return resultId;

    }


    public long update(TipsGroup group){

        long resultId = 0;

        ContentValues values= new ContentValues();
        values.put(GROUP_COLUMN_NAME, group.getGroupName());

        SQLiteDatabase db = getWritableDatabase();

        String[] args = {String.valueOf(group.getId())};
        String where = GROUP_COLUMN_ID + " = ?";

        resultId = db.update(TABLE_GROUP, values, where, args);

        return resultId;

    }

    public void delete(int id){

        String query = "DELETE FROM " + TABLE_GROUP +  " WHERE " + GROUP_COLUMN_ID + " = " + id;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);

    }

}
