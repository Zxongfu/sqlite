package com.fu.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;

public class SqlHelper extends SQLiteOpenHelper {

    private static final String databasename = "company.db";
    private String table_name = "employee";
    private static final int version = 1;
    private SQLiteDatabase database;

    private String column_id = "id", column_name = "name";

    public SqlHelper(@Nullable Context context) {
        super(context, databasename, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String database_create = "create table " + table_name + "("
                + column_id + " INTEGER PRIMARY KEY autoincrement, " +
                column_name + " text not null)";
        db.execSQL(database_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgrade = "Drop table if exists " + table_name;
        db.execSQL(upgrade);
    }

    public boolean insertdata(String name) {
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_name, name);
        long result = database.insert(table_name, null, contentValues);
        if (result == -1) {
            return false;
        }
        return true;
    }

    public boolean update(String name, Integer id) {
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_name, name);
        database.update(table_name, contentValues, column_id + "=" + id, null);
        return true;
    }


    public boolean delete(Integer id) {
        database = this.getWritableDatabase();
        database.delete(table_name, column_id + "=" + id, null);
        return true;

    }
}
