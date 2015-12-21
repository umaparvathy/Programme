package com.uv.programme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by venkatsr on 21/11/15.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "USER_DATABASE.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "USER";
    private static final String COL_USER_NAME = "USER_NAME";
    private static final String COL_USER_PLACE = "PLACE";
    private static final String COL_USER_ID = "USER_ID";

    SQLiteDatabase database;

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.e("DBHelper", "Constructor executed");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("DBHelper", "Table Creation");
        String create_table_ddl = "CREATE TABLE " + TABLE_NAME + "(" + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_USER_NAME + " TEXT, " + COL_USER_PLACE + " TEXT);";
        db.execSQL(create_table_ddl);
        Log.e("DBHelper", "Table Creation done" + create_table_ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertUserRecord(User user) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, user.getName());
        values.put(COL_USER_PLACE, user.getPlace());
        database.insert(TABLE_NAME, null, values);
        database.close();
    }
    public void updateUserRecord(int userId, User newUser) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, newUser.getName());
        values.put(COL_USER_PLACE, newUser.getPlace());
        database.update(TABLE_NAME, values, COL_USER_ID + "=" + userId, null);
        database.close();
    }

    private boolean deleteUserRecord(int userId) {
        boolean deleted = false;

        return deleted;
    }

    public User getUser(int userId) {

        String searchUserQuery = "select " + COL_USER_ID + ", " + COL_USER_NAME + ", " + COL_USER_PLACE + " from " + TABLE_NAME + " where " + COL_USER_ID + " = '" + userId + "'";

        Log.e("DBHelper", "Select Query: " + searchUserQuery);
        User user = null;
        database = getReadableDatabase();
        Log.e("DBHelper", "Before rawQuery");
        Cursor cursor = database.rawQuery(searchUserQuery, null);
        Log.e("DBHelper", "After rawQuery: " + cursor.getCount());

        if(cursor.getCount() >0 && cursor.moveToFirst()) {
            Log.e("DBHelper", "Inside if");
            do {
                Log.e("DBHelper", "Inside do-while");
                String userName = cursor.getString(cursor.getColumnIndex(COL_USER_NAME));
                String userPlace = cursor.getString(cursor.getColumnIndex(COL_USER_PLACE));
                int id = cursor.getInt(cursor.getColumnIndex(COL_USER_ID));
                user = new User(id, userName, userPlace);
                break;
            } while (false);
        } else {
            Log.w("SQLiteHelper", "No records available");
        }
        Log.e("DBHelper", "Done");
        cursor.close();
        database.close();
        return user;
    }

    public User getAUser(String searchUserName) {

        String searchUserQuery = null;
        //String searchUsersQuery = "select " + COL_USER_NAME + ", " + COL_USER_PLACE + ", " + COL_USER_PHOTO + " from " + TABLE_NAME + " where " + COL_USER_NAME + " = '" + searchUserName + "'";
        //String searchUsersQuery = "select " + COL_USER_NAME + ", " + COL_USER_PLACE + " from " + TABLE_NAME + " where " + COL_USER_NAME + " = '" + searchUserName + "'";

        if(searchUserName == null) {
            searchUserQuery = "select " + COL_USER_ID + ", " + COL_USER_NAME + ", " + COL_USER_PLACE + " from " + TABLE_NAME;
        } else {
            searchUserQuery = "select " + COL_USER_ID + ", " + COL_USER_NAME + ", " + COL_USER_PLACE + " from " + TABLE_NAME + " where " + COL_USER_NAME + " = '" + searchUserName + "'";
        }
        Log.e("DBHelper", "Select Query: " + searchUserQuery);
        User user = null;
        database = getReadableDatabase();
        Log.e("DBHelper", "Before rawQuery");
        Cursor cursor = database.rawQuery(searchUserQuery, null);
        Log.e("DBHelper", "After rawQuery: " + cursor.getCount());

        if(cursor.getCount() >0 && cursor.moveToFirst()) {
            Log.e("DBHelper", "Inside if");
            do {
                Log.e("DBHelper", "Inside do-while");
                String userName = cursor.getString(cursor.getColumnIndex(COL_USER_NAME));
                String userPlace = cursor.getString(cursor.getColumnIndex(COL_USER_PLACE));
                int id = cursor.getInt(cursor.getColumnIndex(COL_USER_ID));
                user = new User(id, userName, userPlace);
                break;
            } while (false);
        } else {
            Log.w("SQLiteHelper", "No records available");
        }
        Log.e("DBHelper", "Done");
        cursor.close();
        database.close();
        return user;
    }

    public User getCurrentUser() {

        String searchUsersQuery = "select " + COL_USER_ID + ", " + COL_USER_NAME + ", " + COL_USER_PLACE + " from " + TABLE_NAME;

        Log.e("DBHelper", "Select Query: " + searchUsersQuery);
        User user = null;
        database = getReadableDatabase();
        Log.e("DBHelper", "Before rawQuery");
        Cursor cursor = database.rawQuery(searchUsersQuery, null);
        Log.e("DBHelper", "After rawQuery: " + cursor.getCount());

        if(cursor.getCount() >0 && cursor.moveToFirst()) {
            Log.e("DBHelper", "Inside if");
            do {
                Log.e("DBHelper", "Inside do-while");
                String userName = cursor.getString(cursor.getColumnIndex(COL_USER_NAME));
                String userPlace = cursor.getString(cursor.getColumnIndex(COL_USER_PLACE));
                Integer id = cursor.getInt(cursor.getColumnIndex(COL_USER_ID));
                user = new User(id, userName, userPlace);
            } while (false);
        } else {
            Log.w("SQLiteHelper", "No records available");
        }
        Log.e("DBHelper", "Done");
        cursor.close();
        database.close();
        return user;
    }


    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<User>();


        String searchUsersQuery = "select " + COL_USER_ID + ", " + COL_USER_NAME + ", " + COL_USER_PLACE + " from " + TABLE_NAME;

        Log.e("DBHelper", "Select Query: " + searchUsersQuery);
        User user = null;
        database = getReadableDatabase();
        Log.e("DBHelper", "Before rawQuery");
        Cursor cursor = database.rawQuery(searchUsersQuery, null);
        Log.e("DBHelper", "After rawQuery: " + cursor.getCount());

        if(cursor.getCount() >0 && cursor.moveToFirst()) {
            Log.e("DBHelper", "Inside if");
            do {
                Log.e("DBHelper", "Inside do-while");
                String userName = cursor.getString(cursor.getColumnIndex(COL_USER_NAME));
                String userPlace = cursor.getString(cursor.getColumnIndex(COL_USER_PLACE));
                int id = cursor.getInt(cursor.getColumnIndex(COL_USER_ID));
                user = new User(id, userName, userPlace);
                users.add(user);
            } while (cursor.moveToNext());
        } else {
            Log.w("SQLiteHelper", "No records available");
        }
        Log.e("DBHelper", "Done");
        cursor.close();
        database.close();
        Log.e("DBHelper", "Number of users: " + users.size());
        return users;
    }

    public String[] getAllUserNames() {
        ArrayList<String> allUserNames = new ArrayList<String>();
        database = getReadableDatabase();
        Log.e("DBHelper", "Before rawQuery");
        String selectProductsQuery = "select " + COL_USER_ID + ", " + COL_USER_NAME + " from " + TABLE_NAME;
        Cursor cursor = database.rawQuery(selectProductsQuery, null);
        Log.e("DBHelper", "After rawQuery: " + cursor.getCount());
        /*String[] columnNames = cursor.getColumnNames();
        for(int i=0;i<columnNames.length; i++) {
            Log.e("SQLiteHelper-Column name: ", columnNames[i]);
        }*/
        if(cursor.getCount() >0 && cursor.moveToFirst()) {
            Log.e("DBHelper", "Inside if");
            do {
                Log.e("DBHelper", "Inside do-while");
                String userName = cursor.getString(cursor.getColumnIndex("USER_NAME"));
                allUserNames.add(userName);
            } while (cursor.moveToNext());
        } else {
            Log.w("SQLiteHelper", "No records available");
        }
        Log.e("DBHelper", "Done");
        cursor.close();
        database.close();
        String[] stringArray = allUserNames.toArray(new String[0]);
        return stringArray;
    }

    public boolean isTableExists(String tableName, boolean openDb) {
        if(openDb) {
            if(database == null || !database.isOpen()) {
                database = getReadableDatabase();
            }

            if(!database.isReadOnly()) {
                database.close();
                database = getReadableDatabase();
            }
        }

        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
}