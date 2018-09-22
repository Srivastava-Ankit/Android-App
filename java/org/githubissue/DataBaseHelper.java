package org.githubissue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.githubissue.ext.DateModel;
import org.githubissue.ext.Issues;

import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "github.db";
    public static final String TABLE_NAME = "issue_table";
    public static final String COL_1 = "ID_DB_REPO";
    public static final String COL_2 = "ISSUE";
    public static final String COL_3 = "TIME_STAMP";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID_DB_REPO TEXT PRIMARY KEY ,ISSUE TEXT,TIME_STAMP TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String id_db_repo, List<Issues> issue, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id_db_repo);
        Gson gson =  new Gson();
        String data = gson.toJson(issue);
        contentValues.put(COL_2,data);
        contentValues.put(COL_3,date);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public DateModel getAllData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] Cols = {COL_1, COL_2, COL_3};
        String selection = "ID_DB_REPO = ?";
        String[] selectionArgs = {id};
        Cursor res = db.query(TABLE_NAME, Cols, selection,selectionArgs, null, null, null);
        DateModel model = null;

        if(res != null && res.getCount() > 0 && res.moveToNext()){
            model = new DateModel();
            model.id = res.getString(0);
            Gson gson = new Gson();
            model.issue = gson.fromJson(res.getString(1), new TypeToken<List<Issues>>(){}.getType());
            model.time = res.getString(2);
        }
        return model;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

    public boolean updateData(String id_db_repo, String issue, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id_db_repo);
        contentValues.put(COL_2,issue);
        contentValues.put(COL_3,date);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id_db_repo });
        return true;
    }
}
