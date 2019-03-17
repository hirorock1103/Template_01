package com.design_phantom.hirorock1103.base01.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.design_phantom.hirorock1103.base01.Common.Common;
import com.design_phantom.hirorock1103.base01.Master.Member;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberManager extends MyDbHelper {
    public MemberManager(Context context) {
        super(context);
    }

    public long addMember(Member member){

        ContentValues values = new ContentValues();
        values.put(MEMBER_COLUMN_NAME, member.getName());
        values.put(MEMBER_COLUMN_AGE, member.getAge());
        values.put(MEMBER_COLUMN_CREATEDATE, Common.formatDate(new Date(), Common.DB_DATE_FORMAT));

        SQLiteDatabase db = getWritableDatabase();
        long inertId = db.insert(TABLE_NAME, null, values);

        return inertId;

    }

    public List<Member> getList(){

        List<Member> list = new ArrayList<>();

        String query = " SELECT * FROM " + TABLE_NAME + " ORDER BY " + MEMBER_COLUMN_ID + " DESC ";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();

        while(!c.isAfterLast()){
            Member member = new Member();

            member.setName(c.getString(c.getColumnIndex(MEMBER_COLUMN_NAME)));
            member.setAge(c.getInt(c.getColumnIndex(MEMBER_COLUMN_AGE)));
            member.setCreatedate(c.getString(c.getColumnIndex(MEMBER_COLUMN_CREATEDATE)));

            list.add(member);

            c.moveToNext();
        }

        return list;
    }

}
