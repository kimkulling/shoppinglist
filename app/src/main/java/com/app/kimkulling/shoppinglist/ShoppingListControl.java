package com.app.kimkulling.shoppinglist;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

/**
 * Created by kimku_000 on 01.05.2016.
 */
public class ShoppingListControl {
    private static final String TAG = "ShoppingListControl";
    private Activity mParentActivity;
    private DatabaseAccess mDBAccess;

    public ShoppingListControl( Activity parentActivity, DatabaseAccess dbAccess ) {
        mParentActivity = parentActivity;
        mDBAccess = dbAccess;
    }

    public void onCreateShoppingList( final String shop ) {
        Log.d(TAG, "onCreateShoppingList");
        Intent intent = new Intent( mParentActivity, ShoppingListActivity.class );
        intent.putExtra( "shop", shop );
        mParentActivity.startActivity(intent );
        Log.d( TAG, "new Intent to create ShoppingListActivity" );
    }

    public DatabaseAccess getDatabaseAccess() {
        return mDBAccess;
    }

    public void onAboutDlg() {

    }

}
