package com.app.kimkulling.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 *  Helper class for SQLLite access.
 */
public class SLDatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG       = "SLDatabaseHelper";
    public static final String DB_NAME   = "shoppinglist.db";
    public static final String SHL_TABLE = "shoppinglists";
    public static final String C_ID      = BaseColumns._ID;
    public static final String C_SHOP    = "shop";
    public static final String C_LIST    = "item";
    public static final int DB_VERSION   = 1;

    private final Context mCtx;

    /**
     * The class constructor
     * @param ctx The application context.
     */
    public SLDatabaseHelper(Context ctx) {
        super( ctx, DB_NAME, null, DB_VERSION );
        mCtx = ctx;
    }

    /**
     * The onCreate-callback, will initially create the table
     * @param db    The database instance
     */
    @Override
    public void onCreate( SQLiteDatabase db ) {
        String sql = "create table " + SHL_TABLE + " (" + C_ID + " int primary key, "
                + C_SHOP + " text, " + C_LIST + " text )";
        executeSQL(db, sql);
        Log.d(TAG, " Create database " + DB_NAME);
    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        String sql = "drop table if exists " + SHL_TABLE;
        executeSQL( db, sql );

        Log.d(TAG, " Update database " + DB_NAME);
        onCreate( db );
    }

    private void executeSQL( SQLiteDatabase db, String sql ) {
        Log.d( TAG, " Perform SQL query: " + sql );
        try {
            db.execSQL(sql);
        } catch(  Exception ex ) {
            Log.d( TAG, "Exception " + ex.toString() + " catched." );
        }
    }
}
