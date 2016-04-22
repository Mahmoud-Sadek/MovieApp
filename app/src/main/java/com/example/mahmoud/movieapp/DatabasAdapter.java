package com.example.mahmoud.movieapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Mahmoud on 4/20/2016.
 */
public class DatabasAdapter {

    DatabaseHelper databaseHelper ;
    Context context;
    public DatabasAdapter(Context context) {
        databaseHelper = new DatabaseHelper(context);
        this.context=context;
    }

    public int deleteData(){
        SQLiteDatabase db=databaseHelper.getWritableDatabase();
        String [] id = {"-1"};
        int count=db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.UID+" <>?", id);
        return count;
    }

    public long insertData (int UID, String POSTER, String TITEL, String DATE, String VOTE, String OVERVIEW ){
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(DatabaseHelper.UID,UID);
        contentValues.put(DatabaseHelper.POSTER,POSTER);
        contentValues.put(DatabaseHelper.TITEL,TITEL);
        contentValues.put(DatabaseHelper.DATE,DATE);
        contentValues.put(DatabaseHelper.VOTE,VOTE);
        contentValues.put(DatabaseHelper.OVERVIEW, OVERVIEW);
        long id = sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
        return id;
    }
//http://api.themoviedb.org/3/movie/278927/videos?api_key=4b38b66fb794d381f8d80f14f82f05bd
    public String getAllData2(){
        String [] results = new String[100];
        int i =0;
        SQLiteDatabase db=databaseHelper.getWritableDatabase();
        String [] columns={DatabaseHelper.UID , DatabaseHelper. POSTER,DatabaseHelper.TITEL,DatabaseHelper.DATE,DatabaseHelper.VOTE,DatabaseHelper.OVERVIEW};
        Cursor cursor=db.query( DatabaseHelper.TABLE_NAME, columns, null, null, null,null,null);
        StringBuffer buffer=new StringBuffer();
        while (cursor.moveToNext()){
            int cid=cursor.getInt(0);
            String POSTER=cursor.getString(1);
            String TITEL=cursor.getString(2);
            String DATE=cursor.getString(3);
            String VOTE=cursor.getString(4);
            String OVERVIE=cursor.getString(5);
            buffer.append(cid+" "+ POSTER+" "+TITEL+"\n"+ DATE+" "+OVERVIE+"\n"+ VOTE);
            results[i] = POSTER+"\"t\""+TITEL+"\"d\""+DATE +"\"v\""+VOTE+"\"o\""+OVERVIE+"\"d\""+cid;
            i++;

        }

        return buffer.toString();
//        return results;
    }
    private ArrayList<String> movieArrayList = null;
    public ArrayList<String> getAllData(){
        int i =0;
        SQLiteDatabase db=databaseHelper.getWritableDatabase();
        String [] columns={DatabaseHelper.UID , DatabaseHelper. POSTER,DatabaseHelper.TITEL,DatabaseHelper.DATE,DatabaseHelper.VOTE,DatabaseHelper.OVERVIEW};
        Cursor cursor=db.query( DatabaseHelper.TABLE_NAME, columns, null, null, null,null,null);
        movieArrayList = new ArrayList<>();
        String movieStr;
        while (cursor.moveToNext()){
            int cid=cursor.getInt(0);
            String POSTER=cursor.getString(1);
            String TITEL=cursor.getString(2);
            String DATE=cursor.getString(3);
            String VOTE=cursor.getString(4);
            String OVERVIE=cursor.getString(5);
            movieStr = POSTER+"\"t\""+TITEL+"\"d\""+DATE +"\"v\""+VOTE+"\"o\""+OVERVIE+"\"d\""+cid;
            movieArrayList.add(movieStr);
            i++;

        }
        Toast.makeText(context, i+"", Toast.LENGTH_LONG).show();
//        return buffer.toString();
        return movieArrayList;
    }

    static class DatabaseHelper extends SQLiteOpenHelper{
        private final static String DATABASE_NAME = "firstdatabase";
        private final static int DATABASE_VERSION = 1;
        private final static String TABLE_NAME = "firsttable";
        private final static String UID = "_id";
        private final static String  POSTER = "poster";
        private final static String TITEL = "titel";
        private final static String DATE = "date";
        private final static String VOTE = "vote";
        private final static String OVERVIEW = "overview";
        private final static String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+POSTER+" VARCHAR(255) ,"+TITEL+" VARCHAR(255) ,"+DATE+" VARCHAR(255) ,"+VOTE+" VARCHAR(255) ,"+OVERVIEW+" VARCHAR(255));";
        private final static String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                Toast.makeText(context, "Creating database", Toast.LENGTH_SHORT).show();
                sqLiteDatabase.execSQL(CREATE_TABLE);
                Toast.makeText(context, "database CREATED", Toast.LENGTH_SHORT).show();
            }catch (SQLException e){
                Toast.makeText(context, "Sorry there is Error in create your database \n"+e, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                Toast.makeText(context, "upgrading database", Toast.LENGTH_SHORT).show();
                sqLiteDatabase.execSQL(DROP_TABLE);
                onCreate(sqLiteDatabase);
            }catch (SQLException e)
            {
                Toast.makeText(context, "Sorry there is Error in upgrade your database \n"+e, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

