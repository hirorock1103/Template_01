package com.design_phantom.hirorock1103.template_01.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.design_phantom.hirorock1103.template_01.Common.Common;
import com.design_phantom.hirorock1103.template_01.Master.TipsContents;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TipsContentsManager extends MyDbHelper {
    public TipsContentsManager(Context context) {
        super(context);
    }

    //get all list
    public List<TipsContents> getList(){

        List<TipsContents> list = new ArrayList<>();

        String query ="SELECT * FROM " + TABLE_TIPS_CONTENTS + " ORDER BY " + TIPS_CONTENTS_COLUMN_ID + " ASC";

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            TipsContents contents = new TipsContents();
            contents.setId(c.getInt(c.getColumnIndex(TIPS_CONTENTS_COLUMN_ID)));
            contents.setType(c.getString(c.getColumnIndex(TIPS_CONTENTS_COLUMN_TYPE)));
            contents.setTipsId(c.getInt(c.getColumnIndex(TIPS_CONTENTS_COLUMN_TIPS_ID)));
            contents.setContents(c.getString(c.getColumnIndex(TIPS_CONTENTS_COLUMN_CONTENTS)));
            contents.setImage(c.getBlob(c.getColumnIndex(TIPS_CONTENTS_COLUMN_IMAGE)));
            contents.setMoviePath(c.getString(c.getColumnIndex(TIPS_CONTENTS_COLUMN_MOVIE_PATH)));
            contents.setCreatedate(c.getString(c.getColumnIndex(TIPS_CONTENTS_COLUMN_CREATEDATE)));

            list.add(contents);
            c.moveToNext();

        }


        return list;


    }

    //get by tips id
    public List<TipsContents> getListByTipsId(int tipsId){

        List<TipsContents> list = new ArrayList<>();

        String query ="SELECT * FROM " + TABLE_TIPS_CONTENTS
                + " WHERE " + TIPS_CONTENTS_COLUMN_TIPS_ID + " = " + tipsId
                + " ORDER BY " + TIPS_CONTENTS_COLUMN_ID + " ASC";

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){

            TipsContents contents = new TipsContents();
            contents.setId(c.getInt(c.getColumnIndex(TIPS_CONTENTS_COLUMN_ID)));
            contents.setType(c.getString(c.getColumnIndex(TIPS_CONTENTS_COLUMN_TYPE)));
            contents.setTipsId(c.getInt(c.getColumnIndex(TIPS_CONTENTS_COLUMN_TIPS_ID)));
            contents.setContents(c.getString(c.getColumnIndex(TIPS_CONTENTS_COLUMN_CONTENTS)));
            contents.setImage(c.getBlob(c.getColumnIndex(TIPS_CONTENTS_COLUMN_IMAGE)));
            contents.setMoviePath(c.getString(c.getColumnIndex(TIPS_CONTENTS_COLUMN_MOVIE_PATH)));
            contents.setCreatedate(c.getString(c.getColumnIndex(TIPS_CONTENTS_COLUMN_CREATEDATE)));

            list.add(contents);
            c.moveToNext();

        }


        return list;


    }

    public TipsContents getListById(int id){

        String query ="SELECT * FROM " + TABLE_TIPS_CONTENTS + " WHERE " + TIPS_CONTENTS_COLUMN_ID + " = " + id;

        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        TipsContents contents = new TipsContents();

        while(!c.isAfterLast()){


            contents.setId(c.getInt(c.getColumnIndex(TIPS_CONTENTS_COLUMN_ID)));
            contents.setType(c.getString(c.getColumnIndex(TIPS_CONTENTS_COLUMN_TYPE)));
            contents.setTipsId(c.getInt(c.getColumnIndex(TIPS_CONTENTS_COLUMN_TIPS_ID)));
            contents.setContents(c.getString(c.getColumnIndex(TIPS_CONTENTS_COLUMN_CONTENTS)));
            contents.setImage(c.getBlob(c.getColumnIndex(TIPS_CONTENTS_COLUMN_IMAGE)));
            contents.setMoviePath(c.getString(c.getColumnIndex(TIPS_CONTENTS_COLUMN_MOVIE_PATH)));
            contents.setCreatedate(c.getString(c.getColumnIndex(TIPS_CONTENTS_COLUMN_CREATEDATE)));

            c.moveToNext();

        }


        return contents;
    }

    public long addTipsContents(TipsContents contents){

        long resultId = 0;

        ContentValues values= new ContentValues();
        values.put(TIPS_CONTENTS_COLUMN_TYPE, contents.getType());
        values.put(TIPS_CONTENTS_COLUMN_TIPS_ID, contents.getTipsId());
        values.put(TIPS_CONTENTS_COLUMN_CONTENTS, contents.getContents());
        values.put(TIPS_CONTENTS_COLUMN_IMAGE, contents.getImage());
        values.put(TIPS_CONTENTS_COLUMN_MOVIE_PATH, contents.getMoviePath());
        values.put(TIPS_CONTENTS_COLUMN_CREATEDATE, Common.formatDate(new Date(), Common.DB_DATE_FORMAT));

        SQLiteDatabase db = getWritableDatabase();
        resultId = db.insert(TABLE_TIPS_CONTENTS, null, values);

        return resultId;

    }

    public long updateTipsContents(TipsContents contents){

        long resultId = 0;

        ContentValues values= new ContentValues();
        values.put(TIPS_CONTENTS_COLUMN_TYPE, contents.getType());
        values.put(TIPS_CONTENTS_COLUMN_TIPS_ID, contents.getTipsId());
        values.put(TIPS_CONTENTS_COLUMN_CONTENTS, contents.getContents());
        values.put(TIPS_CONTENTS_COLUMN_IMAGE, contents.getImage());
        values.put(TIPS_CONTENTS_COLUMN_MOVIE_PATH, contents.getMoviePath());

        SQLiteDatabase db = getWritableDatabase();

        String[] args = {String.valueOf(contents.getId())};
        String where = TIPS_COLUMN_ID + " = ?";

        resultId = db.update(TABLE_TIPS_CONTENTS, values, where, args);

        return resultId;

    }

    public void delete(int id){

        String query = "DELETE FROM " + TABLE_TIPS_CONTENTS + " WHERE " + TIPS_CONTENTS_COLUMN_ID + " = " + id;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);

    }

}
