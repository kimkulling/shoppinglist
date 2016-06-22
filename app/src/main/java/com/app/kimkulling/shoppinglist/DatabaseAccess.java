package com.app.kimkulling.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;

/**
 *  Class to access all databases.
 */
public class DatabaseAccess {
    private static final String TAG = "DatabaseAccess";
    private final SLDatabaseHelper mSLDBHelper;
    private SQLiteDatabase mShoppingListDB;
    private final Context mContext;
    private final boolean mReadonly;

    public enum DatabaseType {
        ShoppingListType
    }

    public DatabaseAccess( Context ctx, boolean readonly  ) {
        mContext = ctx;
        mSLDBHelper = new SLDatabaseHelper( ctx );
        mReadonly = readonly;
    }

    public boolean exists( DatabaseType type ) {
        if ( null == mContext ) {
            return false;
        }
        boolean exist = false;
        if ( DatabaseType.ShoppingListType == type ) {
            File dbFile = mContext.getDatabasePath( SLDatabaseHelper.DB_NAME );
            exist = dbFile.exists();
        }

        return exist;
    }

    public boolean open( DatabaseType type ) {
        boolean res = false;
        if ( type == DatabaseType.ShoppingListType ) {
            if ( null != mShoppingListDB ) {
                if ( mShoppingListDB.isOpen() ) {
                    return true;
                }
            }

            if ( mReadonly ) {
                mShoppingListDB = mSLDBHelper.getReadableDatabase();
            } else {
                mShoppingListDB = mSLDBHelper.getWritableDatabase();
            }

            if (mShoppingListDB.isOpen()) {
                Log.d(TAG, "Opening database.");
                res = true;
            } else {
                Log.d(TAG, "Cannot open database.");
            }
        }

        return  res;
    }

    public boolean close() {
        boolean res = false;
        if ( mShoppingListDB.isOpen() ) {
            res = true;
            mSLDBHelper.close();
        } else {
            Log.d( TAG, "Cannot close database." );
        }

        return res;
    }

    public long addNewShoppingList( final String shop, final String items ) {
        if ( mShoppingListDB == null ) {
            Log.d( TAG, "Cannot insert item, no database object." );
            return -1;
        }

        if ( 0 == shop.length() ) {
            Log.e( TAG, "Shop name is empty." );
            return -1;
        }

        if ( 0 == items.length() ) {
            Log.d( TAG, "items are empty." );
            return -1;
        }

        ContentValues newItems = new ContentValues();
        newItems.put( SLDatabaseHelper.C_SHOP, shop );
        newItems.put(SLDatabaseHelper.C_LIST, items);
        long insertId = mShoppingListDB.insert(SLDatabaseHelper.SHL_TABLE, null, newItems);
        if ( -1 == insertId ) {
            Log.d( TAG, "Error while inserting values." );
        }

        return insertId;
    }

    public boolean hasShoppingList( final String shopName ) {
        if ( null == shopName ) {
            return false;
        }
        if (0 == shopName.length()) {
            return false;
        }

        boolean found = false;
        String columns[] = new String[] {
            SLDatabaseHelper.C_LIST
        };

        String selection = SLDatabaseHelper.C_SHOP + "=\"" + shopName+"\"";
        Log.d(TAG, selection);
        Cursor cursor = mShoppingListDB.query(SLDatabaseHelper.SHL_TABLE, columns, selection, null,
                null, null, null);
        if ( null != cursor ) {
            int numLists = cursor.getCount();
            cursor.close();
            if (numLists > 0) {
                found = true;
            }
        }

        return found;
    }

    public boolean modifyShoppingLists( final String shopName, final String items ) {
        Log.d( TAG, "modifyShoppingLists" );
        if ( 0 == shopName.length() ) {
            Log.d( TAG, "Shop name is empty, cannot modify data." );
            return false;
        }

        String sql = "update " + SLDatabaseHelper.SHL_TABLE + " SET " + SLDatabaseHelper.C_LIST + "=\"" +
                items  +"\"" + " where " + SLDatabaseHelper.C_SHOP + "=\"" + shopName+"\"";
        Log.d( TAG, sql );

        mShoppingListDB.execSQL( sql );

        return true;
    }

    public boolean deleteAllShoppingLists() {
        String sql = "drop table if exists " + SLDatabaseHelper.SHL_TABLE;
        mShoppingListDB.execSQL( sql );

        return true;
    }

    public boolean deleteShoppingList( final String shopName ) {
        if ( 0 == shopName.length() ) {
            return false;
        }

        if ( !this.hasShoppingList( shopName ) ) {
            return false;
        }

        String sql = "DELETE FROM " + SLDatabaseHelper.SHL_TABLE + " WHERE " +
                SLDatabaseHelper.C_SHOP +"=\n" + "\"" +shopName + "\"";
        Log.d( TAG, sql );
        mShoppingListDB.execSQL( sql );

        return true;
    }

    public ShoppingItem getShoppingListByShop( final String shopName ) {
        if ( 0 == shopName.length() ) {
            return null;
        }
        String columns[] = new String[] {
                SLDatabaseHelper.C_LIST
        };
        ShoppingItem shItem = null;
        String selection = SLDatabaseHelper.C_SHOP + "=\"" + shopName+"\"";
        Cursor cursor = mShoppingListDB.query( SLDatabaseHelper.SHL_TABLE, columns, selection, null,
                null, null, null);
        if ( null != cursor ) {
            int numLists = cursor.getCount();
            if ( numLists  > 0 ) {
                if ( cursor.moveToFirst() ) {
                    String items = cursor.getString( cursor.getColumnIndex(
                            SLDatabaseHelper.C_LIST ) );
                    Log.d( TAG, "shop = " + shopName + " ,items = " + items );
                    shItem = new ShoppingItem( shopName, items );
                }
            }
            cursor.close();
        }

        return shItem;
    }

    public ShoppingItem[] readAllShoppingLists() {
        if ( mShoppingListDB == null ) {
            Log.d( TAG, "Cannot read lists, no database object." );
            return null;
        }

        Cursor myCursor = mShoppingListDB.query( SLDatabaseHelper.SHL_TABLE, null, null, null, null,
                null, null );
        if ( null == myCursor ) {
            Log.d( TAG, "Cannot read table " + SLDatabaseHelper.SHL_TABLE );
            return null;
        }

        ShoppingItem shoppingItems[] = null;
        final int numLists = myCursor.getCount();
        if ( numLists > 0 ) {
            shoppingItems = new ShoppingItem[ numLists ];
            if ( myCursor.moveToFirst() ) {
                int i = 0;
                do {
                    String shopName = myCursor.getString( myCursor.getColumnIndex(
                            SLDatabaseHelper.C_SHOP ) );
                    String items = myCursor.getString( myCursor.getColumnIndex(
                            SLDatabaseHelper.C_LIST ) );
                    Log.d( TAG, "shop = " + shopName + " ,items = " + items );
                    shoppingItems[ i ] = new ShoppingItem( shopName, items );
                    i++;
                } while ( myCursor.moveToNext() );
            }
        }
        myCursor.close();

        return shoppingItems;
    }
}
