package com.example.hirorock1103.template_01.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hirorock1103.template_01.Common.Common;
import com.example.hirorock1103.template_01.Master.Tips;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TipsManager extends MyDbHelper {
    public TipsManager(Context context) {
        super(context);
    }

    //get all list
    public List<Tips> getList(){

        List<Tips> list = new ArrayList<>();

        String query ="SELECT * FROM " + TABLE_TIPS + " ORDER BY " + TIPS_COLUMN_ID + " ASC";

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            Tips tips = new Tips();

            tips.setTipsId(c.getInt(c.getColumnIndex(TIPS_COLUMN_ID)));
            tips.setTipsTitle(c.getString(c.getColumnIndex(TIPS_COLUMN_TITLE)));
            tips.setGroupId(c.getInt(c.getColumnIndex(TIPS_COLUMN_GROUP_ID)));
            tips.setCreatedate(c.getString(c.getColumnIndex(TIPS_COLUMN_CREATEDATE)));

            list.add(tips);
            c.moveToNext();

        }

        return list;

    }
    public List<Tips> getList(int groupId){

        List<Tips> list = new ArrayList<>();

        String query ="";

        if(groupId > 0){
            query ="SELECT * FROM " + TABLE_TIPS
                    + " WHERE " + TIPS_COLUMN_GROUP_ID + " = " + groupId
                    + " ORDER BY " + TIPS_COLUMN_ID + " ASC";
        }else{
            query ="SELECT * FROM " + TABLE_TIPS
                    + " ORDER BY " + TIPS_COLUMN_ID + " ASC";
        }

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            Tips tips = new Tips();

            tips.setTipsId(c.getInt(c.getColumnIndex(TIPS_COLUMN_ID)));
            tips.setTipsTitle(c.getString(c.getColumnIndex(TIPS_COLUMN_TITLE)));
            tips.setGroupId(c.getInt(c.getColumnIndex(TIPS_COLUMN_GROUP_ID)));
            tips.setCreatedate(c.getString(c.getColumnIndex(TIPS_COLUMN_CREATEDATE)));

            list.add(tips);
            c.moveToNext();

        }

        return list;

    }

    //get tips
    public Tips getListById(int id){

        Tips tips = new Tips();
        String query ="SELECT * FROM " + TABLE_TIPS + " WHERE " + TIPS_COLUMN_ID + " = " + id;

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            tips.setTipsId(c.getInt(c.getColumnIndex(TIPS_COLUMN_ID)));
            tips.setTipsTitle(c.getString(c.getColumnIndex(TIPS_COLUMN_TITLE)));
            tips.setGroupId(c.getInt(c.getColumnIndex(TIPS_COLUMN_GROUP_ID)));
            tips.setCreatedate(c.getString(c.getColumnIndex(TIPS_COLUMN_CREATEDATE)));

            c.moveToNext();

        }


        return tips;


    }

    //add
    public long addTips(Tips tips){

        long resultId = 0;

        ContentValues values= new ContentValues();
        values.put(TIPS_COLUMN_TITLE, tips.getTipsTitle());
        values.put(TIPS_COLUMN_GROUP_ID, tips.getGroupId());
        values.put(TIPS_COLUMN_CREATEDATE, Common.formatDate(new Date(), Common.DB_DATE_FORMAT));

        SQLiteDatabase db = getWritableDatabase();
        resultId = db.insert(TABLE_TIPS, null, values);

        return resultId;

    }


    public long updateTips(Tips tips){

        long resultId = 0;

        ContentValues values= new ContentValues();
        values.put(TIPS_COLUMN_TITLE, tips.getTipsTitle());
        values.put(TIPS_COLUMN_GROUP_ID, tips.getGroupId());
        SQLiteDatabase db = getWritableDatabase();

        String[] args = {String.valueOf(tips.getTipsId())};
        String where = TIPS_COLUMN_ID + " = ?";

        resultId = db.update(TABLE_TIPS, values, where, args);

        return resultId;

    }


    public void delete(int id){

        String query = "DELETE FROM " + TABLE_TIPS +  " WHERE " + TIPS_COLUMN_ID + " = " + id;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);

    }










}
