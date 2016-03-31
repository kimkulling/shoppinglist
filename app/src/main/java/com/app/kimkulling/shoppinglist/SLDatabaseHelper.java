package com.app.kimkulling.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 *  Helper class for SQL access.
 */
public class SLDatabaseHelper extends SQLiteOpenHelper {
    static final String TAG       = "SLDatabaseHelper";
    static final String DB_NAME   = "shoppinglist.db";
    static final String SHL_TABLE = "shoppinglists";
    static final String C_ID      = BaseColumns._ID;
    static final String C_SHOP    = "shop";
    static final String C_LIST    = "item";
    static final int DB_VERSION   = 1;
    Context mCtx;

    public SLDatabaseHelper(Context ctx) {
        super( ctx, DB_NAME, null, DB_VERSION );
        mCtx = ctx;
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        try {
            String sql = "create table " + SHL_TABLE + " (" + C_ID + " int primary key, "
                    + C_SHOP + " text, " + C_LIST + " text )";
            executeSQL(db, sql);
            Log.d(TAG, " Create database " + DB_NAME);
        } catch ( Exception ex ) {
            Log.d( TAG, "Exception " + ex.toString() + " catched." );
        }
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        String sql = "drop table if exists " + SHL_TABLE;
        executeSQL( db, sql );

        Log.d(TAG, " Update database " + DB_NAME);
        onCreate( db );
    }

    private void executeSQL( SQLiteDatabase db, String sql ) {
        Log.d( TAG, " Perform SQL query: " + sql );
        db.execSQL( sql );
    }
}
