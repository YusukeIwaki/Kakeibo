package org.yi01.kakeibo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final static int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, "pay", null, DB_VERSION);
    }


    public interface DBCallback<T> {
        T process(SQLiteDatabase db);
    }

    public static <T> T read(Context context, DBCallback<T> process) {
        T ret = null;

        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
        try {
            ret = process.process(db);
        }
        finally {
            db.close();
        }
        return ret;
    }

    public static <T> T write(Context context, DBCallback<T> process) {
        T ret = null;

        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();
        try {
            ret = process.process(db);
        }
        finally {
            db.close();
        }
        return ret;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpgrade(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Pay.updateTable(db, oldVersion, newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Pay.dropTable(db);
        onUpgrade(db, 0, newVersion);
    }
}
