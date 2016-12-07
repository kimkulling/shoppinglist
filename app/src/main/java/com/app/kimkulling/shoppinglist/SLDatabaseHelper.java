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
    private static final String TAG           = "SLDatabaseHelper";
    public static final String DB_NAME       = "shoppinglist.db";
    public static final String SHL_TABLE     = "shoppinglists";
    private static final String C_SH_ID       = BaseColumns._ID;
    public static final String C_SH_SHOP     = "shop";
    public static final String C_SH_LIST     = "item";
    public static final String C_SH_CATID    = "catid";
    public static final String C_SH_ISIN     = "isin";
    public static final String C_SH_NUMITEMS = "number";
    public static final String C_SHCAT_TABLE = "shopping_categories";
    public static final String C_SHCAT_NAME  = "category";

    private static final int DB_VERSION   = 2;

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
     * The onCreate-callback, will initially create the tables.
     * @param db    The database instance
     */
    @Override
    public void onCreate( SQLiteDatabase db ) {
        String slSql = "create table " + SHL_TABLE + " (" + C_SH_ID + " int primary key, "
                + C_SH_SHOP + " text, " + C_SH_LIST + " text, " + C_SH_CATID + " int )";
        executeSQL( db, slSql );

        String catSql = "create table " + C_SHCAT_TABLE + " (" + C_SH_ID + " int primary key, "
                + C_SHCAT_NAME + " text )";
        executeSQL( db, catSql );

        Log.d(TAG, " Create database " + DB_NAME);
    }

    /**
     *  The onUpgrade callback, will be called when the version changed.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        if ( 1 == oldVersion ) {
            deleteTable( db, SHL_TABLE );
            Log.d( TAG, " Update database " + DB_NAME + ", old database will be removed." );
        }

        onCreate( db );
    }

    private boolean deleteTable( SQLiteDatabase db, final String table ) {
        final String sql = "drop table if exists " + table;
        final boolean ok = executeSQL( db, sql );
        if ( !ok ) {
            Log.e( TAG, "Error while deleting table " + table + "." );
        }
        return ok;
    }

    private boolean executeSQL( SQLiteDatabase db, String sql ) {
        boolean ok = true;
        Log.d( TAG, " Perform SQL query: " + sql );
        try {
            db.execSQL(sql);
        } catch(  Exception ex ) {
            Log.d( TAG, "Exception " + ex.toString() + " catched." );
            ok = false;
        }

        return ok;
    }
}
