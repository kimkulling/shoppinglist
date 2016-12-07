package com.app.kimkulling.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by kimku_000 on 07.12.2016.
 */

public class CategoryDBAccess {
    private static final String TAG = "CategoryDBAccess";
    private Context mCtx;
    private SLDatabaseHelper mDBHelper;
    private SQLiteDatabase mShoppingListDB;

    public CategoryDBAccess( Context ctx ) {
        mCtx = ctx;
        if ( null != mCtx ) {
            mDBHelper = new SLDatabaseHelper( ctx );
        }
    }

    public long addNewShoppingCategory( final String categoryName ) {
        if ( mShoppingListDB == null ) {
            Log.d( TAG, "Cannot insert item, no database object." );
            return -1;
        }

        if ( 0 == categoryName.length() ) {
            Log.e( TAG, "Category name is empty" );
            return -1;
        }

        ContentValues newItems = new ContentValues();
        newItems.put( SLDatabaseHelper.C_SHCAT_NAME, categoryName );
        long insertId = mShoppingListDB.insert(SLDatabaseHelper.C_SHCAT_TABLE, null, newItems);
        if ( -1 == insertId ) {
            Log.d( TAG, "Error while inserting values." );
        }

        return insertId;
    }
}
