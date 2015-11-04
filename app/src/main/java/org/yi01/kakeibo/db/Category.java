package org.yi01.kakeibo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Category extends AbstractModel {
    public static final String TABLE_NAME = "category";

    public String name;


    public static Category createUsingCursor(Cursor c) {
        Category category = new Category();

        category.id = c.getLong(c.getColumnIndex("_id"));
        category.name = c.getString(c.getColumnIndex("name"));

        return category;
    }

    static class DBAccessor extends AbstractDBAccessor<Category> {
        public DBAccessor(SQLiteDatabase db){
            super(db, TABLE_NAME);
        }
        @Override
        protected Category createModel(Cursor c) {
            return createUsingCursor(c);
        }

        @Override
        protected ContentValues createContentValue(Category instance) {
            ContentValues values = new ContentValues();
            if (instance.id!=UNDEFINED) values.put("_id", instance.id);
            values.put("name",instance.name);
            return values;
        }
    }





    public static ArrayList<Category> list(SQLiteDatabase db, String selection, String[] selectionArgs, String orderBy) {
        return new DBAccessor(db).list(selection, selectionArgs, orderBy);
    }

    public static Category get(SQLiteDatabase db, long id) {
        return new DBAccessor(db).get(id);
    }

    public static Category getOrCreate(SQLiteDatabase db, String name) {
        ArrayList<Category> list = new DBAccessor(db).list("name='" + name+"'", null, "_id DESC");
        if(list!=null && list.size()>=1) return list.get(0);

        Category c = new Category();
        c.name = name;
        return c;
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

        if (updateVersion < 2) {
            StringBuilder s = new StringBuilder();
            s.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME)
                    .append("(_id INTEGER PRIMARY KEY")
                    .append(",name TEXT UNIQUE NOT NULL")
                    .append(");");
            db.execSQL(s.toString());

            updateVersion = 2;
        }

    }

    public static void dropTable(SQLiteDatabase db) {
        new DBAccessor(db).dropTable();
    }
}
