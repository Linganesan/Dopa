package com.kuviam.dopa.db;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.kuviam.dopa.model.DaoMaster;
import com.kuviam.dopa.model.DaoSession;


public class GreenDaoApplication extends Application {

    DaoSession mDaoSession;


    public void onCreate () {
        super.onCreate ();
        setupDatabase ();
    }

    private void setupDatabase () {
        DaoMaster.DevOpenHelper helper =
                new DaoMaster.DevOpenHelper (this, "MyGreenDao.db", null);
        SQLiteDatabase db = helper.getWritableDatabase ();
        DaoMaster daoMaster = new DaoMaster (db);
        mDaoSession = daoMaster.newSession ();
    }

    public DaoSession getDaoSession () {
        return mDaoSession;
    }
}