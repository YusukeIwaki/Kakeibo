package org.yi01.kakeibo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Pay extends AbstractModel {
    public static final String TABLE_NAME = "pay";

    public String itemname;
    public int yen;
    public long datetime;


    public static Pay createUsingCursor(Cursor c) {
        Pay pay = new Pay();

        pay.id = c.getLong(c.getColumnIndex("_id"));
        pay.itemname = c.getString(c.getColumnIndex("itemname"));
        pay.yen = c.getInt(c.getColumnIndex("yen"));
        pay.datetime = c.getLong(c.getColumnIndex("datetime"));

        return pay;
    }

    static class DBAccessor extends AbstractDBAccessor<Pay> {
        public DBAccessor(SQLiteDatabase db){
            super(db, TABLE_NAME);
        }
        @Override
        protected Pay createModel(Cursor c) {
            return createUsingCursor(c);
        }

        @Override
        protected ContentValues createContentValue(Pay instance) {
            ContentValues values = new ContentValues();
            if (instance.id!=UNDEFINED) values.put("_id", instance.id);
            values.put("itemname",instance.itemname);
            values.put("yen", instance.yen);
            values.put("datetime", instance.datetime);
            return values;
        }
    }





    public static ArrayList<Pay> list(SQLiteDatabase db, String selection, String[] selectionArgs, String orderBy) {
        return new DBAccessor(db).list(selection, selectionArgs, orderBy);
    }

    public static Pay get(SQLiteDatabase db, long id) {
        return new DBAccessor(db).get(id);
    }

    public void put(SQLiteDatabase db) {
        new DBAccessor(db).put(this);
    }

    public static void delete(SQLiteDatabase db, String selection, String[] selectionArgs) {
        new DBAccessor(db).delete(selection, selectionArgs);
    }

    public void delete(SQLiteDatabase db) {
        new DBAccessor(db).delete(this);
    }






    public static void updateTable(SQLiteDatabase db, int oldVersion, int newVersion) {
        int updateVersion = oldVersion;

        if (updateVersion < 1) {
            StringBuilder s = new StringBuilder();
            s.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME)
                    .append("(_id INTEGER PRIMARY KEY")
                    .append(",itemname TEXT")
                    .append(",yen INTEGER")
                    .append(",datetime INTEGER")
                    .append(");");
            db.execSQL(s.toString());

            updateVersion = 1;
        }

    }

    public static void dropTable(SQLiteDatabase db) {
        new DBAccessor(db).dropTable();
    }
}
