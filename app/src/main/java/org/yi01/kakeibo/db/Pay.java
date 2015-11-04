package org.yi01.kakeibo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Pay extends AbstractModel {
    public static final String TABLE_NAME = "pay";

    public long categoryId;
    public int yen;
    public long datetime;


    public static Pay createUsingCursor(Cursor c) {
        Pay pay = new Pay();

        pay.id = c.getLong(c.getColumnIndex("_id"));
        pay.categoryId = c.getLong(c.getColumnIndex("category_id"));
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
            values.put("category_id",instance.categoryId);
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

        if (updateVersion < 2) {


            db.beginTransaction();
            try {
                {
                    StringBuilder s = new StringBuilder();
                    s.append("CREATE TEMPORARY TABLE IF NOT EXISTS mig1to2")
                            .append("(_id INTEGER PRIMARY KEY")
                            .append(",itemname TEXT")
                            .append(",yen INTEGER")
                            .append(",datetime INTEGER")
                            .append(");");
                    db.execSQL(s.toString());
                }

                {
                    StringBuilder s = new StringBuilder();
                    s.append("INSERT INTO mig1to2(_id, itemname, yen, datetime) ")
                            .append("SELECT _id,itemname,yen,datetime FROM ").append(TABLE_NAME);
                    db.execSQL(s.toString());
                }

                dropTable(db);

                {
                    StringBuilder s = new StringBuilder();
                    s.append("INSERT INTO ").append(Category.TABLE_NAME).append("(name) ")
                            .append("SELECT DISTINCT itemname FROM mig1to2");
                    db.execSQL(s.toString());
                }

                {
                    StringBuilder s = new StringBuilder();
                    s.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME)
                            .append("(_id INTEGER PRIMARY KEY")
                            .append(",category_id INTEGER NOT NULL")
                            .append(",yen INTEGER")
                            .append(",datetime INTEGER")
                            .append(",FOREIGN KEY(category_id) REFERENCES category(_id)")
                            .append(");");
                    db.execSQL(s.toString());
                }

                {
                    StringBuilder s = new StringBuilder();
                    s.append("INSERT INTO ").append(TABLE_NAME).append("(_id, category_id, yen, datetime) ")
                            .append("SELECT mig1to2._id,category._id,mig1to2.yen,mig1to2.datetime FROM mig1to2 INNER JOIN category ON itemname=name");
                    db.execSQL(s.toString());
                }

                db.setTransactionSuccessful();
            }
            finally {
                db.endTransaction();
            }

            updateVersion = 2;
        }


    }

    public static void dropTable(SQLiteDatabase db) {
        new DBAccessor(db).dropTable();
    }
}
