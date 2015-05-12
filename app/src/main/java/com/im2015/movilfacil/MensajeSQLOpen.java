package com.im2015.movilfacil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Carlos on 08/05/2015.
 */
public class MensajeSQLOpen extends SQLiteOpenHelper {

    private static final  int version = 1;
    private static final String name = "MovilFacil";
    private static SQLiteDatabase.CursorFactory factory = null;
    public MensajeSQLOpen(Context context) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Mensaje(" +
                "numero TEXT NOT NULL," +
                "mensaje TEXT NOT NULL," +
                "fecha INTEGER NOT NULL," +
                "tipo TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
