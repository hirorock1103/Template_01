package com.example.hirorock1103.template_01.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

    private final static int DBVERSION = 3;
    private final static String DBNAME = "TipsApp.db";
    protected final static String TABLE_NAME = "Member";
    protected final static String MEMBER_COLUMN_ID = "id";
    protected final static String MEMBER_COLUMN_NAME = "name";
    protected final static String MEMBER_COLUMN_AGE = "age";
    protected final static String MEMBER_COLUMN_PROFILE_IMAGE = "profile_image";
    protected final static String MEMBER_COLUMN_CREATEDATE = "createdate";

    //Tips
    protected final static String TABLE_TIPS = "Tips";
    protected final static String TIPS_COLUMN_ID = "id";
    protected final static String TIPS_COLUMN_TITLE = "title";
    protected final static String TIPS_COLUMN_CREATEDATE = "createdate";

    //tipsContents
    protected final static String TABLE_TIPS_CONTENTS ="TipsContents";
    protected final static String TIPS_CONTENTS_COLUMN_ID = "id";
    protected final static String TIPS_CONTENTS_COLUMN_TYPE ="type";
    protected final static String TIPS_CONTENTS_COLUMN_CONTENTS = "contents";
    protected final static String TIPS_CONTENTS_COLUMN_MOVIE_PATH = "movie_path";
    protected final static String TIPS_CONTENTS_COLUMN_IMAGE ="image";
    protected final static String TIPS_CONTENTS_COLUMN_CREATEDATE="createdate";

    //tag
    protected final static String TABLE_TAG ="Tag";
    protected final static String TAG_COLUMN_ID = "id";
    protected final static String TAG_COLUMN_TITLE="title";

    //tipsTag
    protected final static String TABLE_TIPS_TAG_MTRX ="TipsTag";
    protected final static String TIPS_TAG_MTRX_COLUMN_ID = "id";
    protected final static String TIPS_TAG_MTRX_COLUMN_TIPS_ID="tips_id";
    protected final static String TIPS_TAG_MTRX_COLUMN_TAG_ID="tag_id";



    public MyDbHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                MEMBER_COLUMN_ID + " integer primary key autoincrement ," +
                MEMBER_COLUMN_NAME + " text ," +
                MEMBER_COLUMN_AGE + " intger," +
                MEMBER_COLUMN_PROFILE_IMAGE + " blob," +
                MEMBER_COLUMN_CREATEDATE + " text" +
                ")" ;

        sqLiteDatabase.execSQL(query);


        ////create

        query ="CREATE TABLE IF NOT EXISTS " + TABLE_TIPS_CONTENTS +"("+
                TIPS_CONTENTS_COLUMN_ID +" integer primary key autoincrement, " +
                TIPS_CONTENTS_COLUMN_TYPE +" text, " +
                TIPS_CONTENTS_COLUMN_CONTENTS +" text, " +
                TIPS_CONTENTS_COLUMN_MOVIE_PATH +" text, " +
                TIPS_CONTENTS_COLUMN_IMAGE +" blob, " +
                TIPS_CONTENTS_COLUMN_CREATEDATE+" text " +
                ")";

        sqLiteDatabase.execSQL(query);

        query ="CREATE TABLE IF NOT EXISTS " + TABLE_TAG +"("+
                TAG_COLUMN_ID +" integer primary key autoincrement, " +
                TAG_COLUMN_TITLE+" text " +
                ")";

        sqLiteDatabase.execSQL(query);

        query ="CREATE TABLE IF NOT EXISTS " + TABLE_TIPS +"("+
                TIPS_COLUMN_ID +" integer primary key autoincrement, " +
                TIPS_COLUMN_TITLE +" text, " +
                TIPS_COLUMN_CREATEDATE +" text " +
                ")";

        sqLiteDatabase.execSQL(query);

        query ="CREATE TABLE IF NOT EXISTS " + TABLE_TIPS_TAG_MTRX +"("+
                TIPS_TAG_MTRX_COLUMN_ID +" integer primary key autoincrement, " +
                TIPS_TAG_MTRX_COLUMN_TIPS_ID+" integer , " +
                TIPS_TAG_MTRX_COLUMN_TAG_ID+" integer " +
                ")";

        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(query);

        query = "DROP TABLE IF EXISTS " + TABLE_TIPS;
        sqLiteDatabase.execSQL(query);

        query = "DROP TABLE IF EXISTS " + TABLE_TIPS_CONTENTS;
        sqLiteDatabase.execSQL(query);

        query = "DROP TABLE IF EXISTS " + TABLE_TAG;
        sqLiteDatabase.execSQL(query);

        query = "DROP TABLE IF EXISTS " + TABLE_TIPS_TAG_MTRX;
        sqLiteDatabase.execSQL(query);

        onCreate(sqLiteDatabase);
    }
}
