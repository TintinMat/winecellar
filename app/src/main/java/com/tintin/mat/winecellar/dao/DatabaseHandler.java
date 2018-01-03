package com.tintin.mat.winecellar.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Mat & Audrey on 15/10/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int ON_CREATE = 1;
    public static final int ON_UPDATE = 2;

    private int mode;

    public DatabaseHandler(Context context) {
        super(context, DAOBase.NOM, null, DAOBase.VERSION);
    }

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(PaysDao.TABLE_CREATE);
            db.execSQL(RegionDao.TABLE_CREATE);
            db.execSQL(AppellationDao.TABLE_CREATE);

            db.execSQL(CaveDao.TABLE_CREATE);
            db.execSQL(ClayetteDao.TABLE_CREATE);
            db.execSQL(BouteilleDao.TABLE_CREATE);

            db.execSQL(CepageDao.TABLE_CREATE);
            db.execSQL(CepageBouteilleDao.TABLE_CREATE);

            setMode(this.ON_CREATE);

        }catch (SQLiteException ex){
            Log.e(TAG, "onCreate: ",ex );
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(CepageBouteilleDao.TABLE_DROP);
        db.execSQL(CepageDao.TABLE_DROP);
        db.execSQL(BouteilleDao.TABLE_DROP);
        db.execSQL(ClayetteDao.TABLE_DROP);
        db.execSQL(CaveDao.TABLE_DROP);

        db.execSQL(AppellationDao.TABLE_DROP);
        db.execSQL(RegionDao.TABLE_DROP);
        db.execSQL(PaysDao.TABLE_DROP);

        onCreate(db);

        setMode(this.ON_UPDATE);
     }


    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}