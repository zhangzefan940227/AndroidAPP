package com.zzfan.Class;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zzfan.SQLite.DatabaseHelper;

import java.util.ArrayList;


public class UserService {

    private ArrayList<String> usernameList = new ArrayList<>();
    private DatabaseHelper dbHelper;
    private String s = null;

    public UserService(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    //登录用
    public boolean login(String uname, String pwd) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql="select * from user where username=? and password=?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{uname, pwd});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }
}